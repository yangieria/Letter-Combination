import javax.swing.*;
import java.awt.*;

/** Page 3 - How to Play (1/3): introduces the main parts of the game. */
public class HowToPlay1Panel extends JPanel {

    public HowToPlay1Panel(Navigator nav) {
        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        // call-out bubbles
        add(Theme.place(new Bubble("Random 2-3 Digit Number"), 10, 98, 112, 68));
        add(Theme.place(new Bubble("Total combinations you need to find"),
                350, 74, 124, 100));
        add(Theme.place(new Bubble("Enter your letter combination here"),
                354, 256, 118, 68));

        add(Theme.place(Theme.twoTone("HOW TO", "PLAY", 40), 0, 14, W, 54));

        // generated number
        RoundedPanel find = new RoundedPanel(Theme.BG, Theme.BLUE, 32, 3);
        find.add(Theme.place(Theme.label("FIND COMBINATIONS FOR",
                Font.PLAIN, 13, Color.WHITE, JLabel.CENTER), 0, 8, 220, 20));
        find.add(Theme.place(Theme.label("652",
                Font.BOLD, 46, Theme.YELLOW, JLabel.CENTER), 0, 28, 220, 56));
        add(Theme.place(find, 130, 78, 220, 92));

        // total combinations
        add(Theme.place(Theme.label("TOTAL COMBINATIONS: 27",
                Font.PLAIN, 14, Color.WHITE, JLabel.CENTER), 0, 178, W, 22));

        // answer box
        RoundedPanel answer = new RoundedPanel(Theme.PANEL, Theme.BLUE, 32, 3);
        answer.add(Theme.place(Theme.label("YOUR ANSWER",
                Font.PLAIN, 15, Color.WHITE, JLabel.CENTER), 0, 8, 360, 22));
        answer.add(Theme.place(Theme.label("_   _   _",
                Font.BOLD, 32, new Color(0x3A4A78), JLabel.CENTER), 0, 35, 360, 42));
        add(Theme.place(answer, 60, 208, 360, 100));

        // score and mistakes
        add(Theme.place(Theme.label(
                "<html><span style='color:#F8D45A'>SCORE:</span> 0</html>",
                Font.PLAIN, 14, Color.WHITE, JLabel.LEFT), 60, 322, 150, 22));

        add(Theme.place(Theme.label(
                "<html>MISTAKES: <span style='color:#E04848'>0/3</span></html>",
                Font.PLAIN, 14, Color.WHITE, JLabel.RIGHT), 230, 322, 190, 22));

        // keypad
        add(Theme.place(new KeypadPanel(null), 70, 352, 340, 236));

        // next button
        RoundedButton next = new RoundedButton("Next", Theme.ORANGE, Color.WHITE, 22, 26);
        next.addActionListener(e -> nav.show(Navigator.HOW2));
        add(Theme.place(next, (W - 180) / 2, 608, 180, 46));
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D) g;

        Theme.drawArrow(g2, 122, 128, 196, 120);  // random number
        Theme.drawArrow(g2, 384, 176, 340, 189);  // total combinations
        Theme.drawArrow(g2, 366, 270, 318, 240);  // answer box
    }
}