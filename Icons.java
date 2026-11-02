import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/** Small vector icons drawn in code (no image files needed). */
public final class Icons {
    private Icons() {}

    private abstract static class Base implements Icon {
        final int w, h;
        Base(int w, int h) { this.w = w; this.h = h; }
        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            Theme.aa(g2);
            g2.translate(x, y);
            draw(g2);
            g2.dispose();
        }
        abstract void draw(Graphics2D g);
    }

    public static Icon play(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(c);
                g.fillPolygon(new int[]{(int) (s * .2), (int) (s * .2), (int) (s * .92)},
                        new int[]{(int) (s * .05), (int) (s * .95), s / 2}, 3);
            }
        };
    }

    public static Icon help(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(c);
                g.setStroke(new BasicStroke(Math.max(2f, s / 12f)));
                g.drawOval(2, 2, s - 4, s - 4);
                g.setFont(Theme.font(Font.BOLD, (int) (s * .62)));
                FontMetrics fm = g.getFontMetrics();
                g.drawString("?", (s - fm.stringWidth("?")) / 2, (s - fm.getHeight()) / 2 + fm.getAscent());
            }
        };
    }

    public static Icon warning(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                float sw = Math.max(2.5f, s / 14f);
                g.setColor(c);
                g.setStroke(new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                Path2D t = new Path2D.Float();
                t.moveTo(s / 2f, sw);
                t.lineTo(s - sw, s - sw * 1.5f);
                t.lineTo(sw, s - sw * 1.5f);
                t.closePath();
                g.draw(t);
                g.draw(new Line2D.Float(s / 2f, s * .38f, s / 2f, s * .6f));
                g.fill(new Ellipse2D.Float(s / 2f - sw * .6f, s * .69f, sw * 1.2f, sw * 1.2f));
            }
        };
    }

    public static Icon check(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                float sw = Math.max(2.5f, s / 14f);
                g.setColor(c);
                g.setStroke(new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.drawOval(2, 2, s - 4, s - 4);
                Path2D p = new Path2D.Float();
                p.moveTo(s * .28f, s * .52f);
                p.lineTo(s * .44f, s * .68f);
                p.lineTo(s * .74f, s * .34f);
                g.draw(p);
            }
        };
    }

    private static Path2D star(double cx, double cy, double r) {
        Path2D.Double p = new Path2D.Double();
        for (int i = 0; i < 10; i++) {
            double rad = (i % 2 == 0) ? r : r * 0.48;
            double a = -Math.PI / 2 + i * Math.PI / 5;
            double x = cx + rad * Math.cos(a), y = cy + rad * Math.sin(a);
            if (i == 0) p.moveTo(x, y); else p.lineTo(x, y);
        }
        p.closePath();
        return p;
    }

    public static Icon star(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(c);
                g.fill(star(s / 2.0, s / 2.0 + s * .04, s / 2.0 - 1));
            }
        };
    }

    /** Three stars clustered together (Round Complete). */
    public static Icon stars(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(c);
                g.fill(star(s * .32, s * .30, s * .27));
                g.fill(star(s * .72, s * .55, s * .25));
                g.fill(star(s * .36, s * .76, s * .25));
            }
        };
    }

    public static Icon back(int s) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(Color.BLACK);
                g.fillOval(0, 0, s, s);
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(s / 9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Float(s * .27f, s / 2f, s * .74f, s / 2f));
                Path2D head = new Path2D.Float();
                head.moveTo(s * .5f, s * .27f);
                head.lineTo(s * .27f, s / 2f);
                head.lineTo(s * .5f, s * .73f);
                g.draw(head);
            }
        };
    }

    public static Icon refresh(int s, Color c) {
        return new Base(s, s) {
            void draw(Graphics2D g) {
                g.setColor(c);
                g.setStroke(new BasicStroke(s / 9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                double cx = s / 2.0, cy = s / 2.0, r = s * .34;
                g.draw(new Arc2D.Double(cx - r, cy - r, 2 * r, 2 * r, 50, 260, Arc2D.OPEN));
                double a = Math.toRadians(310);
                double ex = cx + r * Math.cos(a), ey = cy - r * Math.sin(a);
                double dx = -Math.sin(a), dy = -Math.cos(a);       // direction of travel
                double px = -dy, py = dx;                          // perpendicular
                Path2D head = new Path2D.Double();
                head.moveTo(ex + dx * s * .22, ey + dy * s * .22);
                head.lineTo(ex + px * s * .17, ey + py * s * .17);
                head.lineTo(ex - px * s * .17, ey - py * s * .17);
                head.closePath();
                g.fill(head);
            }
        };
    }
}