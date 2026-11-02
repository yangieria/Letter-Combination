    import javax.swing.*;
    import java.awt.*;
    import java.util.List;

    /** Tab that lists everything the player has submitted this round (newest first). */
    public class HistoryPanel extends JPanel {

        private final JLabel findLabel  = Theme.label("", Font.PLAIN, 15, Color.WHITE, JLabel.CENTER);
        private final JLabel countLabel = Theme.label("", Font.PLAIN, 15, Color.WHITE, JLabel.CENTER);
        private final JLabel emptyLabel = Theme.label("No answers yet", Font.PLAIN, 16, Theme.GRAY_TEXT, JLabel.CENTER);
        private final DefaultListModel<Attempt> model = new DefaultListModel<>();
        private final JScrollPane scroll;

        public HistoryPanel(Navigator nav) {
            setLayout(null);
            setBackground(Theme.BG);
            int W = Theme.W;

            add(Theme.place(Theme.twoTone("YOUR", "ANSWERS", 36), 0, 18, W, 50));
            add(Theme.place(findLabel, 0, 76, W, 22));
            add(Theme.place(countLabel, 0, 100, W, 22));

            RoundedPanel box = new RoundedPanel(Theme.PANEL, Theme.BLUE, 32, 3);

            JList<Attempt> list = new JList<>(model);
            list.setOpaque(false);
            list.setFocusable(false);
            list.setSelectionModel(new DefaultListSelectionModel() {
                @Override public void setSelectionInterval(int a, int b) { /* no selection */ }
            });
            list.setFixedCellHeight(34);
            list.setCellRenderer(new AttemptRenderer());

            scroll = new JScrollPane(list);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);
            box.add(Theme.place(scroll, 16, 14, 368, 402));
            box.add(Theme.place(emptyLabel, 16, 180, 368, 30));
            add(Theme.place(box, 40, 138, 400, 430));

            RoundedButton back = new RoundedButton("< Back", Theme.BLUE, Color.WHITE, 22, 20);
            back.addActionListener(e -> nav.backFromHistory());
            add(Theme.place(back, (W - 180) / 2, 596, 180, 46));
        }

        /** Refresh the list. Called every time the tab is opened. */
        public void update(String digits, List<Attempt> attempts, int found, int total) {
            findLabel.setText("<html>Find combinations for <span style='color:#F8D45A'><b>" + digits + "</b></span></html>");
            countLabel.setText("<html><span style='color:#8ED96A'>FOUND: " + found + "</span> &nbsp;|&nbsp; TOTAL COMBINATIONS: " + total + "</html>");
            model.clear();
            for (int i = attempts.size() - 1; i >= 0; i--) model.addElement(attempts.get(i));   // newest first
            emptyLabel.setVisible(attempts.isEmpty());
            scroll.setVisible(!attempts.isEmpty());
        }

        /** Draws one row: number, the letters, and CORRECT / WRONG / DUPLICATE. */
        private class AttemptRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean selected, boolean focus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, "", index, false, false);
                Attempt a = (Attempt) value;
                int number = model.size() - index;                // oldest = 1
                String color, word;
                switch (a.result) {
                    case CORRECT:   color = "#8ED96A"; word = "CORRECT";   break;
                    case DUPLICATE: color = "#F8D45A"; word = "DUPLICATE"; break;
                    default:        color = "#E04848"; word = "WRONG";     break;
                }
                l.setText("<html><table cellspacing='0' cellpadding='0'><tr>"
                        + "<td width='44' style='color:#7D8494'>" + number + ".</td>"
                        + "<td width='84' style='color:#FFFFFF'>" + a.text + "</td>"
                        + "<td style='color:" + color + "'>" + word + "</td>"
                        + "</tr></table></html>");
                l.setFont(Theme.font(Font.BOLD, 18));
                l.setOpaque(false);
                l.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));
                return l;
            }
        }
    }