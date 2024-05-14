import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new LinkedList<>();

        threads.add(new Thread(() -> {
            for (String text : texts) {
                if (compareText(text) && isPalindrome(text)) counter(text);
            }
        }));

        threads.add(new Thread(() -> {
            for (String text : texts) {
                if (compareText(text) && areSameCharacters(text)) counter(text);
            }
        }));

        threads.add(new Thread(() -> {
            for (String text : texts) {
                if (compareText(text) && areCharactersIncreasing(text)) counter(text);
            }
        }));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        printResult();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void counter(String text) {

        int lengthText = text.length();

        switch (lengthText) {
            case 3:
                count3.getAndIncrement();
                break;
            case 4:
                count4.getAndIncrement();
                break;
            case 5:
                count5.getAndIncrement();
                break;
            default:
                System.out.println("Something wrong");
        }
    }

    public static boolean compareText(String text) {
        if (text.length() < 3) return false;
        if (text.length() > 5) return false;
        return true;
    }

    public static boolean isPalindrome(String str) {

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean areSameCharacters(String text) {

        char firstChar = text.charAt(0);

        if (text.length() == 0) return false;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) return false;
        }
        return true;
    }

    public static boolean areCharactersIncreasing(String str) {
        char firstChar = str.charAt(0);
        char secondChar = str.charAt(1);

        if (secondChar <= firstChar) return false;
        for (int i = 2; i < str.length(); i++) {

            char currentChar = str.charAt(i);
            char previousChar = str.charAt(i - 1);

            if (currentChar <= previousChar) return false;
        }
        return true;
    }

    public static void printResult() {
        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }
}
