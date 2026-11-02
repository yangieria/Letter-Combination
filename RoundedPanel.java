import javax.swing.*;
import java.awt.*;

/** A panel with rounded corners, optional fill and optional colored border. Uses null layout by default. */
public class RoundedPanel extends JPanel {
    private final Color fill, border;
    private final int radius, borderWidth;

    public RoundedPanel(Color fill, Color border, int radius, int borderWidth) {
        this.fill = fill;
        this.border = border;
        this.radius = radius;
        this.borderWidth = borderWidth;
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Theme.aa(g2);
        if (fill != null) {
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        if (border != null && borderWidth > 0) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(borderWidth));
            int o = borderWidth / 2;
            g2.drawRoundRect(o, o, getWidth() - borderWidth - 1, getHeight() - borderWidth - 1, radius, radius);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}