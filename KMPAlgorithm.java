public class KMPAlgorithm {

    // Function to perform KMP Search
    public static void KMPSearch(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();

        // Create lps[] that will hold the longest prefix suffix values
        int[] lps = new int[M];
        computeLPSArray(pattern, M, lps);

        int i = 0; // index for text
        int j = 0; // index for pattern
        while (i < N) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == M) {
                System.out.println("Pattern found at index " + (i - j));
                j = lps[j - 1];
            } else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
    }

    // Function to compute the LPS (Longest Prefix Suffix) array
    public static void computeLPSArray(String pattern, int M, int[] lps) {
        int len = 0; // length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    // Main function to test the KMP algorithm
    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        KMPSearch(pattern, text);
    }
}
