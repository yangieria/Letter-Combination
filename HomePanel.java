import javax.swing.*;
import java.awt.*;

/**
 * Page 1 - Homepage.
 * Shows New Game normally.
 * When a game is in progress, Continue and New Game are shown.
 */
public class HomePanel extends JPanel {

    private final RoundedButton continueButton;
    private final RoundedButton newGameButton;

    public HomePanel(Navigator nav) {
        setLayout(null);
        setBackground(Theme.BG);
        int W = Theme.W;

        // title
        add(Theme.place(Theme.label("LETTER",
                        Font.BOLD, 54, Color.WHITE, JLabel.CENTER),
                0, 55, W, 64));

        add(Theme.place(Theme.label("COMBINATION",
                        Font.BOLD, 44, Theme.YELLOW, JLabel.CENTER),
                0, 115, W, 54));

        // centered subheading
        add(Theme.place(Theme.label(
                        "<html><div style='text-align:center;'>"
                                + "One digit at a time.<br>"
                                + "One combination at a time."
                                + "</div></html>",
                        Font.BOLD, 20, Theme.GRAY_TEXT, JLabel.CENTER),
                0, 195, W, 64));

        // how to play
        RoundedButton how = new RoundedButton(
                "How to Play", Theme.BLUE, Color.WHITE, 22, 26);

        how.setIcon(Icons.help(26, Color.WHITE));
        how.addActionListener(e -> nav.show(Navigator.HOW1));

        add(Theme.place(how,
                (W - 240) / 2, 290, 240, 54));

        // continue button
        continueButton = new RoundedButton(
                "Continue", new Color(0x6A93BB), Color.WHITE, 28, 26);

        continueButton.setIcon(Icons.play(26, Color.WHITE));
        continueButton.setBorderColor(Theme.BLUE);
        continueButton.addActionListener(e -> nav.show(Navigator.GAME));

        add(Theme.place(continueButton,
                (W - 300) / 2, 368, 300, 56));

        continueButton.setVisible(false);

        // new game button
        newGameButton = new RoundedButton(
                "New Game", Theme.YELLOW, Color.WHITE, 28, 26);

        newGameButton.setIcon(Icons.play(26, Color.WHITE));
        newGameButton.setBorderColor(Theme.ORANGE);
        newGameButton.addActionListener(e -> nav.startGame(true));

        add(Theme.place(newGameButton,
                (W - 300) / 2, 368, 300, 56));

        // decorative keypad
        RoundedPanel grid = new RoundedPanel(
                Theme.DARK, null, 28, 0);

        grid.setLayout(new GridLayout(4, 3, 8, 8));
        grid.setBorder(BorderFactory.createEmptyBorder(
                16, 20, 16, 20));

        for (int i = 0; i < 12; i++) {
            JPanel tile = new JPanel();

            tile.setBackground(
                    i == 9 ? Theme.TILE_DARK
                            : i == 11 ? Theme.TILE_BLUE
                            : Theme.TILE);

            grid.add(tile);
        }

        add(Theme.place(grid,
                40, 472, 400, 190));
    }

    /**
     * Called every time the Home screen is shown.
     */
    public void refreshState(boolean inProgress) {

        if (inProgress) {
            // active game: show both buttons
            continueButton.setVisible(true);

            continueButton.setBounds(
                    (Theme.W - 300) / 2,
                    360,
                    300,
                    56);

            newGameButton.setBounds(
                    (Theme.W - 300) / 2,
                    424,
                    300,
                    56);

        } else {
            // no active game: only show New Game
            continueButton.setVisible(false);

            newGameButton.setBounds(
                    (Theme.W - 300) / 2,
                    368,
                    300,
                    56);
        }

        revalidate();
        repaint();
    }
}