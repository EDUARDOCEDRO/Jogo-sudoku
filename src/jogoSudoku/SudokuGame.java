package jogoSudoku;

import javax.swing.*;

public class SudokuGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuController controller = new SudokuController();
            controller.startGame();
        });
    }
}