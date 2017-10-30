import java.util.Arrays;

/**
 * Represents a couple of clue + list of cell indices that clue relates to
 * Cell Indices are ordered in a direction of the clue
 * If the clue relates to row 2 from right to left, then cell indices will be 11, 10, 9, 8
 */
class ClueWithCellIndices {

    private int clue;
    private int[] cellIndices;

    /**
     * @param inClue
     * @param cluePosition
     */
    ClueWithCellIndices(int inClue, int cluePosition) {

        if (inClue >= 0 && inClue <= 4)
            this.clue = inClue;
        else
            throw new IllegalArgumentException();

        int i = cluePosition;
        switch (cluePosition) {
            case 0:
            case 1:
            case 2:
            case 3:
                cellIndices = new int[]{0 + i, 4 + i, 8 + i, 12 + i};
                break;

            case 4:
            case 5:
            case 6:
            case 7:
                cellIndices = new int[]{3 + (i - 4) * 4, 2 + (i - 4) * 4, 1 + (i - 4) * 4, 0 + (i - 4) * 4};
                break;

            case 8:
            case 9:
            case 10:
            case 11:
                cellIndices = new int[]{15 - (i - 8), 11 - (i - 8), 7 - (i - 8), 3 - (i - 8)};
                break;

            case 12:
            case 13:
            case 14:
            case 15:
                cellIndices = new int[]{12 - (i - 12) * 4, 13 - (i - 12) * 4, 14 - (i - 12) * 4, 15 - (i - 12) * 4};
                break;
        }
    }

    public int getClue() {
        return clue;
    }

    public int[] getCellIndices() {
        return cellIndices;
    }

    @Override
    public String toString() {
        return "ClueWithCellIndices{" +
                "clue=" + clue +
                ", cellIndices=" + Arrays.toString(cellIndices) +
                '}';
    }
}
