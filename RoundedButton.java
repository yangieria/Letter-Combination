import javax.swing.*;
import java.awt.*;

/** Flat rounded button with optional icon and border. */
public class RoundedButton extends JButton {

    // Removed "final" so the fill color can change
    private Color fill;

    private final int radius;
    private Color border;

    public RoundedButton(String text, Color fill, Color fg, int fontSize, int radius) {
        super(text);

        this.fill = fill;
        this.radius = radius;

        setForeground(fg);
        setFont(Theme.font(Font.BOLD, fontSize));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
        setRolloverEnabled(true);
        setMargin(new Insets(0, 0, 0, 0));
        setIconTextGap(10);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Change the fill color of the button
    public RoundedButton setFillColor(Color c) {
        this.fill = c;
        repaint();
        return this;
    }

    // Change the border color of the button
    public RoundedButton setBorderColor(Color c) {
        this.border = c;
        repaint();
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        Theme.aa(g2);

        Color c = getModel().isPressed()
                ? fill.darker()
                : getModel().isRollover()
                ? fill.brighter()
                : fill;

        g2.setColor(c);

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radius,
                radius
        );

        if (border != null) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(3));

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    radius,
                    radius
            );
        }

        g2.dispose();

        super.paintComponent(g);
    }
}