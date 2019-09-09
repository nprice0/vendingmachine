package vendingmachine;

import com.assignment.vendingmachine.VendingAPI;
import com.assignment.vendingmachine.VendingMachine;
import com.assignment.vendingmachine.state.Initialised;
import com.assignment.vendingmachine.state.InitialisedEmpty;
import com.assignment.vendingmachine.state.Uninitialised;
import com.assignment.vendingmachine.state.VendingState;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class VendingMachineStateTests {

    //Not a test, using as helper for flow...
    public void testExampleWorkFlow(){
        VendingAPI vendingAPI = new VendingMachine();

        Map<Integer, Integer> cf = new TreeMap<>();
        cf.put(1,10);
        cf.put(2,10);
        cf.put(5,10);
        cf.put(10,10);
        cf.put(20,10);
        cf.put(50,10);
        cf.put(100,10);
        cf.put(200,10);

        //Initialise the machine
        vendingAPI.initialiseWithFloat(cf);

        Map<Integer, Integer> extraFloat = new TreeMap<>();
        extraFloat.put(1,10);
        extraFloat.put(2,10);

        //Register some extra coins
        vendingAPI.registerCoins(extraFloat);

        List<Integer> allcoins = vendingAPI.dispenseCoins(4090);

        List<Integer> emptyList = vendingAPI.dispenseCoins(4090);
        assertEquals(true,emptyList.isEmpty());

    }

    @Test
    public void testStartStateIsUninitialisedState() {
        VendingMachine vm = new VendingMachine();

        //Started in an uninitialised state
        assertThat(vm.getState(), instanceOf(Uninitialised.class));
    }

    @Test
    public void testInitialisesToEmptyStateWithoutAFloat() {
        VendingMachine vm = new VendingMachine();
        //Started in an uninitialised state
        assertThat(vm.getState(), instanceOf(Uninitialised.class));

        //Should be in an initialised-empty state
        vm.initialise();
        assertThat(vm.getState(), instanceOf(InitialisedEmpty.class));
    }

    @Test
    public void testCanBeInitialisedWithAFloat() {
        VendingMachine vm = new VendingMachine();

        Map<Integer,Integer> m = new HashMap<>();
        m.put(1,1000);
        m.put(2,1000);
        m.put(5,1000);
        m.put(10,1000);

        //Should in an uninitialised state, now initialise
        vm.initialiseWithFloat(m);
        assertThat(vm.getState(), instanceOf(Initialised.class));
    }

    @Test
    public void testCanDispenseCoinsWhenInitialised() {
        VendingMachine vm = new VendingMachine();

        Map<Integer,Integer> m = new HashMap<>();
        m.put(1,1000);
        m.put(2,1000);
        m.put(5,1000);
        m.put(10,1000);

        //Should be in an uninitialised state, now initialise and set float
        vm.initialiseWithFloat(m);
        assertThat(vm.getState(), instanceOf(Initialised.class));
        List<Integer> dc = vm.dispenseCoins(100);
        assertEquals(10,dc.size());
    }

    @Test
    public void testCanBeUninitialisedWithAFloatAndWontDispense() {
        VendingMachine vm = new VendingMachine();

        Map<Integer,Integer> m = new HashMap<>();
        m.put(1,1000);
        m.put(2,1000);
        m.put(5,1000);
        m.put(10,1000);

        //Should be in an uninitialised state, now initialise and set float
        vm.initialiseWithFloat(m);
        vm.uninitialise();
        List<Integer> dc = vm.dispenseCoins(100);
        assertEquals(null,dc);
    }

    @Test
    public void testSwitchesToEmptyStateWhenFloatIsEmptyThenBackToInitiaslisedWhenToppedUp() {
        VendingMachine vm = new VendingMachine();

        Map<Integer,Integer> m = new HashMap<>();
        m.put(1,1000);

        vm.initialiseWithFloat(m);
        assertThat(vm.getState(), instanceOf(Initialised.class));

        vm.dispenseCoins(1000);
        assertThat(vm.getState(), instanceOf(InitialisedEmpty.class));

        vm.registerCoins(m);
        assertThat(vm.getState(), instanceOf(Initialised.class));
    }

}
