import java.util.Scanner;


public class Craps {

    public static final int[] passRelevant = {2, 3, 7, 11, 12};
    public static int bank = 100;
    // Let's also define a static Scanner for every function to use equally.
    // This is easier than making a new Scanner whenever we want to get user input.
    private final static Scanner crapsScanner = new Scanner(System.in);

    public static void main(String[] args) {
        startCrapsGame();
    }

    private static void startCrapsGame() {
        int index;

        System.out.println("A game of Craps begins.");
        Roll roll = new Roll();
        int die1 = roll.die1;
        int die2 = roll.die2;
        int result = roll.getResult();
        comeOut(result, die1, die2); // Here we just pass the current game's rolled result into a function rather than having it as a global variable. This will be easier if we want to eventually run more craps games, etc.
        // We always do the come out no matter what.

        for (index = 0; index < 5; index++) {
            if (result != passRelevant[index]) {
                System.out.println("The point is now on: " + result);
                int point = result;
                String placeBet;
                do {
                    System.out.println("Roll again shooter, or place more bets?");
                    // TODO: Fully implement point round. This should include:
                    // 1. Prompt player to define their more complicated bet
                    // Note, this is a separate process for additional bets
                    // The default bet still stands from the pass line
                    placeBet = "";
                    do {
                        System.out.println("Type either bet or roll");
                        placeBet = crapsScanner.next();
                    }
                    while (!placeBet.equalsIgnoreCase("bet") && !placeBet.equalsIgnoreCase("roll"));


                    if (placeBet.equalsIgnoreCase("roll")) {
                        // 2. Roll as needed
                        Roll pointRoll = new Roll();
                        result = pointRoll.getResult();
                        pointRoll.die1 = die1;
                        pointRoll.die2 = die2;
                        pointRound(result, die1, die2, point);
                    }
                } while (result != point);

                if (placeBet.equalsIgnoreCase("bet")) {
                    //TODO: Make more complex bets
                }

                // 4. End the game afterwards.
                //endGame();
            }
            if (index == 5) {
                endGame();
            }
        }
    }


    private static void endGame() {
        System.out.println("Game over.");
        System.out.println("Play again?");
        String result = crapsScanner.next();
        if (result.equalsIgnoreCase("y")) {
            startCrapsGame(); // Start another game.
        } else {
            System.exit(0); // Exit with return code 0. 0 means "everything worked as it should".
        }
    }

    private static void comeOut(int result, int die1, int die2) {
        boolean pass = false;

        System.out.println("Pass or Don't Pass?");

        // HERE is a case where we want to use a 'while' loop. Until we get pass or don't pass, we need to keep asking.
        String setPass = "";
        do {
            setPass = crapsScanner.next();
        } while (!setPass.equalsIgnoreCase("pass") && !setPass.equalsIgnoreCase("don't pass"));
        System.out.println("You chose " + setPass + "."); // confirmation

        System.out.println("What's your bet? You currently have " + bank + ".");
        int bet = crapsScanner.nextInt();
        bank = bank - bet; // deduct money from bank
        System.out.println("Betting " + bet + ". New total in bank is " + bank + ".");

        if (setPass.equalsIgnoreCase("pass")) {
            pass = true;
            System.out.println(bet + " on the " + setPass + " line.");
        }
        if (setPass.equalsIgnoreCase("don't pass")) {
            pass = false;
            System.out.println(bet + " on the " + setPass + " line.");
        }

        // For this come out bit, since this bet is really basic, let's not use classes.
        // We can just double when you win.
        // And we don't need to deduct when you lose, since we already deducted when you bet.
        // We also want to display the roll no matter what (to show that we'll move forwards to the next bet).
        System.out.println("[" + die1 + "]" + " [" + die2 + "]");
        payBet payout = new payBet();
        if (result == 7 || result == 11) {
            if (pass) {
                System.out.println(result + " on the come out, pass pays even!");
                bank = payout.payEven(bet);
            }
            if (!pass) {
                System.out.println(result + " on the come out, don't pass loses.");
            }
        }
        if (result == 2 || result == 3) {
            if (pass) {
                System.out.println("Craps out, better luck next time.");
            }
            if (!pass) {
                System.out.println("Craps out on the come out, don't pass pays even!");
                bank = payout.payEven(bet);
            }
        }
        if (result == 12) {
            if (pass) {
                System.out.println("Boxcars craps out, pass loses.");
            }
            if (!pass) {
                System.out.println("Boxcars pushes don't pass, try again.");
                bank = bank + bet;
            }
        }
    }

    static void pointRound(int result, int die1, int die2, int point) {
        if (result == point) {
        //Conglaturations, Your Winner
            // Payout as Needed
        }

    }
}





