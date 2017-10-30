import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 2017-10-20
 */


public class SkyScrapersTest {

    private static int clues[][] = {
            { 2, 2, 1, 3,
                    2, 2, 3, 1,
                    1, 2, 2, 3,
                    3, 2, 1, 3 },
            { 0, 0, 1, 2,
                    0, 2, 0, 0,
                    0, 3, 0, 0,
                    0, 1, 0, 0 },
            {1, 2, 4, 2, 2, 1, 3, 2, 3, 1, 2, 3, 3, 2, 2, 1}
    };

    private static int outcomes[][][] = {
            { { 1, 3, 4, 2 },
                    { 4, 2, 1, 3 },
                    { 3, 4, 2, 1 },
                    { 2, 1, 3, 4 } },
            { { 2, 1, 4, 3 },
                    { 3, 4, 1, 2 },
                    { 4, 2, 3, 1 },
                    { 1, 3, 2, 4 } },
            {{4, 2, 1, 3},
                    {3, 1, 2, 4},
                    {1, 4, 3, 2},
                    {2, 3, 4, 1}}
    };

    @Test
    public void testSolvePuzzle1 () {
        int[][] result = SkyScrapers.solvePuzzle (clues[0]);
        assertEquals (result, outcomes[0]);
    }

    @Test
    public void testSolvePuzzle2 () {
        int[][] result = SkyScrapers.solvePuzzle (clues[1]);
        assertEquals (result, outcomes[1]);
    }

    @Test
    public void testSolvePuzzle3 () {
        int[][] result = SkyScrapers.solvePuzzle (clues[2]);
        assertEquals (result, outcomes[2]);
    }

    @Test
    public void testIsUniqueInRowAndCol() {
        Board board = new Board(outcomes[0]);

        assertTrue(board.isUniqueInRowAndCol(7, 3));
        assertFalse(board.isUniqueInRowAndCol(0, 3));
    }
}