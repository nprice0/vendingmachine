package com.assignment.vendingmachine;

import com.assignment.vendingmachine.state.Initialised;
import com.assignment.vendingmachine.state.InitialisedEmpty;
import com.assignment.vendingmachine.state.Uninitialised;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class to demonstrate test harness wrapping the Vending API
 */
public class VendingMachineApplication {

    private static final Logger logger = LoggerFactory.getLogger(VendingMachineApplication.class);

    public static void main(String[] args) throws Exception {
        VendingMachineApplication vm = new VendingMachineApplication();
        vm.run(args);
    }

    /**
     * Main test harness runner
     *
     * @param args
     * @throws Exception
     */
    private void run(String[] args) throws Exception {

        boolean run = true;

        Console console = System.console();
        if (console == null) {
            logger.error("No console");
            //run = false;
        }

        VendingAPI vendingAPI = new VendingMachine();
        while (run) {
            Scanner scanner = new Scanner(System.in);
            Map<Integer, Integer> m;

            doMainMenuDisplay(vendingAPI);
            String option = doMainMenuRead(vendingAPI, scanner);

            switch (option) {
                case "1":
                    m = addFunds(scanner);
                    vendingAPI.initialiseWithFloat(m);
                    break;

                case "2":
                    m = addFunds(scanner);
                    vendingAPI.registerCoins(m);
                    break;

                case "3":
                    dispense(scanner, vendingAPI);
                    break;

                case "9":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid entry");
            }
        }
    }

    /**
     * Presents menu and dispenses coins from the vending machine
     *
     * @param scanner
     * @param vendingAPI
     */
    private void dispense(Scanner scanner, VendingAPI vendingAPI) {
        System.out.println(">> Enter coin value to dispense as integer:");
        String input = scanner.next();
        try {
            int iInput = Integer.valueOf(input);
            if (iInput > 0) {
                List<Integer> dispenseCoins = vendingAPI.dispenseCoins(iInput);
                if (dispenseCoins.isEmpty()) {
                    System.out.println("Sorry, unable to produce a collection of coins to that amount");
                } else {
                    System.out.println("Removed the following coins from the machine " + dispenseCoins);
                }
            } else {
                System.out.println("Value must be between 1 and " + Integer.MAX_VALUE);
            }

        } catch (NumberFormatException nfe) {
            System.out.println("Value must be a positive whole number between 1 and " + Integer.MAX_VALUE);
        }
    }

    /**
     * Adds extra float to the vending machine
     *
     * @param scanner
     */
    private Map<Integer, Integer> addFunds(Scanner scanner) {

        Map<Integer, Integer> map = new HashMap<>();

        String error = "Invalid values entered, values must be between 1 and " + Integer.MAX_VALUE;
        String denomination = "";
        String numberOfCoins;

        while (!denomination.trim().equalsIgnoreCase("X")) {
            try {
                System.out.println(">> Enter coin denomination as integer (or X to continue):");
                denomination = scanner.next();
                if (denomination.trim().equalsIgnoreCase("X"))
                    continue;
                int iDenomination = Integer.valueOf(denomination);

                System.out.println(">> Enter number of coins for " + iDenomination + ":");
                numberOfCoins = scanner.next();
                int iNumberOfCoins = Integer.valueOf(numberOfCoins);

                if (iDenomination > 0 && iNumberOfCoins > 0) {
                    if (map.containsKey(iDenomination)) {
                        map.put(iDenomination, map.get(iDenomination) + Integer.valueOf(numberOfCoins));
                    } else {
                        map.put(Integer.valueOf(denomination), Integer.valueOf(numberOfCoins));
                    }
                } else {
                    System.out.println(error);
                }
            } catch (NumberFormatException nfe) {
                System.out.println(error);
            }
        }
        return map;
    }


    /**
     * Prints the main menu options
     *
     * @param vendingAPI
     */
    private void doMainMenuDisplay(VendingAPI vendingAPI) {

        System.out.println("\n");
        System.out.println(">> Select an option <<");

        if (vendingAPI.getStatus().equals(Uninitialised.STATUS)) {
            System.out.println("1:      Initialise the vending machine and set the initial float");
        }

        if (vendingAPI.getStatus().equals(Initialised.STATUS) || vendingAPI.getStatus().equals(InitialisedEmpty.STATUS)) {
            System.out.println("2:      Register coins that have been deposited by a user");
        }

        if (vendingAPI.getStatus().equals(Initialised.STATUS)) {
            System.out.println("3:      Produce a collection of coins that sum to a particular value");
        }
        System.out.println("9:      Exit");
    }


    /**
     * Reads the main menu selections
     *
     * @param vendingAPI
     * @param scanner
     * @return
     */
    private String doMainMenuRead(VendingAPI vendingAPI, Scanner scanner) {

        String option = scanner.next().trim();
        String result = "0";
        switch (option) {
            case "1":
                if (vendingAPI.getStatus().equals(Uninitialised.STATUS)) {
                    result = option;
                }
                break;
            case "2":
                if (vendingAPI.getStatus().equals(Initialised.STATUS) || vendingAPI.getStatus().equals(InitialisedEmpty.STATUS)) {
                    result = option;
                }
                break;
            case "3":
                if (vendingAPI.getStatus().equals(Initialised.STATUS)) {
                    result = option;
                }
                break;
            case "9":
                result = option;
                break;
            default:
                break;
        }
        return result;
    }

}
