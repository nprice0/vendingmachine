package com.assignment.vendingmachine;

import com.assignment.vendingmachine.coin.CoinFloat;
import com.assignment.vendingmachine.state.Initialised;
import com.assignment.vendingmachine.state.InitialisedEmpty;
import com.assignment.vendingmachine.state.Uninitialised;
import com.assignment.vendingmachine.state.VendingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Vending machine  Starts in the Uninitialised state, after first intialisation, changes state by observing
 * the coins in the float
 */
public class VendingMachine implements VendingAPI, Observer {

    private static final Logger logger = LoggerFactory.getLogger(VendingMachine.class);

    private VendingState initialisedState;
    private VendingState uninitialisedState;
    private VendingState initialisedEmptyState;
    private VendingState state;

    private CoinFloat coinFloat;

    public VendingMachine() {
        this.initialisedState = new Initialised(this);
        this.uninitialisedState = new Uninitialised(this);
        this.initialisedEmptyState = new InitialisedEmpty(this);

        this.state = uninitialisedState;

        this.coinFloat = new CoinFloat();
        this.coinFloat.addObserver(this);
    }

    @Override
    public void initialiseWithFloat(Map<Integer, Integer> coinsToAdd) {
        state.initialiseWithFloat(coinsToAdd);
    }

    @Override
    public void uninitialise() {
        state.uninitialise();
    }

    @Override
    public void initialise() {
        state.initialise();
    }

    @Override
    public void registerCoins(Map<Integer, Integer> coinsToAdd) {
        this.coinFloat.addCoins(coinsToAdd);
    }

    @Override
    public String getStatus() {
        return state.getStatus();
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        return state.dispenseCoins(value);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (coinFloat.getCoinFloat().isEmpty()) {
            if (state == initialisedState) {
                state = initialisedEmptyState;
                logger.info("Switching to " + state.getStatus());
            }
        } else if (state == initialisedEmptyState) {
            state = initialisedState;
            logger.info("Switching to " + state.getStatus());
        }
    }

    public VendingState getState() {
        return state;
    }

    public void setState(VendingState state) {
        this.state = state;
    }

    public VendingState getInitialisedState() {
        return initialisedState;
    }

    public VendingState getUninitialisedState() {
        return uninitialisedState;
    }

    public VendingState getInitialisedEmptyState() {
        return initialisedEmptyState;
    }

    public CoinFloat getCoinFloat() {
        return coinFloat;
    }
}
