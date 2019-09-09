package vendingmachine.coin;

import com.assignment.vendingmachine.coin.CoinFloat;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

import java.util.List;
import java.util.TreeMap;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CoinFloatTests {

    @Test
    public void testUnableToProvideChange(){

        TreeMap<Integer, Integer> cf = new TreeMap<>();

        cf.put(21,10);
        cf.put(2,5);

        CoinFloat coin =new CoinFloat(cf);
        List<Integer> change =  coin.removeCoins(26);
        assertEquals(change.size(),0);
        assertThat(change, IsEmptyCollection.empty());
    }

    @Test
    public void testForceRecursion(){

        TreeMap<Integer, Integer> cf = new TreeMap<>();

        cf.put(20,1);
        cf.put(10,2);
        cf.put(5,2);
        cf.put(2,3);

        CoinFloat coin =new CoinFloat(cf);
        List<Integer> change =  coin.removeCoins(21);
        assertThat(change, containsInAnyOrder(2,2,2,5,10));
    }

    @Test
    public void testReturnsSameChangeAfterRemovalAndTopUp(){

        TreeMap<Integer, Integer> cf = new TreeMap<>();

        cf.put(1,10);
        cf.put(2,2);
        cf.put(5,10);
        cf.put(10,5);
        cf.put(20,5);
        cf.put(50,5);
        cf.put(100,5);
        cf.put(200,5);

        CoinFloat coin =new CoinFloat(cf);

        List<Integer> change =  coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(2,2,5,20,20,50));

        //No twos remaining
        change =  coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(1,1,1,1,5,20,20,50));

        //Top up the twos and twenties
        TreeMap<Integer, Integer> cf2 = new TreeMap<>();
        cf2.put(2,2);
        cf2.put(20,1);
        coin.addCoins(cf2);

        change = coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(2,2,5,20,20,50));
    }

}
