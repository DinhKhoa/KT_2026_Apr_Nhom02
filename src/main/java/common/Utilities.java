package common;

public class Utilities {
    public static String generateRandomString(int length) {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    public static String generateRandomNumber(int length) {
        StringBuilder result = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public static String generateRandomEmail(String prefix) {
        return (prefix.isEmpty() ? "user" : prefix) + "_" + generateRandomString(5) + "@example.com";
    }

    public static String getIDFromURL(String url) {
        String[] parts = url.split("id=");
        return parts.length > 1 ? parts[1] : "";
    }

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
