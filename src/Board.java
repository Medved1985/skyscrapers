/**
 * Represents a Board of blocks
 * Board size is 4 x 4
 * Each cell has either a value 0 (not initialized) or <1 .. 4>
 */
class Board {

    private int[][] board;
    private final int size;

    //initialize empty board
    Board(int inSize) {
        size = inSize;
        board = new int[size][size];
    }

    //initialize board with a copy of another board
    Board(Board anotherBoard) {
        size = anotherBoard.board.length;
        board = new int[size][];
        for(int i = 0; i < size; i++)
            this.board[i] = anotherBoard.board[i].clone();
    }

    Board(int[][] anotherBoard) {
        size = anotherBoard.length;
        board = new int[size][];
        for(int i = 0; i < size; i++)
            this.board[i] = anotherBoard[i].clone();
    }


    public int getCell(int index) {
        int row = index / size;
        int col = index % size;

        return board[row][col];
    }

    public void setCell(int index, int value) {

        if(index < 0 || index >= size*size)
            throw new IllegalArgumentException();

        if(value < 1 || value > size)
            throw new IllegalArgumentException();

        int row = index / size;
        int col = index % size;

        board[row][col] = value;
    }

    public int[][] getBoard() {
        return board;
    }

    /**
     * Returns true if all board cells are initialized.
     * @return Returns false if any cell is zero. Otherwise returns true.
     */
    public boolean allCellsInitialized() {
        for (int[] a : board)
            for (int i : a) {
                if(i <= 0 || i > size)
                    return false;
            }

         return true;
    }

    /**
     *
     * @param index - index of a cell to check 0..15
     * @param value - value to check
     * @return Return true if no such value exists in the same row and column as a given cell
     */
    public boolean isUniqueInRowAndCol(int index, int value) {

        if(index < 0 || index >= size*size)
            throw new IllegalArgumentException();

        int row = index / size;
        int col = index % size;

        //iterate through the same col and row
        for(int i = 0; i < size; i++) {
            if(board[row][i] == value && i != col)
                return false;
            if(board[i][col] == value && i != row)
                return false;
        }
        return true;
    }
}
