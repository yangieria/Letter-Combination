import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame implements Navigator {

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    private final HomePanel home = new HomePanel(this);
    private final GamePanel game = new GamePanel(this);
    private final ScorePanel result = new ScorePanel(this);
    private final HistoryPanel history = new HistoryPanel(this);

    private boolean gameInProgress = false;
    private String currentScreen = HOME;
    private String historyReturnScreen = GAME;

    public AppFrame() {
        super("Letter Combination");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        root.add(home, HOME);
        root.add(new HowToPlay1Panel(this), HOW1);
        root.add(new HowToPlay2Panel(this), HOW2);
        root.add(new HowToPlay3Panel(this), HOW3);
        root.add(game, GAME);
        root.add(result, RESULT);
        root.add(history, HISTORY);

        root.setPreferredSize(new Dimension(Theme.W, Theme.H));

        setContentPane(root);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        show(HOME);
    }

    @Override
    public void show(String screen) {
        if (HOME.equals(screen)) {
            home.refreshState(gameInProgress);
        }

        currentScreen = screen;
        cards.show(root, screen);
    }

    @Override
    public void startGame(boolean resetScore) {
        game.startRound(resetScore);
        gameInProgress = true;
        show(GAME);
    }

    @Override
    public void showResult(boolean won, int score, int found,
                           int total, int mistakes) {

        gameInProgress = false;

        result.showResult(
                won,
                score,
                found,
                total,
                mistakes
        );

        show(RESULT);
    }

    @Override
    public void showHistory() {
        historyReturnScreen = currentScreen;

        history.update(
                game.getDigits(),
                game.getAttempts(),
                game.getFoundCount(),
                game.getTotal()
        );

        show(HISTORY);
    }

    @Override
    public void backFromHistory() {
        show(historyReturnScreen);
    }
}