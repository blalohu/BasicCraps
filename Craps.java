import java.util.Scanner;


public class Craps {
    public static boolean hardBetOn;
    // Let's also define a static Scanner for every function to use equally.
    // This is easier than making a new Scanner whenever we want to get user input.
    private final static Scanner crapsScanner = new Scanner(System.in);
    public static int bank = 100;

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
        int passBet = comeOut(result, die1, die2);
        // Here we just pass the current game's rolled result into a function rather than having it as a global variable. This will be easier if we want to eventually run more craps games, etc.
        // We always do the come out no matter what.

        for (index = 0; index < 5; index++) {
            if (result == passRelevant[index]) {
                passBet = comeOut(result, die1, die2);
            }
        }
        if (index == 5) {
            pointRound(result, passBet, pass);
            endGame();
        }
    }


    public static final int[] passRelevant = {2, 3, 7, 11, 12};
    public static boolean pass;

    public static int comeOut(int result, int die1, int die2) {

        System.out.println("Pass or Don't Pass?");
        System.out.println("Please type \"pass\" or \"don't\"");

        // HERE is a case where we want to use a 'while' loop. Until we get pass or don't pass, we need to keep asking.
        String setPass = "";
        do {
            setPass = crapsScanner.next();
        } while (!setPass.equalsIgnoreCase("pass") && !setPass.equalsIgnoreCase("don't"));
        //TODO: There is an issue here: crapsScanner.next() can't actually pick up "don't pass"
        if (setPass.equalsIgnoreCase("don't")) { // this little setup is so that the next println()
            setPass = setPass.concat(" pass");         // can still return "don't pass"
        }
        System.out.println("You chose " + setPass + "."); // confirmation

        System.out.println("What's your bet? You currently have " + bank + ".");
        int passBet = crapsScanner.nextInt();
        bank = bank - passBet; // deduct money from bank
        System.out.println("Betting " + passBet + ". New total in bank is " + bank + ".");

        if (setPass.equalsIgnoreCase("pass")) {
            pass = true;
            System.out.println(passBet + " on the " + setPass + " line.");
        }
        if (!setPass.equalsIgnoreCase("pass")) {
            pass = false;
            System.out.println(passBet + " on the " + setPass + " line.");
        }

        // For this come out bit, since this bet is really basic, let's not use classes.
        // We can just double when you win.
        // And we don't need to deduct when you lose, since we already deducted when you bet.
        // We also want to display the roll no matter what (to show that we'll move forwards to the next bet).
        System.out.println("[" + die1 + "]" + " [" + die2 + "]");
        PayBet payout = new PayBet();
        if (result == 7 || result == 11) {
            if (pass) {
                System.out.println(result + " on the come out, pass pays even!");
                bank = bank + payout.payEven(passBet);
            }
            if (!pass) {
                System.out.println(result + " on the come out, don't pass loses.");
            }
            endGame();
        }
        if (result == 2 || result == 3) {
            if (pass) {
                System.out.println("Craps out, better luck next time.");
            }
            if (!pass) {
                System.out.println("Craps out on the come out, don't pass pays even!");
                bank = bank + payout.payEven(passBet);
            }
            endGame();
        }
        if (result == 12) {
            if (pass) {
                System.out.println("Boxcars craps out, pass loses.");
            }
            if (!pass) {
                System.out.println("Boxcars pushes don't pass, try again.");
                bank = bank + passBet;
            }
            endGame();
        }
        return passBet;
    }

    static void pointRound(int point, int passBet, boolean pass) {
        String placeBet;
        int die1 = 0;
        int die2 = 0;
        int pointRoundResult = 0;
        do {
            System.out.println("The point is now on: " + point);
            System.out.println("Roll again shooter, or place more bets?");
            // TODO: Fully implement point round. This should include:
            // 1. Prompt player to define their more complicated bet
            // Note, this is a separate process for additional bets
            // The default bet still stands from the pass line
            do {
                System.out.println("Type either bet or roll");
                placeBet = crapsScanner.next();
            } while (!placeBet.equalsIgnoreCase("bet") && !placeBet.equalsIgnoreCase("roll"));


            if (placeBet.equalsIgnoreCase("roll")) {
                // 2. Roll as needed
                Roll pointRoll = new Roll();
                pointRoundResult = pointRoll.getResult();
                die1 = pointRoll.die1;
                die2 = pointRoll.die2;
                System.out.println("[" + die1 + "]" + " [" + die2 + "]");
                if (pointRoundResult == 7 && !pass) {
                    System.out.println("You sevened on don't pass, that's a win!");
                    System.out.println("You were paid " + (passBet * 2));
                }
                if (pointRoundResult == 7 && pass) {
                    System.out.println("Sevened out, bad luck!");
                    System.out.println("You lost " + passBet);
                }
                if (pointRoundResult == point && pass) {
                    bank = bank + passBet * 2; // This is the basic pass payout
                    System.out.println("You got the point!");
                    System.out.println("You were paid " + (passBet * 2));
                }
                if (pointRoundResult == point && !pass) {
                    System.out.println("You hit the point on don't pass, bad luck");
                    System.out.println("You lost " + passBet);
                }

            }

            if (placeBet.equalsIgnoreCase("bet")) {
                System.out.println("What kind of bet are you making?");
                String betType = crapsScanner.next();
                betType = betType.toUpperCase();

                switch (betType) {
                    case "ODDS":
                        oddsBetOn = true;
                        System.out.println("Betting Odds, what's your number?");
                        int oddsNumber = crapsScanner.nextInt();
                        String oddsNumberString = Integer.toString(oddsNumber);
                        while (!oddsRelevant.contains(oddsNumberString)) {
                            System.out.println("That's not a valid number to place odds on");
                            System.out.println("Try 4, 5, 6, 8, 9 or 10");
                            oddsNumber = crapsScanner.nextInt();
                            oddsNumberString = Integer.toString(oddsNumber);
                        }
                        oddsBet(oddsBetOn, pointRoundResult, oddsNumber);
                        break;
                    case "FIELD":
                        fieldBetOn = true;
                        System.out.println("Betting on the Field, 2, 3, 4, 9, 10, 11, 12");
                        System.out.println("Pays 1:1. Snake Eyes is 2:1 and Boxcars are 3:1");
                        break;
                    case "ELEVEN":
                        elevenBetOn = true;
                        singleBetOn = elevenBetOn;
                        System.out.println("Betting on Eleven, odds are 15:1");
                        break;
                    case "TWO":
                        twoBetOn = true;
                        singleBetOn = twoBetOn;
                        System.out.println("Betting on Snake Eyes, odds are 30:1");
                        break;
                    case "THREE":
                        //boolean threeBetOn = true;
                        //singleBetOn = threeBetOn;
                        System.out.println("Betting Ace Deuce, odds are 15:1");
                        break;
                    case "TWELVE":
                        //boolean twelveBetOn = true;
                        //singleBetOn = twelveBetOn;
                        System.out.println("Betting on Boxcars, odds are 30:1");
                        break;
                    case "HORN":
                        //boolean hornBetOn = true;
                        //singleBetOn = hornBetOn;
                        System.out.println("Betting equally on 2, 3, 11 and 12");
                        System.out.println("We pay 27:4 on 2 and 12, and 3:1 on 3 or 11");
                        System.out.println("Multiples of 4 only please; we don't give change");
                        break;
                    case "CRAPS":
                        boolean crapsBetOn = true;
                        singleBetOn = crapsBetOn;
                        System.out.println("Betting on Craps: 2, 3 or 11.");
                        System.out.println("Odds are 7:1");
                        break;
                    case "HARD 4":
                        boolean hardFourOn = true;
                        hardBetOn = hardFourOn;
                        System.out.println("Betting on hard 4, odds are 7:1");
                        break;
                    case "HARD 10":
                        boolean hardTenOn = true;
                        hardBetOn = hardTenOn;
                        System.out.println("Betting on Hard 10, odds are 7:1");
                        break;
                    case "HARD 6":
                        boolean hardSixOn = true;
                        hardBetOn = hardSixOn;
                        System.out.println("Betting on Hard 6, odds are 9:1");
                        break;
                    case "HARD 8":
                        boolean hardEightOn = true;
                        hardBetOn = hardEightOn;
                        System.out.println("Betting on Hard 8, odds are 9:1");
                        break;
                }
            }

            oddsBet(oddsBetOn, pointRoundResult, oddsNumber);
            fieldBet(fieldBetOn, pointRoundResult);
            calledBets(singleBetOn, pointRoundResult); //this handles the single number bets as well as craps and horn
            hardBets(hardBetOn, pointRoundResult, die1, die2); //this handles the "hard" bets AKA doubles
        } while (pointRoundResult != point);
    }

    public static final String oddsRelevant = "4,5,6,8,9,10"; //string because it was misbehaving
    public static boolean oddsBetPayoutOn, oddsBetOn;
    public static int oddsNumber;

    static void oddsBet(boolean oddsBetOn, int result, int oddsNumber) {
        int oddsBet = 0;

        if (oddsBetOn) {
            String oddsNumberString = Integer.toString(oddsNumber); //this has to be a crime, I hate that it works.
            if (oddsRelevant.contains(oddsNumberString) && !oddsBetPayoutOn) {
                System.out.println("You've placed odds on " + oddsNumber);
                System.out.println("How much would you like to bet?");
                oddsBet = crapsScanner.nextInt();
                while (oddsNumber == 6 || oddsNumber == 8 && oddsBet % 6 != 0) {
                    System.out.println("Please enter a multiple of 6 for odds on 6 and 8");
                    System.out.println("We do not give change");
                    oddsBet = crapsScanner.nextInt();
                }
                while ((oddsNumber == 5 || oddsNumber == 9) && (oddsBet % 2 != 0 && oddsBet < 6)) {
                    System.out.println("Please enter a multiple of 2 that is 6 or greater");
                    System.out.println("We do not give change");
                    oddsBet = crapsScanner.nextInt();
                }
            }
            System.out.println("Very well, you've placed " + oddsBet + " on " + oddsNumber);
            oddsBetPayoutOn = true;
        }
        PayBet payout = new PayBet();
        if (oddsBetPayoutOn) {
            if (result == 6 && oddsNumber == 6 || result == 8 && oddsNumber == 8) {
                bank = bank + payout.payOddsSixEight(oddsBet);
                System.out.println("You've paid out your odds on " + oddsNumber);
                System.out.println("You were paid out" + payout.payOddsSixEight(oddsBet));
                oddsBetPayoutOn = false;
            }
            if (result == 5 && oddsNumber == 5 || result == 9 && oddsNumber == 9) {
                bank = bank + payout.payOddsFiveNine(oddsBet);
                System.out.println("You've paid out your odds on " + oddsNumber);
                System.out.println("You were paid out " + payout.payOddsFiveNine(oddsBet));
                oddsBetPayoutOn = false;
            }
            if (result == 4 || result == 10) {
                bank = bank + payout.payOddsFourTen(oddsBet);
                System.out.println("You've paid out your odds on " + oddsNumber);
                System.out.println("You were paid out " + payout.payOddsFourTen(oddsBet));
                oddsBetPayoutOn = false;
            }
            if (result == 7) {
                System.out.println("Unlucky, you've lost your odds");
                System.out.println("You lost " + oddsBet);
                oddsBetPayoutOn = false;
            }
        }
        oddsBetOn = false;
    }


    public static boolean fieldPayoutOn, fieldBetOn;
    public static final int[] fieldRelevant = {2, 3, 4, 9, 10, 11, 12};
    public static int fieldBet;

    public static void fieldBet(boolean fieldBetOn, int result) {


        if (fieldBetOn && !fieldPayoutOn) {
            System.out.println("What's your bet on field? House minimum is 5");
            fieldBet = crapsScanner.nextInt();
            while (fieldBet < 5) {
                System.out.println("House minimum is 5, ser");
                fieldBet = crapsScanner.nextInt();
            }
            fieldPayoutOn = true;
        }
        int index;
        PayBet payout = new PayBet();
        for (index = 0; index < 7; index++) {
            if (result == fieldRelevant[index] && fieldPayoutOn) {
                if (result == 2) {
                    bank = bank + payout.payFieldTwo(fieldBet);
                    System.out.println("Field is 2, pays double");
                    System.out.println("You were paid out " + payout.payFieldTwo(fieldBet));
                    fieldPayoutOn = false;
                }
                if (result == 12) {
                    bank = bank + payout.payFieldTwelve(fieldBet);
                    System.out.println("Field is 12, pays triple");
                    System.out.println("You were paid " + payout.payFieldTwelve(fieldBet));
                    fieldPayoutOn = false;
                } else {
                    bank = bank + payout.payEven(fieldBet);
                    System.out.println(result + " is on the field, you were paid " + payout.payEven(fieldBet));
                    fieldPayoutOn = false;
                }
            }
        }
        if (index == 7 && fieldPayoutOn) {
            System.out.println("Sorry, that number isn't on the field, you lost " + fieldBet);
            fieldBetOn = false;
        }
    }

    public static boolean twoPayoutOn, threePayoutOn, elevenPayoutOn, hornPayoutOn, crapsPayoutOn,
            singleBetOn, twoBetOn, threeBetOn, elevenBetOn, hornBetOn, crapsBetOn;
    public static int twoBet, threeBet, elevenBet, hornBet, crapsBet;

    static void calledBets(boolean singleBetOn, int result) {
        PayBet singleBet = new PayBet();
        if (singleBetOn && twoBetOn) {
            if (!twoPayoutOn) {
                System.out.println("Betting for Snake Eyes, no house minimum");
                System.out.println("Odds are 30:1, what's your bet?");
                twoBet = crapsScanner.nextInt();
            }
            if (twoPayoutOn) {
                if (result == 2) {
                    System.out.println("Snake Eyes! What a throw!");
                    System.out.println("You've won " + (twoBet * 30) + "!");
                    bank = bank + singleBet.payTwoTwelveSingle(twoBet);
                } else {
                    System.out.println("Sorry bud, no 2 here.");
                }
            }
        }
        if (singleBetOn && threeBetOn) {
            if (!threePayoutOn) {
                System.out.println("Betting on 3, no house minimum");
                System.out.println("Odds are 15:1, what's your bet?");
                threeBet = crapsScanner.nextInt();
            }
            if (threePayoutOn) {
                if (result == 3) {
                    System.out.println("Ace Deuce! What a throw!");
                    System.out.println("You've won " + (threeBet * 15) + "!");
                    bank = bank + singleBet.payThreeEleven(threeBet);
                } else {
                    System.out.println("Sorry bud, no 3 here.");
                }
            }
        }
        if (singleBetOn && elevenBetOn) {
            if (!elevenPayoutOn) {
                System.out.println("Betting on yo, no house minimum");
                System.out.println("Odds are 15:1, what's your bet?");
                elevenBet = crapsScanner.nextInt();
            }
            if (elevenPayoutOn) {
                if (result == 11) {
                    System.out.println("Yo! That's 11! What a throw!");
                    System.out.println("You've won " + (elevenBet * 15) + "!");
                    bank = bank + singleBet.payThreeEleven(elevenBet);
                } else {
                    System.out.println("Sorry bud, no 3 here.");
                }
            }
        }
        if (singleBetOn && elevenBetOn) { //TODO: This is for 12
            if (!elevenPayoutOn) {
                System.out.println("Betting on yo, no house minimum");
                System.out.println("Odds are 15:1, what's your bet?");
                elevenBet = crapsScanner.nextInt();
            }
            if (elevenPayoutOn) {
                if (result == 11) {
                    System.out.println("Yo! That's 11! What a throw!");
                    System.out.println("You've won " + (elevenBet * 15) + "!");
                    bank = bank + singleBet.payThreeEleven(elevenBet);
                } else {
                    System.out.println("Sorry bud, no 3 here.");
                }
            }
        }
    }

    static void hardBets(boolean hardBetOn, int result, int die1, int die2) {

    }

    private static void endGame() {
        System.out.println("You currently have " + bank + " chips");
        System.out.println("Play again?");
        String result = crapsScanner.next();
        if (result.equalsIgnoreCase("y")) {
            startCrapsGame(); // Start another game.
        } else {
            System.exit(0); // Exit with return code 0. 0 means "everything worked as it should".
        }
    }
}






