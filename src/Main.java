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
        scanner.nextLine();
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
