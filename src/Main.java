import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        List<String> categories = Arrays.asList("Geography", "Art", "Literature");
        
        System.out.println(ANSI_CYAN + "Welcome to the Quiz" + ANSI_RESET);
        int selectedCategoryIndex = getSelectedCategoryIndex(categories);
        if (selectedCategoryIndex == -1) {
            System.out.println(ANSI_RED + "There is no such category. Exiting the game." + ANSI_RESET);
            return;
        }
        
        String selectedCategory = categories.get(selectedCategoryIndex);
        List<String[]> questions = readQuestionsFromCSV(selectedCategory);
        
        if (questions.isEmpty()) {
            System.out.println(ANSI_RED + "We forgot to put questions in this category :(. Exiting the game." + ANSI_RESET);
            return;
        }
        
        System.out.println("Let's start the quiz!");
        int score = playQuiz(questions);
        System.out.println("Your final score: " + score);
    }
    
    private static int getSelectedCategoryIndex(List<String> categories) {
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + getRainbowText(categories.get(i)));
        }
        
        int selectedCategoryIndex = scanner.nextInt();
        scanner.nextLine();
        if (selectedCategoryIndex < 1 || selectedCategoryIndex > categories.size()) {
            return -1;
        }
        
        return selectedCategoryIndex - 1;
    }
    
    private static int playQuiz(List<String[]> questions) {
        int score = 0;
        int questionIndex = 0;
        while (questionIndex < questions.size()) {
            String[] questionData = questions.get(questionIndex);
            String questionText = questionData[0];
            String correctAnswer = questionData[1];
            String[] wrongAnswers = Arrays.copyOfRange(questionData, 2, questionData.length);
            System.out.println("Question: " + getRainbowText(questionText));
            List<String> answerOptions = getShuffledAnswerOptions(correctAnswer, wrongAnswers);
            printAnswerOptions(answerOptions);
            
            int userAnswerIndex = getUserAnswerIndex(answerOptions.size());
            if (userAnswerIndex < 1 || userAnswerIndex > answerOptions.size()) {
                System.out.println(ANSI_RED + "Invalid selection. Game over." + ANSI_RESET);
                break;
            }
            
            String userAnswer = answerOptions.get(userAnswerIndex - 1);
            if (userAnswer.equals(correctAnswer)) {
                System.out.println(ANSI_GREEN + "Correct answer!" + ANSI_RESET);
                score++;
                questionIndex++;
            } else {
                System.out.println(ANSI_RED + "Wrong answer. Game over." + ANSI_RESET);
                break;
            }
        }
        return score;
    }
    
    private static List<String[]> readQuestionsFromCSV(String category) {
        List<String[]> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("questions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String questionCategory = data[0].trim();
                if (questionCategory.equalsIgnoreCase(category)) {
                    questions.add(Arrays.copyOfRange(data, 1, data.length));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions, random);
        return questions;
    }
    
    private static List<String> getShuffledAnswerOptions(String correctAnswer, String[] wrongAnswers) {
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add(correctAnswer);
        answerOptions.addAll(Arrays.asList(wrongAnswers));
        return shuffleList(answerOptions);
    }
    
    private static List<String> shuffleList(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList, random);
        return shuffledList;
    }
    
    private static void printAnswerOptions(List<String> answerOptions) {
        for (int i = 0; i < answerOptions.size(); i++) {
            System.out.println((i + 1) + ". " + getRainbowText(answerOptions.get(i)));
        }
    }
    
    private static int getUserAnswerIndex(int maxIndex) {
        int userAnswerIndex = scanner.nextInt();
        scanner.nextLine();
        return userAnswerIndex;
    }
    
    private static String getRainbowText(String text) {
        StringBuilder rainbowText = new StringBuilder();
        String[] rainbowColors = { ANSI_RED, ANSI_YELLOW, ANSI_GREEN, ANSI_CYAN, ANSI_BLUE, ANSI_PURPLE };
        int colorIndex = 0;
        
        for (int i = 0; i < text.length(); i++) {
            rainbowText.append(rainbowColors[colorIndex]).append(text.charAt(i));
            colorIndex = (colorIndex + 1) % rainbowColors.length;
        }
        
        return rainbowText.toString();
    }
}
