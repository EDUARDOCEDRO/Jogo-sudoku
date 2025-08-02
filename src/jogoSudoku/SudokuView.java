package jogoSudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SudokuView extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private JButton checkButton, newGameButton;
    private SudokuController controller;
    
    public SudokuView(SudokuController controller) {
        this.controller = controller;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        
        JPanel gridPanel = createGridPanel();
        JPanel buttonPanel = createButtonPanel();
        
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                styleCell(cells[row][col], row, col);
                gridPanel.add(cells[row][col]);
            }
        }
        
        return gridPanel;
    }
    
    private void styleCell(JTextField cell, int row, int col) {
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 20));
        
        if (row % 3 == 0 && row != 0) {
            cell.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        }
        if (col % 3 == 0 && col != 0) {
            cell.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
        }
        
        cell.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        checkButton = new JButton("Verificar");
        newGameButton = new JButton("Novo Jogo");
        
        checkButton.addActionListener(e -> controller.checkSolution());
        newGameButton.addActionListener(e -> controller.newGame());
        
        buttonPanel.add(checkButton);
        buttonPanel.add(newGameButton);
        
        return buttonPanel;
    }
    
    public void updateBoard(int[][] puzzle, int[][] solution) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(new Color(240, 240, 240));
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }
    
    public int[][] getUserInput() {
        int[][] userInput = new int[9][9];
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    userInput[row][col] = cells[row][col].getText().isEmpty() ? 
                        0 : Integer.parseInt(cells[row][col].getText());
                } catch (NumberFormatException e) {
                    userInput[row][col] = -1; // Valor inválido
                }
            }
        }
        
        return userInput;
    }
    
    public void showValidationResult(boolean isValid, boolean isComplete) {
        if (!isComplete) {
            JOptionPane.showMessageDialog(this, "O tabuleiro não está completo!", "Verificação", JOptionPane.WARNING_MESSAGE);
        } else if (isValid) {
            JOptionPane.showMessageDialog(this, "Parabéns! Solução correta!", "Verificação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            highlightIncorrectCells();
            JOptionPane.showMessageDialog(this, "Há erros no tabuleiro. Corrija as células marcadas.", "Verificação", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void highlightIncorrectCells() {
        int[][] solution = controller.getSolution();
        int[][] userInput = getUserInput();
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (userInput[row][col] != solution[row][col]) {
                    cells[row][col].setBackground(Color.PINK);
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }
}