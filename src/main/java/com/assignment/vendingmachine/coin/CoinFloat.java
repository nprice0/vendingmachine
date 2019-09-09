package com.assignment.vendingmachine.coin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Stores the coin denomination, and the number of coins available.  Provides basic add/remove coin methods.
 */
public class CoinFloat extends Observable {

    private static final Logger logger = LoggerFactory.getLogger(CoinFloat.class);

    //Stores the coin denomination, and the number of each coin
    private final TreeMap<Integer, Integer> coinFloat;

    /**
     * Creates a new CoinFloat
     */
    public CoinFloat() {
        this.coinFloat = new TreeMap<>(Collections.reverseOrder());
        changeStateBroadcast();
    }

    /**
     * @param coinFloat Creates a new CoinFloat. Copies the contents of the map into a new map
     *                  storing the keys in reverse natural order
     */
    public CoinFloat(TreeMap<Integer, Integer> coinFloat) {
        this.coinFloat = new TreeMap<>(Collections.reverseOrder());
        addCoins(coinFloat);
        changeStateBroadcast();
    }


    /**
     * Issues a copy of the map
     *
     * @return
     */
    public TreeMap<Integer, Integer> getCoinFloat() {
        TreeMap<Integer, Integer> cf = new TreeMap<>(Collections.reverseOrder());
        cf.putAll(coinFloat);
        return cf;
    }

    /**
     * Adds more coins to the float
     *
     * @param coinFloatToAdd
     */
    public void addCoins(Map<Integer, Integer> coinFloatToAdd) {
        boolean state = coinFloat.isEmpty();
        coinFloatToAdd.forEach((k, v) -> {
            if (k != null && v != null && k > 0 && v > 0) {
                Integer currentValue = coinFloat.get(k);
                if (currentValue == null) {
                    currentValue = 0;
                }
                coinFloat.put(k, v + currentValue);
            }
        });
        if (state != coinFloat.isEmpty())
            changeStateBroadcast();
    }

    /**
     * Removes coins to the sum of value from the float
     *
     * @param valueToRemove
     * @return
     */
    public List<Integer> removeCoins(int valueToRemove) {
        List<Integer> coins=null;
        if (valueToRemove > 0) {
            coins = getCoinList(valueToRemove);
            boolean state = coinFloat.isEmpty();
            for (int coin : coins) {
                Integer previous = coinFloat.put(coin, coinFloat.get(coin) - 1);
                if (previous == 1) {
                    coinFloat.remove(coin);
                }
            }
            if (state != coinFloat.isEmpty())
                changeStateBroadcast();
        }
        return coins!=null?coins:new ArrayList<>();
    }

    /**
     * Identify whether a coin list can be created for the value
     *
     * @param value the value of coins needed
     */
    private List<Integer> getCoinList(int value) {

        int[] floatCoinDenominations = coinFloat.keySet().stream().mapToInt(Integer::intValue).toArray();
        int[] floatCoinQtys = coinFloat.values().stream().mapToInt(Integer::intValue).toArray();

        List<Integer> coinList = new ArrayList<>();

        createCoinList(value, floatCoinDenominations, floatCoinQtys, coinList, 0);

        return coinList;
    }

    /**
     * Recursive algorithm to identify whether a coin list can be created for the value
     *
     * @param targetValue
     * @param floatCoinDenominations
     * @param coinQtys
     * @param listOfCoins
     * @param position
     * @return
     */
    private int createCoinList(int targetValue, int[] floatCoinDenominations, int[] coinQtys, List<Integer> listOfCoins, int position) {

        //if total is 0 then return
        if (targetValue == 0) {
            return 0;
        }

        int pushedToList = 0;
        for (int counter = position; counter < floatCoinDenominations.length; counter++) {

            int coinDenomination = floatCoinDenominations[counter];
            //Continue if coin is greater than the total
            if (coinDenomination > targetValue) {
                continue;
            }

            //Continue if the coin isnt available to dispense as change
            if (coinQtys[counter] == 0) {
                continue;
            }

            coinQtys[counter] -= 1;
            listOfCoins.add(coinDenomination);
            pushedToList = coinDenomination;
            targetValue -= coinDenomination;

            targetValue = createCoinList(targetValue, floatCoinDenominations, coinQtys, listOfCoins, counter);
            if (targetValue > 0) {
                if (pushedToList > 0) {
                    targetValue += pushedToList;
                    listOfCoins.remove(listOfCoins.size() - 1);
                    pushedToList = 0;
                }
            }
        }
        return targetValue;
    }

    private void changeStateBroadcast() {
        String message;
        if (coinFloat.isEmpty()) {
            message = "Float empty";
            logger.info(message);
            setChanged();
            notifyObservers(message);
        } else {
            message = "Float loaded";
            logger.info(message);
            setChanged();
            notifyObservers(message);
        }
    }
}