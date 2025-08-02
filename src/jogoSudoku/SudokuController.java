package jogoSudoku;

import javax.swing.*;

@SuppressWarnings("unused")
public class SudokuController {
    private SudokuModel model;
    private SudokuView view;
    
    public SudokuController() {
        model = new SudokuModel();
        view = new SudokuView(this);
    }
    
    public void startGame() {
        model.generateNewPuzzle();
        updateView();
        view.setVisible(true);
    }
    
    public void checkSolution() {
        int[][] userSolution = view.getUserInput();
        boolean isValid = model.validateSolution(userSolution);
        view.showValidationResult(isValid, model.isComplete(userSolution));
    }
    
    public void newGame() {
        model.generateNewPuzzle();
        updateView();
    }
    
    private void updateView() {
        view.updateBoard(model.getPuzzle(), model.getSolution());
    }
    
    public int[][] getSolution() {
        return model.getSolution();
    }
}