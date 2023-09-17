package splitter;

import java.util.Scanner;

public class Splitter {
    public static void main(String[] args) {
        System.out.println("Enter a sentence specified by spaces only:");
        // Add your code
        Scanner myscanner = new Scanner(System.in);
        String line = myscanner.nextLine();
        myscanner.close();
        String[] words = line.split(" ");
        for (String word : words) {
            System.out.println(word);
        }
    }
}
