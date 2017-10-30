import java.util.*;

/**
 * Created on 2017-10-20
 *
 * In a grid of N by N squares you want to place a skyscraper in each square with only some clues:
 *
 * The height of the skyscrapers is between 1 and N
 * No two skyscrapers in a row or column may have the same number of floors
 * A clue is the number of skyscrapers that you can see in a row or column from the outside
 * Higher skyscrapers block the view of lower skyscrapers located behind them
 */

//TODO - make implementation independent of max block height
    //whether it is 4 or 6

public class SkyScrapers {
    
    private static final int MAX_HEIGHT = 4; //N
    private static final SkyScrapersPermutations permutations = new SkyScrapersPermutations(MAX_HEIGHT);
    
    //TODO // Rep invariant:
    // Abstraction Function:
    // Safety from rep exposure:
    //checkRep()

    /**
     * Given a clues array solves a puzzle and returns board 4x4 of 16 skyscrapers heights
     * The number of skyscrapers that you can see in a row or column from the outside matches given clue
     * @param clues - array of 16 clues for each column and row in a 4x4 board
     * @return Returns a board 4x4 of 16 skyscrapers heights that matches given clues
     */
    static int[][] solvePuzzle(int[] clues) {

        //System.out.println("clues = [" + Arrays.toString(clues) + "]");

        if (clues.length < MAX_HEIGHT * MAX_HEIGHT)
            throw new RuntimeException();

        //list of pairs: clue + cell indices that clue concerns
        List<ClueWithCellIndices> cluesWithCellIndices = matchCluesWithCellIndices(clues);

        //if there are no clues > 0
        if (cluesWithCellIndices.size() == 0)
            throw new RuntimeException();

        //sort list from greater clue to less one
        cluesWithCellIndices.sort(new Comparator<ClueWithCellIndices>() {
            @Override
            public int compare(ClueWithCellIndices o1, ClueWithCellIndices o2) {
                return (((Integer) o2.getClue()).compareTo((Integer) o1.getClue()));
            }
        });

        //initialize new board to put skyscrapers on it
        Board board = new Board(MAX_HEIGHT);
        //try populate the board with skyscrapers based on clues
        Optional<Board> result = calculateBoardForClues(board, cluesWithCellIndices, 0);

        if(!result.isPresent())
            return null; //could not solve the puzzle

        //check if any uninitialized cells left
        board = result.get();
        if(!board.allCellsInitialized()) {
            //initialize them
            result = calculateBlocksForCellsWithoutClues(board, 0);
            if (!result.isPresent())
                return null; //could not solve the puzzle
            board = result.get();
        }

        //all cells have a skyscraper
        //return complete board of skyscrapers
        return board.getBoard();
    }
    
    /**
     * Calculates list of pairs: clue + cell indices that clue concerns
     *
     * @param clues - array of 16 clues
     * @return List of pairs: clue + cell indices that clue concerns
     */
    static List<ClueWithCellIndices> matchCluesWithCellIndices(int[] clues) {

        final List<ClueWithCellIndices> cluesWithCellIndices = new ArrayList<>();

        for (int i = 0; i < clues.length; i++) {
            //ignore 0 clues
            if (clues[i] > 0)
                cluesWithCellIndices.add(new ClueWithCellIndices(clues[i], i));
        }

        return cluesWithCellIndices;
    }

    /**
     * Recursive method.
     * Try 1st Permutation for 1st clue.
     * Go to 2nd clue.
     * Try 1st Permutation for 2nd clue.
     * If it fails try 2nd Permutation for 2nd clue
     * If all Permutations fail return to 1st clue.
     * Try 2nd Permutation for 1st clue.
     *
     * Finish when either all Permutations where tried and it failed
     * or found right Permutation for all clues.
     *
     * @param inputBoard - Board of blocks
     * @param cluesWithCellIndices - list of clues
     * @param clueIndex - look for Permutations for given clue
     * @return Board if found right Permutation for all clues.
     *          Empty Optional if checked all possible Permutations and failed
     */
    private static Optional<Board> calculateBoardForClues(Board inputBoard, List<ClueWithCellIndices> cluesWithCellIndices, int clueIndex) {

        if(clueIndex >= cluesWithCellIndices.size())
            return Optional.of(inputBoard);

        int blockPermutationCounter = 0;
        int thisClue = cluesWithCellIndices.get(clueIndex).getClue();
        final int[] clueCellIndices = cluesWithCellIndices.get(clueIndex).getCellIndices();

        boolean tryNextPermutation;
        do {
            tryNextPermutation = false;
            //start with clear thisBoard
            Board thisBoard = new Board(inputBoard);
            //get block Permutation for thisClue and index = blockPermutationCounter
            List<Integer> blockPermutation = permutations.getPermutationsForClueAtIndex(thisClue, blockPermutationCounter);
            
            if(blockPermutation == null)
                //if does not exist (all Permutations used) return failure
                return Optional.empty();

            //check if all thisClue cells are equal to blocks from Permutation or equal 0
            for(int i = 0; i < clueCellIndices.length; i++) {
                if(thisBoard.getCell(clueCellIndices[i]) != blockPermutation.get(i))
                    if(thisBoard.getCell(clueCellIndices[i]) == 0)
                        //if not but they are == 0 => replace them with blocks from Permutation
                        thisBoard.setCell(clueCellIndices[i], blockPermutation.get(i));
                    else {
                        //if not => try another Permutation
                        tryNextPermutation = true;
                        break;
                    }
            }
            
            if(!tryNextPermutation) {
                //thisBoard is correct for thisClue
                //now try and recalculate thisBoard for next clue
                Optional<Board> opt = calculateBoardForClues(thisBoard, cluesWithCellIndices, clueIndex+1);
                if(opt.isPresent())
                    //SUCCESS, calculated board for thisClue and all succesors
                    return opt;
                else
                    //failed, we need to try next Permutation of blocks for thisCLue
                    tryNextPermutation = true;
            }
            blockPermutationCounter ++;
        } while (tryNextPermutation); //until all Permutations where tried or solution is found

        //should never get here
        return Optional.empty();
    }
    

    /**
     * Recursive method.
     * Fill uninitialized cells with right block height, unique in a row and a column
     * @param inputBoard - Board of blocks
     * @param cellIndex
     * @return
     */
    private static Optional<Board> calculateBlocksForCellsWithoutClues(Board inputBoard, int cellIndex) {

        if (cellIndex < 0)
            throw new IllegalArgumentException();
        if (inputBoard.allCellsInitialized() || cellIndex > 15)
            return Optional.of(inputBoard);

        int index = cellIndex;
        Board board = new Board(inputBoard);

        //find first uninitialized cell from given index
        while (index < 16) {
            if (board.getCell(index) == 0)
                break;
            index++;
        }

        if(index >= 16)
            //could not find uninitialized cell
            return Optional.of(board);

        //uninitialized cell at index
        //seek right block
        for(int block = 1; block <= 4; block++) {
            if(board.isUniqueInRowAndCol(index, block)) {
                //set new block value
                board.setCell(index, block);
                //now try and seek right block for next index
                Optional<Board> opt = calculateBlocksForCellsWithoutClues(board, index+1);
                if(opt.isPresent())
                    //SUCCESS
                    return opt;
                //else - try next block
            }
        }

        //if got here it means could not find right Permutation of block
        return Optional.empty();
    }
}


