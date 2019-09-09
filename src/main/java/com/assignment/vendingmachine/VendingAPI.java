package com.assignment.vendingmachine;

import com.assignment.vendingmachine.state.VendingState;

import java.util.Map;

/**
 * API methods in addition to the vending states
 */
public interface VendingAPI extends VendingState {
    VendingState getState();
    void registerCoins(Map<Integer, Integer> coinsToAdd);
}
