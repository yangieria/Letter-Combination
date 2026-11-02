import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Yellow speech-bubble used for the call-outs on the How to Play screens. Word-wraps its own text. */
public class Bubble extends RoundedPanel {
    private final String text;

    public Bubble(String text) {
        super(Theme.YELLOW, Color.WHITE, 30, 3);
        this.text = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        Theme.aa(g2);
        g2.setFont(Theme.font(Font.BOLD, 9));
        g2.setColor(new Color(0x1A1A1A));
        FontMetrics fm = g2.getFontMetrics();

        int maxW = getWidth() - 22;
        List<String> lines = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (String word : text.split(" ")) {
            String test = cur.length() == 0 ? word : cur + " " + word;
            if (fm.stringWidth(test) > maxW && cur.length() > 0) {
                lines.add(cur.toString());
                cur = new StringBuilder(word);
            } else {
                cur = new StringBuilder(test);
            }
        }
        lines.add(cur.toString());

        int lineH = fm.getHeight() - 1;
        int y = (getHeight() - lineH * lines.size()) / 2 + fm.getAscent();
        for (String line : lines) {
            g2.drawString(line, (getWidth() - fm.stringWidth(line)) / 2, y);
            y += lineH;
        }
        g2.dispose();
    }
}