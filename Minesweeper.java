import java.util.*;

public class Minesweeper {
    private int rows;
    private int cols;
    private int totalMines;
    private char[][] board;
    private boolean[][] mines;
    private boolean[][] revealed;

    /*
        Purpose: Initializes a new Minesweeper game with specified dimensions and number of mines.
        Inputs:
        rows (int): Number of rows in the game board.
        cols (int): Number of columns in the game board.
        totalMines (int): Total number of mines to be placed on the board.
        Outputs: None (constructor).
    */
    public Minesweeper(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.board = new char[rows][cols];
        this.mines = new boolean[rows][cols];
        this.revealed = new boolean[rows][cols];
        initializeBoard();
    }

    /*
        Purpose: Sets up the initial state of the game board with all cells set to unrevealed and no mines placed.
        Inputs: None.
        Outputs: None.
    */
    private void initializeBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = '-';
                mines[r][c] = false;
                revealed[r][c] = false;
            }
        }
    }

    /*
        Purpose: Places mines randomly on the board, ensuring the first clicked location is not a mine.
        Inputs:
        firstClickedRow (int): Row of the first user click.
        firstClickedCol (int): Column of the first user click.
        Outputs: None.
    */
    public void placeMines(int firstClickedRow, int firstClickedCol) {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            if ((r == firstClickedRow && c == firstClickedCol) || mines[r][c])
                continue;

            mines[r][c] = true;
            minesPlaced++;
        }
        fillBoard();
    }

    /*
        Purpose: Fills the board with mine indicators ('*') and calculates the number of adjacent mines for each cell.
        Inputs: None.
        Outputs: None.
    */
    private void fillBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (mines[r][c]) {
                    board[r][c] = '*';
                } else {
                    int count = countAdjacentMines(r, c);
                    board[r][c] = count == 0 ? ' ' : Character.forDigit(count, 10);
                }
            }
        }
    }

    /*
        Purpose: Counts mines adjacent to a given cell.
        Inputs:
        r (int): Row index of the cell.
        c (int): Column index of the cell.
        Outputs:
        Returns (int): Number of mines surrounding the specified cell.
    */
    private int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (isInBounds(r + dr, c + dc) && mines[r + dr][c + dc])
                    count++;
            }
        }
        return count;
    }

    /*
        Purpose: Checks if a given cell coordinate is within the board boundaries.
        Inputs:
        r (int): Row index.
        c (int): Column index.
        Outputs:
        Returns (boolean): True if the cell is within bounds, False otherwise.
    */
    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    /*
        Purpose: Checks if the player has won by revealing all non-mine cells.
        Inputs: None.
        Outputs:
        Returns (boolean): True if the player has won, False otherwise.
    */
    private boolean checkWin() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!mines[r][c] && !revealed[r][c]) {
                    return false;
                }
            }
        }
        return true; 
    }

    /*
        Purpose: Reveals a cell at specified coordinates, and recursively reveals adjacent cells if the revealed cell has no adjacent mines.
        Inputs:
        row (int): Row index of the cell to reveal.
        col (int): Column index of the cell to reveal.
        Outputs: None.
    */
    public void reveal(int row, int col) {
        if (!isInBounds(row, col) || revealed[row][col]) {
            if (row < 0 || col < 0) {
                System.out.println("Cannot have a negative coordinate! Row and Col must be greater or equal to 0!");
            } else if (row >= rows) {
                System.out.println("Row cannot be bigger than " + (rows - 1) + "!");
                if (col >= cols) {
                    System.out.println("Col cannot be bigger than " + (cols - 1) + "!");
                }
            } else if (col >= cols) {
                System.out.println("Col cannot be bigger than " + (cols - 1) + "!");
                if (row >= rows) {
                    System.out.println("Row cannot be bigger than " + (rows - 1) + "!");
                }
            }
            return;
        }

        revealed[row][col] = true;

        if (board[row][col] == '*') {
            System.out.println("Game Over! You hit a mine.");
            printBoard(true);
            offerRestart();
            System.exit(0);
        } else{
            if(board[row][col] == ' '){
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (isInBounds(row + dr, col + dc)) {
                            reveal(row + dr, col + dc);
                        }
                    }
                }
            }

            if (checkWin()) {
                printBoard(true);
                System.out.println("Congratulations! You have won the game!\n");
                offerRestart();
            }
        }
    }

    /*
        Purpose: Prints the current state of the board, showing all cells if showAll is true, otherwise showing only revealed cells.
        Inputs:
        showAll (boolean): Determines whether to show all cells or only revealed ones.
        Outputs: None.
    */
    public void printBoard(boolean showAll) {
        System.out.println("\nBoard:");
        for (int r = 0; r < rows; r++) {
            System.out.print(r + " ");
            String spacing = String.valueOf(r);
            int space = spacing.length();
            String maxSpace = String.valueOf(rows);
            int max = maxSpace.length();
            int difference = max-space;

            for(int i = 0; i < difference; i++){
                System.out.print(" ");
            }
            
            for (int c = 0; c < cols; c++) {
                if (showAll || revealed[r][c]) {
                    System.out.print(board[r][c]);
                    String spaces = String.valueOf(c);
                    int length = spaces.length();
                    for(int i = 0; i < length; i++){
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("-");
                    String spaces = String.valueOf(c);
                    int length = spaces.length();
                    for(int i = 0; i < length; i++){
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
        for (int c = 0; c < cols; c++) {
            if(c == 0){
                String maxSpace = String.valueOf(rows);
                int max = maxSpace.length();
                for(int i = 0; i < max; i++){
                    System.out.print(" ");
                }
            }
            System.out.print(" " + c);
        }
        System.out.println();
        System.out.println();
    }

    public void printBoard() {
        printBoard(false);
    }

    /*
        Purpose: Offers the player a choice to restart the game or exit after game over.
        Inputs: None.
        Outputs: None.
    */
    private void offerRestart() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again? (yes/no):");
        String response = scanner.next().toLowerCase();
        if (response.equals("yes")) {
            startNewGame();
            System.out.println();
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
        scanner.close();
    }

    /* 
        Purpose: Resets the game state and prompts the player to input new game settings (rows, columns, mines), then starts a new game.
        Inputs: None.
        Outputs: None.
    */
    private void startNewGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of columns:");
        cols = scanner.nextInt();
        System.out.println("Enter the number of mines:");
        totalMines = scanner.nextInt();
        if (totalMines >= rows * cols) {
            System.out.println("Too many mines. The number of mines must be less than the total cells.");
            totalMines = (rows * cols) - 1;
            System.out.println("Setting mines to maximum allowed: " + totalMines);
        }
        new Minesweeper(rows, cols, totalMines).playGame();
        scanner.close();
    }

    /*
        Purpose: Manages the game loop, prompting the player to reveal cells until the game ends.
        Inputs: None.
        Outputs: None.
    */
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        printBoard();
        System.out.println("Enter the first cell to reveal (row col): ");
        int firstRow = scanner.nextInt();
        int firstCol = scanner.nextInt();
        placeMines(firstRow, firstCol);
        reveal(firstRow, firstCol);
        printBoard();
        
        while (true) {
            System.out.println("Enter the next cell to reveal (row col): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            reveal(row, col);
            printBoard();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Minesweeper! \n\nTo play: \n1: Enter your desired board size (row & column) and number of mines to be placed!");
        System.out.println("2: Choose a spot to reveal by entering its coresponding row number then the column number. Remember, the top left of the board has row: 0 and column: 0.");
        System.out.println("\nReminder: As you increase the column number (labeled on the horizontal axis), the more right of the board position. \nAs you increase the row number (labeled on the vertical axis), the lower the board position.\n");

        new Minesweeper(10, 10, 10).startNewGame();
        scanner.close();
    }
}
