import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Page 6 - Main game screen. */
public class GamePanel extends JPanel implements KeypadPanel.Listener {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 3;
    private static final int MAX_MISTAKES = 3;
    private static final int POINTS = 10;
    private static final int BONUS = 50;

    private final Navigator nav;

    private final JLabel numberLabel = Theme.label("", Font.BOLD, 46, Theme.YELLOW, JLabel.CENTER);
    private final JLabel totalLabel = Theme.label("", Font.PLAIN, 14, Color.WHITE, JLabel.CENTER);
    private final JLabel answerLabel = Theme.label("", Font.BOLD, 38, Color.WHITE, JLabel.CENTER);
    private final JLabel feedbackLabel = Theme.label(" ", Font.BOLD, 13, Color.WHITE, JLabel.CENTER);
    private final JLabel scoreLabel = Theme.label("", Font.PLAIN, 14, Color.WHITE, JLabel.LEFT);
    private final JLabel mistakesLabel = Theme.label("", Font.PLAIN, 14, Color.WHITE, JLabel.RIGHT);

    private String digits = "";
    private Set<String> valid = new HashSet<>();
    private Set<String> found = new HashSet<>();
    private final List<Attempt> attempts = new ArrayList<>();
    private final StringBuilder answer = new StringBuilder();

    private int score = 0;
    private int mistakes = 0;
    private boolean locked = false;

    private int pendingDigit = -1;
    private int pendingIndex = 0;

    private final Timer commitTimer = new Timer(900, e -> pendingDigit = -1);
    private final Timer feedbackTimer = new Timer(1300, e -> feedbackLabel.setText(" "));

    public GamePanel(Navigator nav) {
        this.nav = nav;

        commitTimer.setRepeats(false);
        feedbackTimer.setRepeats(false);

        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        add(Theme.place(Theme.label("LETTER", Font.BOLD, 40, Color.WHITE, JLabel.CENTER),
                0, 12, W, 48));
        add(Theme.place(Theme.label("COMBINATION", Font.BOLD, 34, Theme.YELLOW, JLabel.CENTER),
                0, 58, W, 42));

        RoundedPanel find = new RoundedPanel(Theme.BG, Theme.BLUE, 32, 3);
        find.add(Theme.place(Theme.label("FIND COMBINATIONS FOR",
                Font.PLAIN, 13, Color.WHITE, JLabel.CENTER), 0, 8, 220, 20));
        find.add(Theme.place(numberLabel, 0, 28, 220, 56));
        add(Theme.place(find, 130, 112, 220, 92));

        add(Theme.place(totalLabel, 0, 212, W, 22));

        RoundedPanel box = new RoundedPanel(Theme.PANEL, Theme.BLUE, 32, 3);
        box.add(Theme.place(Theme.label("YOUR ANSWER",
                Font.PLAIN, 15, Color.WHITE, JLabel.CENTER), 0, 8, 360, 22));
        box.add(Theme.place(answerLabel, 0, 30, 360, 46));
        box.add(Theme.place(feedbackLabel, 0, 74, 360, 20));
        add(Theme.place(box, 60, 242, 360, 100));

        add(Theme.place(scoreLabel, 60, 354, 150, 22));
        add(Theme.place(mistakesLabel, 230, 354, 190, 22));

        add(Theme.place(new KeypadPanel(this), 45, 384, 390, 256));

        RoundedButton answers = new RoundedButton("Answers", Theme.BLUE, Color.WHITE, 13, 16);
        answers.addActionListener(e -> nav.showHistory());
        add(Theme.place(answers, W - 110, 14, 96, 32));

        RoundedButton home = new RoundedButton("< Home", Theme.KEY_CLEAR, Color.WHITE, 13, 16);
        home.addActionListener(e -> nav.show(Navigator.HOME));
        add(Theme.place(home, 14, 14, 96, 32));

        installKeyboardShortcuts();
    }

