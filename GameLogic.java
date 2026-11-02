import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Pure game logic (no UI): phone-keypad letters, random digits, backtracking. */
public final class GameLogic {
    private GameLogic() {}

    public static final String[] LETTERS = {"", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
    private static final Random RNG = new Random();

    /** n random digits from 2-9 (0 and 1 have no letters). */
    public static String randomDigits(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(2 + RNG.nextInt(8));
        return sb.toString();
    }

    /** Backtracking: every possible letter combination for the digits. */
    public static List<String> combinations(String digits) {
        List<String> out = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return out;
        backtrack(digits, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(String digits, int i, StringBuilder cur, List<String> out) {
        if (i == digits.length()) {
            out.add(cur.toString());
            return;
        }
        for (char c : LETTERS[digits.charAt(i) - '0'].toCharArray()) {
            cur.append(c);
            backtrack(digits, i + 1, cur, out);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}