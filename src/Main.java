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
    public static void main(String[] args) {
        List<String> categories = Arrays.asList("Geography", "Art", "Literature");
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        
        int selectedCategoryIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        if (selectedCategoryIndex < 1 || selectedCategoryIndex > categories.size()) {
            System.out.println("Invalid category selection. Exiting the game.");
            return;
        }
        
        String selectedCategory = categories.get(selectedCategoryIndex - 1);
        List<String[]> questions = readQuestionsFromCSV(selectedCategory, random);
        
        if (questions.isEmpty()) {
            System.out.println("No questions found for the selected category. Exiting the game.");
            return;
        }
        
        System.out.println("Let's start the quiz!");
        
        int score = 0;
        int questionIndex = 0;
        
        while (questionIndex < questions.size()) {
            String[] questionData = questions.get(questionIndex);
            
            String questionText = questionData[0];
            String correctAnswer = questionData[1];
            String[] wrongAnswers = Arrays.copyOfRange(questionData, 2, questionData.length);
            
            System.out.println("Question: " + questionText);
            
            List<String> answerOptions = new ArrayList<>();
            answerOptions.add(correctAnswer);
            answerOptions.addAll(Arrays.asList(wrongAnswers));
            answerOptions = shuffleList(answerOptions, random);
            
            for (int i = 0; i < answerOptions.size(); i++) {
                System.out.println((i + 1) + ". " + answerOptions.get(i));
            }
            
            int userAnswerIndex = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            if (userAnswerIndex < 1 || userAnswerIndex > answerOptions.size()) {
                System.out.println("Invalid answer option. Game over.");
                break;
            }
            
            String userAnswer = answerOptions.get(userAnswerIndex - 1);
            
            if (userAnswer.equals(correctAnswer)) {
                System.out.println("Correct answer!");
                score++;
                questionIndex++;
            } else {
                System.out.println("Wrong answer. Game over.");
                break;
            }
        }
        
        System.out.println("Your final score: " + score);
    }
    
    private static List<String[]> readQuestionsFromCSV(String category, Random random) {
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
    
    private static List<String> shuffleList(List<String> list, Random random) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList, random);
        return shuffledList;
    }
}