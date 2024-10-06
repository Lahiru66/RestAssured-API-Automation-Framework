package utils;

import java.security.SecureRandom;
import java.util.Random;

public class CommonUtils {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] DOMAINS = {"example.com", "test.com", "mail.com", "demo.com"};
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int LENGTH = 5;

    public static String generateRandomAlphanumeric(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            result.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    public static String generateRandomEmail() {
        StringBuilder localPart = new StringBuilder();

        // Generate a random local part (e.g., "user123")
        int localPartLength = 8; // You can change this length
        for (int i = 0; i < localPartLength; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            localPart.append(CHARACTERS.charAt(index));
        }

        // Choose a random domain
        String domain = DOMAINS[RANDOM.nextInt(DOMAINS.length)];

        // Combine to form an email address
        return localPart + "@" + domain;
    }

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}

