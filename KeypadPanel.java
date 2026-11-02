import javax.swing.*;
import java.awt.*;

/** The 3x4 keypad: 1-9, CLEAR, 0, SUBMIT. Pass a null listener for a display-only keypad (tutorial). */
public class KeypadPanel extends JPanel {

    public interface Listener {
        void onDigit(int digit);
        void onClear();
        void onSubmit();
    }

    private final KeyButton[] keys = new KeyButton[12];

    public KeypadPanel(Listener l) {
        setOpaque(false);
        setLayout(new GridLayout(4, 3, 8, 8));

        for (int d = 1; d <= 9; d++) {
            final int digit = d;
            keys[d - 1] = new KeyButton(String.valueOf(d), spaced(GameLogic.LETTERS[d]), Theme.KEY);
            if (l != null) keys[d - 1].addActionListener(e -> l.onDigit(digit));
        }
        keys[9] = new KeyButton("CLEAR", null, Theme.KEY_CLEAR);
        keys[10] = new KeyButton("0", null, Theme.KEY);
        keys[11] = new KeyButton("SUBMIT", null, Theme.BLUE);
        if (l != null) {
            keys[9].addActionListener(e -> l.onClear());
            keys[11].addActionListener(e -> l.onSubmit());
        }
        for (KeyButton k : keys) add(k);
    }

    /** Darken one digit key (used by the tutorial to point at a key). */
    public void highlight(int digit) {
        keys[digit == 0 ? 10 : digit - 1].setFill(Theme.KEY_HOT);
    }

    private static String spaced(String letters) {
        if (letters.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        for (char c : letters.toCharArray()) sb.append(sb.length() > 0 ? " " : "").append(c);
        return sb.toString();
    }

    /** One key, custom painted. */
    private static class KeyButton extends JButton {
        private final String main, sub;
        private Color fill;

        KeyButton(String main, String sub, Color fill) {
            this.main = main;
            this.sub = sub;
            this.fill = fill;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFocusable(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        void setFill(Color c) {
            fill = c;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Theme.aa(g2);
            g2.setColor(getModel().isPressed() ? fill.darker() : fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);

            int h = getHeight();
            g2.setColor(Color.WHITE);
            if (sub == null) {
                g2.setFont(Theme.font(Font.BOLD, (int) (h * (main.length() > 1 ? 0.36 : 0.42))));
                center(g2, main, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
            } else {
                g2.setFont(Theme.font(Font.BOLD, (int) (h * 0.36)));
                center(g2, main, (int) (h * 0.47));
                g2.setColor(new Color(0x141414));
                g2.setFont(Theme.font(Font.PLAIN, (int) (h * 0.2)));
                center(g2, sub, (int) (h * 0.84));
            }
            g2.dispose();
        }

        private void center(Graphics2D g2, String s, int baseline) {
            g2.drawString(s, (getWidth() - g2.getFontMetrics().stringWidth(s)) / 2, baseline);
        }
    }
}