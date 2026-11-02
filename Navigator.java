public interface Navigator {

    String HOME = "HOME";
    String HOW1 = "HOW1";
    String HOW2 = "HOW2";
    String HOW3 = "HOW3";
    String GAME = "GAME";
    String RESULT = "RESULT";
    String HISTORY = "HISTORY";

    void show(String screen);

    void startGame(boolean resetScore);

    void showResult(boolean won, int score, int found, int total, int mistakes);

    void showHistory();
    void backFromHistory();

}