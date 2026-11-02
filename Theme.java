import javax.swing.*;
import java.awt.*;

/** Shared colors, fonts and small drawing helpers. */
public final class Theme {
    private Theme() {}

    public static final int W = 480;   // window content size
    public static final int H = 680;

    public static final Color BG         = new Color(0x0A1330);
    public static final Color PANEL      = new Color(0x13224A);
    public static final Color DARK       = new Color(0x08122B);
    public static final Color BLUE       = new Color(0x4A80E4);
    public static final Color YELLOW     = new Color(0xF8D45A);
    public static final Color ORANGE     = new Color(0xF2A03D);
    public static final Color GREEN      = new Color(0x55BC6B);
    public static final Color GREEN_LT   = new Color(0x8ED96A);
    public static final Color RED        = new Color(0xE04848);
    public static final Color DARK_RED   = new Color(0x9B2A26);
    public static final Color KEY        = new Color(0xB8B8B8);
    public static final Color KEY_HOT    = new Color(0x7B7B7B);
    public static final Color KEY_CLEAR  = new Color(0x6B777B);
    public static final Color GRAY_TEXT  = new Color(0x7D8494);
    public static final Color TILE       = new Color(0x505766);
    public static final Color TILE_DARK  = new Color(0x2E3A4C);
    public static final Color TILE_BLUE  = new Color(0x224177);

    private static final String FAMILY = pickFamily();

    private static String pickFamily() {
        String[] installed = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String want : new String[]{"Montserrat", "Poppins"})
            for (String have : installed)
                if (have.equalsIgnoreCase(want)) return have;
        return Font.SANS_SERIF;
    }

    public static Font font(int style, int size) {
        return new Font(FAMILY, style, size);
    }

    public static JLabel label(String text, int style, int size, Color color, int align) {
        JLabel l = new JLabel(text, align);
        l.setFont(font(style, size));
        l.setForeground(color);
        return l;
    }

    /** Multi-line wrapped text (html), anchored to the top-left of its bounds. */
    public static JLabel paragraph(String html, int size, Color color) {
        JLabel l = label(html, Font.BOLD, size, color, JLabel.LEFT);
        l.setVerticalAlignment(JLabel.TOP);
        return l;
    }

    /** Sets bounds and returns the component (handy for add(place(...))). */
    public static <T extends JComponent> T place(T c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        return c;
    }

    /** "HOW TO" in white + "PLAY" in yellow, as one label. */
    public static JLabel twoTone(String white, String yellow, int size) {
        String html = "<html><span style='color:#FFFFFF'>" + white + "</span> <span style='color:#F8D45A'>"
                + yellow + "</span></html>";
        return label(html, Font.BOLD, size, Color.WHITE, JLabel.CENTER);
    }

    public static void aa(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    /** Dashed white line with an arrow head at (x2, y2). Used for the tutorial call-outs. */
    public static void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) g.create();
        aa(g2);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{5f, 4f}, 0f));
        g2.drawLine(x1, y1, x2, y2);
        g2.setStroke(new BasicStroke(2f));
        double a = Math.atan2(y2 - y1, x2 - x1);
        int len = 10;
        int[] xs = {x2, (int) (x2 - len * Math.cos(a - 0.45)), (int) (x2 - len * Math.cos(a + 0.45))};
        int[] ys = {y2, (int) (y2 - len * Math.sin(a - 0.45)), (int) (y2 - len * Math.sin(a + 0.45))};
        g2.fillPolygon(xs, ys, 3);
        g2.dispose();
    }
}