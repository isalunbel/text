import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger count3 = new AtomicInteger(0);
    static AtomicInteger count4 = new AtomicInteger(0);
    static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isBeauty3(text)) {
                    count3.incrementAndGet();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isBeauty4(text)) {
                    count4.incrementAndGet();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isBeauty5(text)) {
                    count5.incrementAndGet();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + count3.get());
        System.out.println("Красивых слов с длиной 4: " + count4.get());
        System.out.println("Красивых слов с длиной 5: " + count5.get());
    }

    public static String generateText(String characters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(characters.charAt(random.nextInt(characters.length())));
        }
        return text.toString();
    }

    public static boolean isBeauty3(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isBeauty4(String text) {
        return text.matches("^(.)\\1*$");
    }

    public static boolean isBeauty5(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) <= text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}