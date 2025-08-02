package jogoSudoku;

import java.util.Random;

public class SudokuModel {
    private int[][] solution;
    private int[][] puzzle;
    
    public SudokuModel() {
        solution = new int[9][9];
        puzzle = new int[9][9];
    }
    
    public void generateNewPuzzle() {
        generateSolution(0, 0);
        copySolutionToPuzzle();
        removeNumbersFromPuzzle();
    }
    
    private void generateSolution(int row, int col) {
        if (row == 9) return;
        if (col == 9) {
            generateSolution(row + 1, 0);
            return;
        }
        if (solution[row][col] != 0) {
            generateSolution(row, col + 1);
            return;
        }
        
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numbers);
        
        for (int num : numbers) {
            if (isValidPlacement(row, col, num, solution)) {
                solution[row][col] = num;
                generateSolution(row, col + 1);
                if (isBoardFull(solution)) return;
                solution[row][col] = 0;
            }
        }
    }
    
    private boolean isValidPlacement(int row, int col, int num, int[][] board) {
        // Verificar linha e coluna
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        
        // Verificar quadrante 3x3
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    
    private boolean isBoardFull(int[][] board) {
        for (int[] row : board) {
            for (int num : row) {
                if (num == 0) return false;
            }
        }
        return true;
    }
    
    private void copySolutionToPuzzle() {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, puzzle[i], 0, 9);
        }
    }
    
    private void removeNumbersFromPuzzle() {
        Random random = new Random();
        int cellsToRemove = 40 + random.nextInt(15);
        
        while (cellsToRemove > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                cellsToRemove--;
            }
        }
    }
    
    public boolean validateSolution(int[][] userSolution) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (userSolution[row][col] != solution[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isComplete(int[][] userSolution) {
        for (int[] row : userSolution) {
            for (int num : row) {
                if (num == 0) return false;
            }
        }
        return true;
    }
    
    public int[][] getPuzzle() {
        return puzzle;
    }
    
    public int[][] getSolution() {
        return solution;
    }
}