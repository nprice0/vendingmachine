# Vending Machine
## Build
- JDK 8
-  Gradle
- Build from the project root directory using `gradlew clean build`

## This component offers an  API which:
- Initialises a vending machine to a known state, for use when the machine is set up. This includes setting
the initial float (the coins placed in the machine for customer change) which can be accepted as a parameter.
- Registers coins that have been deposited by a user
- Produces a collection of coins that sum to a particular value (accepted as a parameter) from the coins available
in the machine

## Assumptions
-  Coin denominations are integer value
- The number of each coin is a positive integer value
- Coins can only be dispensed if they are available
- If the float is empty after the first initialisation, then user must register new coins or no coins can be dispensed

## Other
- More unit test are needed
- Logging has been left on to show state info etc during the running

## Instructions
- Run from a command line with
    `java -jar vendingmachine-0.0.1-SNAPSHOT.jar`

The following test-harness screen shows the machine being initialised and the float being added.  After which the menu options have been updated -

        >> Select an option <<
        1:      Initialise the vending machine and set the initial float
        9:      Exit
        1
        >> Enter coin denomination as integer (or X to continue):
        1
        >> Enter number of coins for 1:
        1
        >> Enter coin denomination as integer (or X to continue):
        2
        >> Enter number of coins for 2:
        2
        >> Enter coin denomination as integer (or X to continue):
        5
        >> Enter number of coins for 5:
        5
        >> Enter coin denomination as integer (or X to continue):
        10
        >> Enter number of coins for 10:
        10
        >> Enter coin denomination as integer (or X to continue):
        x
        01:24:19.981 [main] INFO  com.play.vendingmachine.state.Uninitialised - Switching to INITIALISED-EMPTY state
        01:24:20.022 [main] INFO  com.play.vendingmachine.coin.CoinFloat - Float loaded
        01:24:20.022 [main] INFO  com.play.vendingmachine.VendingMachine - Switching to INITIALISED


        >> Select an option <<
        2:      Register coins that have been deposited by a user
        3:      Produce a collection of coins that sum to a particular value
        9:      Exit


The following test-harness screen shows the coin float being emptied, and the respective menu updates -

        >> Select an option <<
        2:      Register coins that have been deposited by a user
        3:      Produce a collection of coins that sum to a particular value
        9:      Exit
        3
        >> Enter coin value to dispense as integer:
        130
        01:27:37.464 [main] INFO  com.play.vendingmachine.coin.CoinFloat - Float empty
        01:27:37.464 [main] INFO  com.play.vendingmachine.VendingMachine - Switching to INITIALISED-EMPTY
        Removed the following coins from the machine [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 5, 5, 5, 2, 2, 1]


        >> Select an option <<
        2:      Register coins that have been deposited by a user
        9:      Exit
