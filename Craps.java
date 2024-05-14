import java.util.Scanner;


public class Craps {

    //this is the starting money for the player
    //it is static because in no way should it ever be instanced.
    public static int bank = 100;
    // I've commented out the other variables - for the points, the result, and the bet.
    // Because these variables depend on the current 'round', it's probably easier if we just create them
    // every time individually rather than keeping them around, which could cause problems.
    //    static int point;
    //    static int result;
    //    static int bet;
    // boolean pass; -- We don't want passing to be defined here, since it's defined by the 'come out'.
    //boolean oddsBetActive;
    //int oddsBet;
    // Let's also define a static Scanner for every function to use equally.
    // This is easier than making a new Scanner whenever we want to get user input.
    private static Scanner crapsScanner = new Scanner(System.in);

    public static void main(String[] args) {
        startCrapsGame();
        // A 'while' loop probably isn't what we want here - 'while' means 'keep repeating whatever's in the loop until the info there is false.
        // We want to not do the point round if you won via a come out roll on the pass line,
        // but that'd be easier with an if, I think. Since if we use a while, it'll wind up repeating the point round
        // forever. Additionally, it'll be easier if we fully code in just the come-out first.
        // In fact, right now, I'll just make adjustments as needed to get to the very first phase.
        //  while (point != result && point != 7) {
        //      pointRound();
        //  }
    }

    private static void startCrapsGame() {
        System.out.println("A game of Craps begins.");
        Roll roll = new Roll();
        int result = roll.getResult();
        comeOut(result); // Here we just pass the current game's rolled result into a function rather than having it as a global variable. This will be easier if we want to eventually run more craps games, etc.
        // We always do the come out no matter what.
        if (result != 7 && result != 11 && result != 2 && result != 3 && result != 12) {
            // TODO: Fully implement point round. This should include:
            // 1. Prompt player to define their more complicated bet
            // 2. Roll as needed
            // 3. Payout as needed
            // 4. End the game afterwards.
        } else {
            endGame();
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

    private static void comeOut(int result) {
        boolean pass = false;
//        while (result == 7 || result == 11 || result == 2 || result == 3 || result == 12) { /* figure out how to array
//        this list for neatness later */
        // We don't want to use a while loop here either - technically, we should be asking about whether or not the player wants to pass
        // no matter what. This while loop means technically nothing would happen unless those reults were rolled.
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
        System.out.println("The roll is " + result + ".");

        if (result == 7 || result == 11) {
            if (pass) {
                System.out.println(result + " on the come out, pass pays even!");
                bank = bank + bet * 2;
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
                bank = bank + bet * 2;
            }
        }
        if (result == 12) {
            if (pass) {
                System.out.println("Boxcars craps out, pass loses.");
            }
            if (!pass) {
                System.out.println("Boxcars pushes don't pass, try again.");
            }
        }
    }

//    void pointRound() {
//        while (result != point && result != 7) {
//            Roll pointRoll = new Roll();
//            int result = pointRoll.getResult();
//            if (pass) {
//                if (result == point) {
//                    bank = bank + comeOutBet.payoutEven(bet);
//                    //do the motions here
//                }
//                if (result == 7) {
//                    bank = bank - comeOutBet.payoutEven(bet);
//                    //end loop
//                }
//            }
//            if (!pass) {
//                if (result == point) {
//                    bank = bank - comeOutBet.payoutEven(bet);
//                }
//                if (result == 7) {
//                    bank = bank + comeOutBet.payoutEven(bet);
//                }
//            }
//        }
//    }
}






