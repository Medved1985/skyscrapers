import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Created on 2017-10-25</p>
 * <p>
 * <p>Class represents a list of all permutations for given set of skyscrapers of different heights.
 * Each permutation is associated with a clue.</p>
 * <p>
 * <p><b>Permutation</b> relates to the act of arranging all the members of a set into some sequence or order
 * For example, there are six permutations of the set {1,2,3}, namely: (1,2,3), (1,3,2), (2,1,3), (2,3,1), (3,1,2), and (3,2,1).</p>
 * <p>
 * <p>The height of the skyscrapers is between 1 and max, where max is given, but no more than 10.
 * For example, if max = 3, then class represents all permutations of three skyscrapers: 1 floor high, 2 floors high and 3 floors high</p>
 * <p>
 * <p><b>A clue</b> is the number of skyscrapers that you can see in a row from the outside.
 * For example for permutation (2,1,3) the clue is 2</p>
 */
public class SkyScrapersPermutations {

    private final int maxHeight; //1 - 10

    //key -> clue
    //value -> list of all combinations of block heights for this clue
    private final Map<Integer, List<List<Integer>>> cluePermutationList = new TreeMap<>();

    // Abstraction Function:
    //  maxHeight - max height of skyscrapers in a set
    //  cluePermutationList.get(clue) - all possible permutations of skyscrapers in a row so that only "clue" of skyscrapers are visible from the front

    // Safety from rep exposure:
    //  attributes are private
    //  no setters exposed
    //  all getters return either a primitive type or a deep copy of a collection

    // Rep invariant:
    //  cluePermutationList.size() == maxHeight
    //  each permutation.size == maxHeight
    //  number of all permutations for all clues == (maxHeight!)
    // TODO checkRep()
    private void checkRep() {
        assert cluePermutationList.size() == maxHeight;
        for (Map.Entry<Integer, List<List<Integer>>> permutation:
                cluePermutationList.entrySet()) {
            assert permutation.getValue().size() == maxHeight;
        }
    }
    /**
     * Constructs SkyScrapersPermutations for given maximum skyscraper height.
     * Calculates all possible skyscraper permutations for all possible visibiity clues.
     * @param maxHeight - value from 1 to 10
     */
    public SkyScrapersPermutations(int maxHeight) {
        if (maxHeight <= 0 || maxHeight > 10)
            throw new IllegalArgumentException();

        this.maxHeight = maxHeight;
        populateCluePermutationList();
    }

    /**
     * Returns number of visibility clues
     * @return Returns number of visibility clues
     */
    public int getClueCount() {
        return cluePermutationList.size();
    }

    /**
     * Returns number of all permutations for all clues
     * @return Returns number of all permutations for all clues
     */
    public int getAllPermutationsCount() {
        int count = 0;
        for (Map.Entry<Integer, List<List<Integer>>> entry : cluePermutationList.entrySet()
             ) {
            count += entry.getValue().size();
        }
        return count;
    }

    /**
     * Returns a number of permutations for a specific clue
     * @param clue
     * @return Returns a number of permutations for a specific clue.
     * Returns 0 if no such clue exists.
     */
    public int getPermutationsForClueCount(int clue) {
        if(cluePermutationList.containsKey(clue))
            return cluePermutationList.get(clue).size();
        return 0;
    }

    /**
     * Returns a permutation for a given clue at a given index
     * @param clue - a clue
     * @param index - an index
     * @return Returns a permutation for a given clue at a given index.
     * Return null if index >= permutation count for a given clue or no such clue exists.
     */
    public List<Integer> getPermutationsForClueAtIndex(int clue, int index) {
        if(index < getPermutationsForClueCount(clue))
            return deepClone(cluePermutationList.get(clue).get(index));

        return null;
    }

    /*
    Returns a deepClone of permutations list
     */
    private List<Integer> deepClone(List<Integer> list) {

        List<Integer> result = new LinkedList<>();

        for (Integer i : list) {
            int copyInt = i;
            result.add(copyInt);
        }

        return result;
    }

    /*
    Populates permutations map for all possible clues from 1 to maxHeight
     */
    private void populateCluePermutationList() {

        //iterate through all combination of block heights
        //calculate its clue and put each combination on a list of combination for specific clue

        int min = 1;
        int max = maxHeight;

        //number of all permutations = n!
        List<Integer> list = new LinkedList<>();
        for (int i = min; i <= max; i++)
            list.add(i);

        List<List<Integer>> permutations = calculatePermutations(list);

        for (List<Integer> perm : permutations
                ) {
            Integer clue = calculateClue(perm);
            List<List<Integer>> permsForClue = cluePermutationList.get(clue);
            if (permsForClue == null) {
                permsForClue = new LinkedList<List<Integer>>();
                permsForClue.add(perm);
                cluePermutationList.put(clue, permsForClue);
            } else
                permsForClue.add(perm);
        }

        checkRep();

    }

    /*
    Calculates a clue for given permutation of skyscrapers height
     */
    private Integer calculateClue(List<Integer> perm) {
        int clue = 0;
        int highest = 0;

        for (Integer i : perm
                ) {
            if (i > highest) {
                highest = i;
                clue++;
            }
        }
        return clue;
    }

    /*
    Calculates all possible permutations of given list of integers.
     */
    private List<List<Integer>> calculatePermutations(List<Integer> list) {

        if (list.size() == 0)
            throw new RuntimeException();

        List<List<Integer>> result = new LinkedList<List<Integer>>();

        if (list.size() == 1) {
            result.add(list);
            return result;
        }

        for (Integer number : list
                ) {
            List<Integer> list2 = new LinkedList<>(list);
            list2.remove(number);
            List<List<Integer>> perms = calculatePermutations(list2);
            for (List<Integer> li : perms
                    ) {
                li.add(0, number);
                result.add(li);
            }
        }

        return result;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
