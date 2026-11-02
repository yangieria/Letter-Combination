/** One thing the player submitted, and how it turned out. */
public class Attempt {
    public enum Result { CORRECT, WRONG, DUPLICATE }

    public final String text;
    public final Result result;

    public Attempt(String text, Result result) {
        this.text = text;
        this.result = result;
    }
}