import javax.swing.*;
import java.awt.*;

/** Page 4 - How to Play (2/3): explains how to use the keypad to enter letters. */
public class HowToPlay2Panel extends JPanel {

    public HowToPlay2Panel(Navigator nav) {
        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        add(Theme.place(new Bubble("Press the same key again to change letters"),
                298, 144, 120, 70));

        add(Theme.place(Theme.twoTone("HOW TO", "PLAY", 40), 0, 14, W, 54));

        // keypad instruction
        add(Theme.place(Theme.paragraph(
                "<html>Each number represents a group of letters. "
                        + "Press the same number repeatedly to cycle through them.</html>",
                17, Color.WHITE), 42, 74, 396, 70));

        // keypad
        RoundedPanel pad = new RoundedPanel(Theme.DARK, null, 34, 0);
        pad.setLayout(new BorderLayout());
        pad.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        KeypadPanel keypad = new KeypadPanel(null);
        keypad.highlight(2);
        pad.add(keypad, BorderLayout.CENTER);
        add(Theme.place(pad, 50, 212, 380, 262));

        // example: 2 -> A -> B -> C
        RoundedPanel example = new RoundedPanel(new Color(0x6A93BB), null, 44, 0);
        example.add(Theme.place(Theme.label("EXAMPLE:",
                Font.BOLD, 13, Theme.BG, JLabel.LEFT), 16, 38, 84, 20));

        String[] letters = {"2", "A", "B", "C"};
        int x = 100, size = 52;

        for (int i = 0; i < letters.length; i++) {
            example.add(Theme.place(
                    new Token(letters[i],
                            i == 0 ? Theme.YELLOW : Theme.BG,
                            i == 0 ? Theme.BG : Color.WHITE),
                    x, 24, size, size));

            if (i < letters.length - 1) {
                example.add(Theme.place(new ArrowGlyph(),
                        x + size + 1, 24, 24, size));
            }

            x += size + 26;
        }

        add(Theme.place(example, 30, 492, 420, 100));

        // submit reminder
        add(Theme.place(Theme.label(
                        "Press SUBMIT when your answer is ready.",
                        Font.BOLD, 14, Theme.YELLOW, JLabel.CENTER),
                0, 594, W, 20));

        // navigation
        RoundedButton back = new RoundedButton("<", Theme.BLUE, Color.WHITE, 22, 20);
        back.addActionListener(e -> nav.show(Navigator.HOW1));
        add(Theme.place(back, 118, 620, 56, 46));

        RoundedButton next = new RoundedButton("Next", Theme.ORANGE, Color.WHITE, 22, 20);
        next.addActionListener(e -> nav.show(Navigator.HOW3));
        add(Theme.place(next, 182, 620, 180, 46));
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Theme.drawArrow((Graphics2D) g, 304, 204, 252, 238);
    }

    /** Circle containing a number or letter. */
    private static class Token extends JComponent {
        private final String text;
        private final Color fill, fg;

        Token(String text, Color fill, Color fg) {
            this.text = text;
            this.fill = fill;
            this.fg = fg;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Theme.aa(g2);

            g2.setColor(fill);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(fg);
            g2.setFont(Theme.font(Font.BOLD, 28));

            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(text,
                    (getWidth() - fm.stringWidth(text)) / 2,
                    (getHeight() + fm.getAscent()) / 2 - 3);

            g2.dispose();
        }
    }

    /** Small arrow between the example circles. */
    private static class ArrowGlyph extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Theme.aa(g2);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(
                    3f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            int y = getHeight() / 2;
            int w = getWidth();

            g2.drawLine(2, y, w - 3, y);
            g2.drawLine(w - 10, y - 7, w - 3, y);
            g2.drawLine(w - 10, y + 7, w - 3, y);

            g2.dispose();
        }
    }
}