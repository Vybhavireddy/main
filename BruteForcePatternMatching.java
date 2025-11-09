public class BruteForcePatternMatching {
    public static void main(String[] args) {
        String text = "AABAACAADAABAABA";
        String pattern = "AABA";

        bruteForceSearch(text, pattern);
    }

    static void bruteForceSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        // Loop through the text to check for pattern match
        for (int i = 0; i <= n - m; i++) {
            int j;

            // Check for match character by character
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;  // mismatch found
                }
            }

            // If pattern fully matched
            if (j == m) {
                System.out.println("Pattern found at index " + i);
            }
        }
    }
}
