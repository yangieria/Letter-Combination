import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Metal look-and-feel so our custom colors look the same on Windows / macOS / Linux
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
    }
}