    public String getDigits() {
        return digits;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public int getFoundCount() {
        return found.size();
    }

    public int getTotal() {
        return valid.size();
    }

    public void startRound(boolean resetScore) {
        if (resetScore) score = 0;

        mistakes = 0;

        int length = MIN_LENGTH
                + (int) (Math.random() * (MAX_LENGTH - MIN_LENGTH + 1));

        digits = GameLogic.randomDigits(length);

        valid = new HashSet<>(GameLogic.combinations(digits));
        found = new HashSet<>();

        attempts.clear();
        answer.setLength(0);

        pendingDigit = -1;
        pendingIndex = 0;
        locked = false;

        commitTimer.stop();
        feedbackTimer.stop();
        feedbackLabel.setText(" ");

        numberLabel.setText(digits);

        refreshAnswer();
        refreshStats();
    }

    private void finish(boolean won) {
        locked = true;
        commitTimer.stop();

        Timer t = new Timer(800, e ->
                nav.showResult(won, score, found.size(), valid.size(), mistakes));

        t.setRepeats(false);
        t.start();
    }

    @Override
    public void onDigit(int d) {
        if (locked || d < 2) return;

        String letters = GameLogic.LETTERS[d];

        if (pendingDigit == d && answer.length() > 0) {
            pendingIndex = (pendingIndex + 1) % letters.length();
            answer.setCharAt(answer.length() - 1, letters.charAt(pendingIndex));
        } else {
            if (answer.length() >= digits.length()) {
                say("Answer is full - Submit or Clear", Theme.YELLOW);
                return;
            }

            pendingDigit = d;
            pendingIndex = 0;
            answer.append(letters.charAt(0));
        }

        commitTimer.restart();
        refreshAnswer();
    }

    @Override
    public void onClear() {
        if (locked) return;

        answer.setLength(0);
        pendingDigit = -1;
        commitTimer.stop();

        refreshAnswer();
    }

    @Override
    public void onSubmit() {
        if (locked) return;

        commitTimer.stop();
        pendingDigit = -1;

        if (answer.length() != digits.length()) {
            say("Enter " + digits.length() + " letters first", Theme.YELLOW);
            return;
        }

        String a = answer.toString();

        answer.setLength(0);
        refreshAnswer();

        if (found.contains(a)) {
            attempts.add(new Attempt(a, Attempt.Result.DUPLICATE));
            mistake("Already submitted!");

        } else if (valid.contains(a)) {
            attempts.add(new Attempt(a, Attempt.Result.CORRECT));

            found.add(a);
            score += POINTS;

            say("Correct!  " + found.size() + "/" + valid.size(), Theme.GREEN_LT);

            if (found.size() == valid.size()) {
                score += BONUS;
                finish(true);
            }

        } else {
            attempts.add(new Attempt(a, Attempt.Result.WRONG));
            mistake("Wrong combination!");
        }

        refreshStats();
    }

    public void typeLetter(char c) {
        if (locked) return;

        if (answer.length() >= digits.length()) {
            say("Answer is full - Submit or Clear", Theme.YELLOW);
            return;
        }

        answer.append(Character.toUpperCase(c));

        pendingDigit = -1;
        commitTimer.stop();

        refreshAnswer();
    }

    public void deleteLast() {
        if (locked || answer.length() == 0) return;

        answer.deleteCharAt(answer.length() - 1);

        pendingDigit = -1;
        commitTimer.stop();

        refreshAnswer();
    }

    private void mistake(String msg) {
        mistakes++;
        say(msg, Theme.RED);

        if (mistakes >= MAX_MISTAKES) {
            finish(false);
        }
    }

    private void say(String text, Color c) {
        feedbackLabel.setForeground(c);
        feedbackLabel.setText(text);
        feedbackTimer.restart();
    }

    private void refreshAnswer() {
        StringBuilder sb = new StringBuilder(
                "<html><div style='text-align:center'>");

        for (int i = 0; i < digits.length(); i++) {
            if (i > 0) sb.append("&nbsp;&nbsp;");

            if (i < answer.length()) {
                sb.append("<span style='color:#FFFFFF'>")
                        .append(answer.charAt(i))
                        .append("</span>");
            } else {
                sb.append("<span style='color:#3A4A78'>_</span>");
            }
        }

        answerLabel.setText(sb.append("</div></html>").toString());
    }

    private void refreshStats() {
        totalLabel.setText(
                "<html><span style='color:#8ED96A'>FOUND: "
                        + found.size()
                        + "</span> &nbsp;|&nbsp; TOTAL COMBINATIONS: "
                        + valid.size()
                        + "</html>");

        scoreLabel.setText(
                "<html><span style='color:#F8D45A'>SCORE:</span> "
                        + score
                        + "</html>");

        mistakesLabel.setText(
                "<html>MISTAKES: <span style='color:#E04848'>"
                        + mistakes + "/" + MAX_MISTAKES
                        + "</span></html>");
    }

    private void installKeyboardShortcuts() {
        InputMap in = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap act = getActionMap();

        for (int d = 2; d <= 9; d++) {
            final int digit = d;

            Action a = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isShowing()) onDigit(digit);
                }
            };

            in.put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + d, 0), "digit" + d);
            in.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + d, 0), "digit" + d);
            act.put("digit" + d, a);
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            final char letter = ch;

            in.put(KeyStroke.getKeyStroke(KeyEvent.VK_A + (ch - 'A'), 0),
                    "letter" + ch);

            act.put("letter" + ch, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isShowing()) typeLetter(letter);
                }
            });
        }

        in.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "back");
        act.put("back", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShowing()) deleteLast();
            }
        });

        in.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clear");
        act.put("clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShowing()) onClear();
            }
        });

        in.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
        act.put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShowing()) onSubmit();
            }
        });
    }
}