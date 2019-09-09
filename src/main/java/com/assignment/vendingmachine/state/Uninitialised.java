package com.assignment.vendingmachine.state;

import com.assignment.vendingmachine.VendingMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Uninitialised implements VendingState {

    public final static String STATUS = "UNINITIALISED";
    private static final Logger logger = LoggerFactory.getLogger(Uninitialised.class);
    private final VendingMachine vendingMachine;

    public Uninitialised(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialise() {
        if (vendingMachine.getCoinFloat().getCoinFloat().isEmpty()) {
            vendingMachine.setState(vendingMachine.getInitialisedEmptyState());
        } else {
            vendingMachine.setState(vendingMachine.getInitialisedState());
        }
        logger.info("Switching to " + vendingMachine.getStatus() + " state");
    }

    @Override
    public void initialiseWithFloat(Map<Integer, Integer> coinsToAdd) {
        this.initialise();
        vendingMachine.registerCoins(coinsToAdd);
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        logger.info("Cannot dispense coins in " + Initialised.STATUS + " state");
        return null;
    }

    @Override
    public void uninitialise() {
        logger.info("Already in " + STATUS + " state");
    }

    @Override
    public String getStatus() {
        return STATUS;
    }
}
