import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created on 2017-10-25
 */
public class SkyScrapersPermutationsTest {

    /*
    Testing strategy
    in: 1, 5, 10
     */

    private final SkyScrapersPermutations ssp1 = new SkyScrapersPermutations(1);
    private final SkyScrapersPermutations ssp5 = new SkyScrapersPermutations(5);
    //private final SkyScrapersPermutations ssp10 = new SkyScrapersPermutations(10); //TAKES LONG

    @Test
    public void getClueCount() throws Exception {
        assertEquals(1, ssp1.getClueCount());
        assertEquals(5, ssp5.getClueCount());
       //assertEquals(10, ssp10.getClueCount());
    }

    @Test
    public void getAllPermutationsCount() throws Exception {
        assertEquals(1, ssp1.getAllPermutationsCount());
        assertEquals(1*2*3*4*5, ssp5.getAllPermutationsCount());
       //assertEquals(1*2*3*4*5*6*7*8*9*10, ssp10.getAllPermutationsCount());;
    }

    @Test
    public void getPermutationsForClueCount() throws Exception {
        assertEquals(1, ssp1.getPermutationsForClueCount(1));
        assertEquals(1, ssp5.getPermutationsForClueCount(5));
        assertEquals(1*2*3*4, ssp5.getPermutationsForClueCount(1));
    }

    @Test
    public void getPermutationsForClueAtIndex() throws Exception {
        assertEquals(Arrays.asList(1), ssp1.getPermutationsForClueAtIndex(1, 0));
        assertEquals(Arrays.asList(1,2,3,4,5), ssp5.getPermutationsForClueAtIndex(5, 0));
    }

}