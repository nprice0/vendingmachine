package com.assignment.vendingmachine.state;

import com.assignment.vendingmachine.VendingMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Initialised implements VendingState {

    public final static String STATUS = "INITIALISED";
    private static final Logger logger = LoggerFactory.getLogger(Initialised.class);
    private final VendingMachine vendingMachine;

    public Initialised(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialise() {
        logger.info("Already in " + STATUS + "state");
    }

    @Override
    public void initialiseWithFloat(Map<Integer, Integer> coinsToAdd) {
        this.initialise();
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        return vendingMachine.getCoinFloat().removeCoins(value);
    }

    @Override
    public void uninitialise() {
        logger.info("Switching to " + Uninitialised.STATUS + " state");
        vendingMachine.setState(vendingMachine.getUninitialisedState());
    }

    @Override
    public String getStatus() {
        return STATUS;
    }

}
