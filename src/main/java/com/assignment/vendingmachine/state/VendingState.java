package com.assignment.vendingmachine.state;

import java.util.List;
import java.util.Map;

/**
 * Vending machine state methods
 */
public interface VendingState {
    String getStatus();
    List<Integer> dispenseCoins(int value);
    void uninitialise();
    void initialise();
    void initialiseWithFloat(Map<Integer, Integer> coinsToAdd);
}
