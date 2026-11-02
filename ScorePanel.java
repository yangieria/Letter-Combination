import javax.swing.*;
import java.awt.*;

/** Page 7 / 8 - Shows the result after a round. */
public class ScorePanel extends JPanel {

    private final JLabel title2 = Theme.label(
            "", Font.BOLD, 42, Theme.YELLOW, JLabel.CENTER);

    private final JLabel subtitle = Theme.label(
            "", Font.BOLD, 16, Theme.GRAY_TEXT, JLabel.CENTER);

    private final JLabel iconLabel = new JLabel();

    private final JLabel scoreValue = Theme.label(
            "0", Font.BOLD, 56, Theme.YELLOW, JLabel.CENTER);

    private final JLabel foundValue = Theme.label(
            "0", Font.BOLD, 44, Theme.YELLOW, JLabel.CENTER);

    private final JLabel totalValue = Theme.label(
            "0", Font.BOLD, 44, Theme.YELLOW, JLabel.CENTER);

    private final JLabel mistakeValue = Theme.label(
            "0/3", Font.BOLD, 20, Theme.RED, JLabel.CENTER);

    private final RoundedButton answers;
    private final RoundedButton nextRound;
    private final RoundedButton exit;
    private final RoundedButton tryAgain;

    public ScorePanel(Navigator nav) {
        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        add(Theme.place(Theme.label("ROUND",
                        Font.BOLD, 48, Color.WHITE, JLabel.CENTER),
                0, 40, W, 58));

        add(Theme.place(title2, 0, 96, W, 52));
        add(Theme.place(subtitle, 0, 152, W, 24));

        RoundedPanel scoreBox = new RoundedPanel(
                Theme.PANEL, Theme.BLUE, 40, 3);

        scoreBox.add(Theme.place(
                iconLabel, 20, 20, 120, 130));

        scoreBox.add(Theme.place(Theme.label(
                        "YOUR SCORE",
                        Font.BOLD, 28, Color.WHITE, JLabel.CENTER),
                135, 28, 235, 40));

        scoreBox.add(Theme.place(
                scoreValue, 135, 72, 235, 70));

        add(Theme.place(
                scoreBox, 50, 195, 380, 170));

        RoundedPanel stats = new RoundedPanel(
                Theme.DARK, Theme.BLUE, 34, 3);

        stats.add(Theme.place(Theme.label(
                        "<html><div style='text-align:center'>"
                                + "COMBINATION<br>FOUND</div></html>",
                        Font.BOLD, 14, Color.WHITE, JLabel.CENTER),
                0, 12, 189, 40));

        stats.add(Theme.place(
                foundValue, 0, 52, 189, 50));

        JPanel divider = new JPanel();
        divider.setBackground(Theme.BLUE);

        stats.add(Theme.place(
                divider, 189, 6, 2, 103));

        stats.add(Theme.place(Theme.label(
                        "<html><div style='text-align:center'>"
                                + "TOTAL<br>COMBINATION</div></html>",
                        Font.BOLD, 14, Color.WHITE, JLabel.CENTER),
                191, 12, 189, 40));

        stats.add(Theme.place(
                totalValue, 191, 52, 189, 50));

        add(Theme.place(
                stats, 50, 385, 380, 115));

        add(Theme.place(Theme.label(
                        "MISTAKES:",
                        Font.BOLD, 15, Color.WHITE, JLabel.RIGHT),
                125, 508, 110, 25));

        add(Theme.place(
                mistakeValue, 240, 508, 80, 25));

        answers = new RoundedButton(
                "ANSWERS", Theme.BLUE, Color.WHITE, 22, 20);

        answers.addActionListener(e -> nav.showHistory());

        add(Theme.place(
                answers, 140, 540, 200, 42));

        nextRound = new RoundedButton(
                "NEXT ROUND",
                Theme.GREEN, Color.WHITE, 30, 64);

        nextRound.setIcon(
                Icons.play(32, Color.WHITE));

        nextRound.setBorderColor(
                Theme.GREEN_LT);

        nextRound.addActionListener(
                e -> nav.startGame(false));

        add(Theme.place(
                nextRound, 60, 595, 360, 60));

        exit = new RoundedButton(
                "EXIT",
                Theme.KEY, Color.WHITE, 28, 64);

        exit.setIcon(Icons.back(30));
        exit.setBorderColor(Color.BLACK);

        exit.addActionListener(
                e -> nav.show(Navigator.HOME));

        add(Theme.place(
                exit, 30, 595, 200, 60));

        tryAgain = new RoundedButton(
                "TRY AGAIN",
                Theme.ORANGE, Color.WHITE, 22, 64);

        tryAgain.setIcon(
                Icons.refresh(28, Color.WHITE));

        tryAgain.setBorderColor(
                new Color(0x7A4A12));

        tryAgain.addActionListener(
                e -> nav.startGame(true));

        add(Theme.place(
                tryAgain, 245, 595, 205, 60));
    }

    public void showResult(boolean won, int score, int found,
                           int total, int mistakes) {

        title2.setText(
                won ? "COMPLETE!" : "FAILED!");

        title2.setForeground(
                won ? Theme.YELLOW : Theme.DARK_RED);

        subtitle.setText(
                won
                        ? "Great job! Here's your result for the round"
                        : "Better luck next time!");

        iconLabel.setIcon(
                won
                        ? Icons.stars(120, Theme.YELLOW)
                        : Icons.star(90, Theme.YELLOW));

        iconLabel.setHorizontalAlignment(
                JLabel.CENTER);

        scoreValue.setText(
                String.valueOf(score));

        foundValue.setText(
                String.valueOf(found));

        totalValue.setText(
                String.valueOf(total));

        mistakeValue.setText(
                mistakes + "/3");

        mistakeValue.setForeground(
                mistakes >= 3
                        ? Theme.RED
                        : Theme.YELLOW);

        nextRound.setVisible(won);
        exit.setVisible(!won);
        tryAgain.setVisible(!won);
    }
}