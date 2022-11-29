import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Humeyra Copoglu
 * Date created: 11/15/2022
 */

public class GameOfLife extends JFrame implements Runnable{
    private static final int SIZE = 10;
    private static JPanel mainPanel = new JPanel(new GridLayout(SIZE, SIZE));
    private static JPanel subPanel = new JPanel();
    private static ArrayList<Cell> population = new ArrayList<Cell>();
    private static JButton[][] gameBoard = new JButton[SIZE][SIZE];
    private static boolean gameStart = false;
    private static Thread thread;

    public static void main(String[] args) {
        GameOfLife app = new GameOfLife();
        app.setSize(new Dimension(500,500));
        app.setLocationRelativeTo(null);
        initializeGameboard();
        JButton startButton = new JButton("Start");
        thread = new Thread(app);
        startButton.addActionListener(e -> startGame());
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> gameStart=false);
        subPanel.add(startButton);
        subPanel.add(stopButton);
        subPanel.setBackground(Color.blue);
        subPanel.setBounds(0,0,500,40);
        mainPanel.setBounds(17, 40, 450, 400);
        app.add(mainPanel);
        app.add(subPanel);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    /**
     * Initializes the gameboard on the Gridlayout that consists of JButtons. Each JButtons also
     * represents a Cell object.
     */
    private static void initializeGameboard() {
        for (int i = 0 ; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++) {
                Cell cell = new Cell(i, j);
                population.add(cell);
                gameBoard[i][j] = new JButton();
                gameBoard[i][j].setBackground(cell.getColor());
                gameBoard[i][j].addActionListener(e -> startFirstLife(e, cell));
                mainPanel.add(gameBoard[i][j]);
            }
        }
    }

    private static void startGame() {
        gameStart = true;
        System.out.println(thread.getState());
        if(!thread.isAlive()) {
            thread.start();
        }
    }

    /**
     * Checks the neighbouring cells and determines which cells die and live in the next generation.
     */
    private static void checkNeighbours() {
        ArrayList<Boolean> nextGen = new ArrayList<Boolean>();
        for (int i = 0; i < population.size(); i++) {
            Cell current = population.get(i);
            Cell topLeft, topMid, topRight, left, right, bottomLeft, bottomMid, bottomRight;
            topLeft = tryCell(i-(SIZE+1));
            topMid = tryCell(i-(SIZE));
            topRight = tryCell(i-(SIZE-1));
            left = tryCell(i-1);
            right = tryCell(i+1);
            bottomLeft = tryCell(i+(SIZE-1));
            bottomMid = tryCell(i+(SIZE));
            bottomRight = tryCell(i+(SIZE+1));
            Cell[] neighbours = {topLeft,topMid,topRight,left, right,bottomLeft,bottomMid,
                    bottomRight};
            int aliveNeighbours = countAliveNeighbours(neighbours);
            if (current.isAlive()) {
                if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                    // Any live cell with less than two neighbours or more than three neighbors
                    // dies.
                    nextGen.add(false);
                } else {
                    // Any live cell with two or three neighbors survives.
                    nextGen.add(true);
                }
            } else {
                if (aliveNeighbours == 3) {
                    // Any dead cell with exactly three live neighbors becomes a live cell.
                    nextGen.add(true);
                } else {
                    nextGen.add(false);
                }
            }
        }
        for (Cell cell:population) {
            int index = population.indexOf(cell);
            cell.setState(nextGen.get(index));
        }
    }

    /**
     * Counts the amount of Cell that are alive in the given Cell array.
     * @param neighbours Cell array
     * @return amount of alive Cells
     */
    private static int countAliveNeighbours(Cell[] neighbours) {
        int alive = 0;
        for (Cell cell:neighbours) {
            if (cell != null && cell.isAlive()) {
                alive++;
            }
        }
        return alive;
    }

    /**
     * Checks if there is a cell at the given position. If there is a cell the Cell object is
     * returned. If there is no cell null is returned.
     * @param i int position in list
     * @return Cell or null
     */
    private static Cell tryCell(int i) {
        try {
             return population.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("This cell has no neighbour at position:" + i);
            return null;
        }
    }



    private static void startFirstLife(ActionEvent e, Cell cell) {
        if (e.getSource() instanceof JButton && !gameStart) {
            cell.setState(true);
            ((JButton) e.getSource()).setBackground(cell.getColor());
        }
    }

    private static void updateGameboard() {
        for (int i = 0 ; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int index = i * SIZE + j;
                if (population.get(index).isAlive()) {
                    gameBoard[i][j].setBackground(Color.red);
                } else {
                    gameBoard[i][j].setBackground(Color.black);
                }
            }
        }
    }

    @Override
    public void run() {
        while (gameStart) {
            System.out.println("in thread");
            checkNeighbours();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException IEerr) {
                IEerr.printStackTrace();
            }
            System.out.println(population.toString());
            updateGameboard();
        }
    }
}
