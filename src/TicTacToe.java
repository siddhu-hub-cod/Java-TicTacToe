import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe implements ActionListener {

    JFrame frame = new JFrame("Tic Tac Toe");
    JButton[] buttons = new JButton[9];
    JLabel status = new JLabel("Player X's turn", JLabel.CENTER);
    JButton restartButton = new JButton("Restart Game");
    boolean xTurn = true;   // true = X, false = O

    public TicTacToe() {
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top: status label
        status.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(status, BorderLayout.NORTH);

        // Center: 3x3 board
        JPanel board = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 50));
            buttons[i].addActionListener(this);
            board.add(buttons[i]);
        }
        frame.add(board, BorderLayout.CENTER);

        // Bottom: restart button
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(e -> restartGame());
        frame.add(restartButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Runs every time a board button is clicked
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;   // box already used

        if (xTurn) {
            clicked.setText("X");
            status.setText("Player O's turn");
        } else {
            clicked.setText("O");
            status.setText("Player X's turn");
        }
        xTurn = !xTurn;

        checkWinner();
    }

    void checkWinner() {
        int[][] wins = {
                {0,1,2}, {3,4,5}, {6,7,8},   // rows
                {0,3,6}, {1,4,7}, {2,5,8},   // columns
                {0,4,8}, {2,4,6}             // diagonals
        };

        for (int[] w : wins) {
            String a = buttons[w[0]].getText();
            String b = buttons[w[1]].getText();
            String c = buttons[w[2]].getText();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                endGame("Player " + a + " wins!");
                return;
            }
        }

        // Draw check: all boxes filled
        boolean full = true;
        for (JButton b : buttons) {
            if (b.getText().equals("")) full = false;
        }
        if (full) endGame("It's a draw!");
    }

    void endGame(String message) {
        status.setText(message);
        for (JButton b : buttons) b.setEnabled(false);   // stop further clicks
        JOptionPane.showMessageDialog(frame, message);
    }

    // Clears the board and starts a new game
    void restartGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
        xTurn = true;
        status.setText("Player X's turn");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}