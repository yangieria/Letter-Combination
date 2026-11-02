import javax.swing.*;
import java.awt.*;

/** Page 5 - How to Play (3/3): explains mistakes, duplicates, and completing the round. */
public class HowToPlay3Panel extends JPanel {

    public HowToPlay3Panel(Navigator nav) {
        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        add(Theme.place(Theme.twoTone("HOW TO", "PLAY", 40), 0, 14, W, 54));

        // duplicates
        RoundedPanel dup = new RoundedPanel(null, Theme.BLUE, 32, 3);
        dup.add(Theme.place(Theme.label("WATCH OUT FOR",
                Font.BOLD, 20, Color.WHITE, JLabel.LEFT), 20, 14, 340, 28));
        dup.add(Theme.place(Theme.label("DUPLICATES!",
                Font.BOLD, 27, Theme.YELLOW, JLabel.LEFT), 20, 42, 340, 36));

        dup.add(Theme.place(new JLabel(Icons.warning(56, Theme.YELLOW)),
                18, 92, 60, 60));

        dup.add(Theme.place(Theme.paragraph(
                "<html>Submit each combination only once. "
                        + "Duplicate answers count as a mistake.</html>",
                14, Color.WHITE), 92, 88, 272, 70));

        dup.add(Theme.place(Theme.label(
                        "<html>MISTAKES: <span style='color:#E04848'>0/3</span></html>",
                        Font.BOLD, 14, Color.WHITE, JLabel.CENTER),
                92, 150, 272, 24));

        add(Theme.place(dup, 50, 84, 380, 190));

        // complete the round
        RoundedPanel done = new RoundedPanel(null, Theme.BLUE, 32, 3);
        done.add(Theme.place(Theme.twoTone("COMPLETE THE", "ROUND", 24),
                8, 14, 364, 34));

        done.add(Theme.place(new JLabel(Icons.check(56, Theme.GREEN_LT)),
                18, 66, 60, 60));

        done.add(Theme.place(Theme.paragraph(
                "<html>Find all possible combinations before reaching 3 mistakes.</html>",
                14, Color.WHITE), 92, 64, 272, 64));

        // round complete message
        RoundedPanel complete = new RoundedPanel(Theme.GREEN, Theme.GREEN_LT, 34, 3);
        complete.add(Theme.place(Theme.label("ROUND COMPLETE!",
                        Font.BOLD, 22, Color.WHITE, JLabel.CENTER),
                0, 8, 324, 30));

        complete.add(Theme.place(Theme.label(
                        "<html><div style='text-align:center'>"
                                + "Find all combinations to complete the round<br>"
                                + "and earn your bonus!</div></html>",
                        Font.PLAIN, 13, Color.WHITE, JLabel.CENTER),
                0, 38, 324, 42));

        done.add(Theme.place(complete, 28, 150, 324, 88));
        add(Theme.place(done, 50, 292, 380, 258));

        // navigation
        RoundedButton back = new RoundedButton("<", Theme.BLUE, Color.WHITE, 22, 20);
        back.addActionListener(e -> nav.show(Navigator.HOW2));
        add(Theme.place(back, 118, 580, 56, 46));

        RoundedButton gotIt = new RoundedButton("Got It!", Theme.ORANGE, Color.WHITE, 22, 20);
        gotIt.addActionListener(e -> nav.show(Navigator.HOME));
        add(Theme.place(gotIt, 182, 580, 180, 46));
    }
}