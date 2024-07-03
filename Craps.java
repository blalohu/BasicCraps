import java.util.Scanner;

/* A note regarding this code: This comment explains some of the shortcomings, and things I've learned since writing
much of this code. Using classes to handle things like setting bets, as well as subtracting the relevant values from
bank and bankDelta seriously cuts down on the complexity of the code as well as makes the mainline code in Craps.java
more readable. As such, there will be mixed ways in which the code is implemented, with some being done in the
main Craps class itself, with newer code being abstracted out to SetBet.java, which extends PayBet.java in order to
provide payouts.

Some things to do are to standardize these implementations into the older code, as well as add a "help" command to
explain the basic rules of craps, but these are far future considerations and this has already gone beyond my original
scope. To avoid feature creep, I will finish what I have first. I also need to add Come bets, but this should be fairly
easy as it's just another implementation of the comeOut and Pass code.
 */
//TODO: Annotate the methods better to explain the code. Get in the habit of this.
//TODO Move these variable declarations closer to the spot where they are relevant.
public class Craps {
    public static final int[] passRelevant = {2, 3, 7, 11, 12};
    public static final String oddsRelevant = "4,5,6,8,9,10"; //string because it was misbehaving
    public static final int[] fieldRelevant = {2, 3, 4, 9, 10, 11, 12};
    // Let's also define a static Scanner for every function to use equally.
    // This is easier than making a new Scanner whenever we want to get user input.
    final static Scanner crapsScanner = new Scanner(System.in);
    public static boolean hardBetOn;
    public static int bank = 100;
    public static int bankDelta = 0;
    public static boolean pass;
    public static int pointRoundResult;
    public static boolean oddsBetPayoutOn, oddsBetOn;
    public static int oddsNumber, oddsBet;
    public static boolean fieldPayoutOn, fieldBetOn;
    public static int fieldBet;


    public static void main(String[] args) {
        startCrapsGame();
    }

    private static void startCrapsGame() {
        int index;
        int bankDelta = 0;

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

    public static int comeOut(int result, int die1, int die2) {
        System.out.println("Pass or Don't Pass?");
        System.out.println("Please type \"pass\" or \"don't\"");

        String setPass = "";
        do {
            setPass = crapsScanner.next();
        } while (!setPass.equalsIgnoreCase("pass") && !setPass.equalsIgnoreCase("don't"));
        if (setPass.equalsIgnoreCase("don't")) { // this little setup is so that the next println()
            setPass = setPass.concat(" pass");         // can still return "don't pass"
        }
        System.out.println("You chose " + setPass + "."); // confirmation

        System.out.println("What's your bet? You currently have " + bank + ".");
        int passBet = crapsScanner.nextInt();
        bank -= passBet; // deduct money from bank
        bankDelta -= passBet;

        if (setPass.equalsIgnoreCase("pass")) {
            pass = true;
            System.out.println(passBet + " on the " + setPass + " line.");
        }
        if (!setPass.equalsIgnoreCase("pass")) {
            pass = false;
            System.out.println(passBet + " on the " + setPass + " line.");
        }

        System.out.println("[" + die1 + "]" + " [" + die2 + "]");
        PayBet payout = new PayBet();
        if (result == 7 || result == 11) {
            if (pass) {
                System.out.println(result + " on the come out, pass pays even!");
                bank += payout.payEven(passBet);
                bankDelta += payout.payEven(passBet);
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
                bank += payout.payEven(passBet);
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
        SetBet twoBet = null;
        SetBet threeBet = null;
        SetBet twelveBet = null;
        SetBet elevenBet = null;
        SetBet hornBet = null;
        SetBet crapsBet = null;




        PayBet payout = new PayBet();
        do {
            System.out.println("The point is now on: " + point);
            System.out.println("Roll again shooter, or place more bets?");
            // Note, this is a separate process for additional bets
            // The default bet still stands from the pass line
            do {
                System.out.println("Type either bet or roll");
                placeBet = crapsScanner.next();
            } while (!placeBet.equalsIgnoreCase("bet") && !placeBet.equalsIgnoreCase("roll"));


            if (placeBet.equalsIgnoreCase("roll")) {

                Roll pointRoll = new Roll();
                pointRoundResult = pointRoll.getResult();
                die1 = pointRoll.die1;
                die2 = pointRoll.die2;
                System.out.println("[" + die1 + "]" + " [" + die2 + "]");
                if (pointRoundResult == 7 && !pass) {
                    System.out.println("You sevened on don't pass, that's a win!");
                    System.out.println("You were paid " + (payout.payEven(passBet)));
                    bank += payout.payEven(passBet);
                    bankDelta += payout.payEven(passBet);
                    oddsBetOn = false;
                }
                if (pointRoundResult == 7 && pass) {
                    System.out.println("Sevened out, bad luck!");
                    System.out.println("You lost " + passBet);
                    oddsBetOn = false;
                }
                if (pointRoundResult == point && pass) {
                    System.out.println("You got the point!");
                    System.out.println("You were paid " + (payout.payEven(passBet)));
                    bank += payout.payEven(passBet);
                    bankDelta += payout.payEven(passBet);
                    oddsBetOn = false;
                }
                if (pointRoundResult == point && !pass) {
                    System.out.println("You hit the point on don't pass, bad luck");
                    System.out.println("You lost " + passBet);
                    oddsBetOn = false;
                }

                boolean payFlag = false; //this flag trips if no single bets were paid for a default "you lose" message
                switch (pointRoundResult) { //For Single Round bets being paid out.
                    case 2:
                        if (hornBet.hornBetOn) {
                            bank += hornBet.payTwoTwelveHorn(hornBet.bet);
                            bankDelta += hornBet.payTwoTwelveHorn(hornBet.bet);
                            System.out.println("Two is in the horn! You were paid out " + hornBet.payTwoTwelveHorn(hornBet.bet));
                            payFlag = true;
                        }
                        if (crapsBet.crapsBetOn) {
                            bank += crapsBet.payCraps(crapsBet.bet);
                            bankDelta += crapsBet.payCraps(crapsBet.bet);
                            payFlag = true;
                        }
                        if (twoBet.twoBetOn) {
                            bank += twoBet.payTwoTwelveSingle(twoBet.bet);
                            bankDelta += twoBet.payTwoTwelveSingle(twoBet.bet);
                        }
                        break;
                    case 3:
                        if (hornBet.hornBetOn) {
                            bank += hornBet.payThreeElevenHorn(hornBet.bet);
                            bankDelta += hornBet.payThreeElevenHorn(hornBet.bet);
                        }
                        if (crapsBet.crapsBetOn) {
                            bank += crapsBet.payCraps(crapsBet.bet);
                            bankDelta += crapsBet.payCraps(crapsBet.bet);
                        }
                        if (threeBet.threeBetOn) {
                            bank += threeBet.payThreeEleven(threeBet.bet);
                            bankDelta += threeBet.payThreeEleven(threeBet.bet);
                        }
                        break;

                    default:
                        break; // for numbers 4 through 10, which do nothing in the single bet space.

                    case 11:
                        if (hornBet.hornBetOn) {
                            bank += hornBet.payThreeElevenHorn(hornBet.bet);
                            bankDelta += hornBet.payThreeElevenHorn(hornBet.bet);
                            payFlag = true;
                        }
                        if (elevenBet.elevenBetOn) {
                            bank += elevenBet.payThreeEleven(elevenBet.bet);
                            bankDelta += elevenBet.payThreeEleven(elevenBet.bet);
                            payFlag = true;
                        }
                        break;
                    case 12:
                        if (hornBet.hornBetOn) {
                            bank += hornBet.payTwoTwelveHorn(hornBet.bet);
                            bankDelta += hornBet.payTwoTwelveHorn(hornBet.bet);
                        }
                        if (crapsBet.crapsBetOn) {
                            bank += crapsBet.payCraps(crapsBet.bet);
                            bankDelta += crapsBet.payCraps(crapsBet.bet);
                        }
                        if (twoBet.twoBetOn) {
                            bank += twelveBet.payTwoTwelveSingle(twelveBet.bet);
                            bankDelta += twelveBet.payTwoTwelveSingle(twelveBet.bet);
                        }
                        break;
                }

                if (!payFlag) {
                    System.out.println("Sorry bud, looks like odds weren't in your favor for the place bets.");
                }
            }


            if (placeBet.equalsIgnoreCase("bet")) {
                System.out.println("What kind of bet are you making?");
                String betType = crapsScanner.next();
                betType = betType.toUpperCase();
                int bet = 0;
                switch (betType) {
                    case "ODDS":
                        oddsBetOn = true;
                        System.out.println("Betting odds on " + point);
                        oddsBet(oddsBetOn, pointRoundResult, point);
                        break;
                    case "FIELD":
                        fieldBetOn = true;
                        System.out.println("Betting on the Field, 2, 3, 4, 9, 10, 11, 12");
                        System.out.println("Pays 1:1. Snake Eyes is 2:1 and Boxcars are 3:1");
                        break;
                    case "ELEVEN":
                        elevenBet = new SetBet();
                        System.out.println("Betting on 11, odds 15:1");
                        bet = crapsScanner.nextInt();
                        elevenBet.setBet(bet, bank, bankDelta);
                        elevenBet.elevenBetOn = true;
                        break;
                    case "TWO":
                        twoBet = new SetBet();
                        System.out.println("Betting on Snake Eyes, odds are 30:1");
                        bet = crapsScanner.nextInt();
                        twoBet.setBet(bet, bank, bankDelta);
                        twoBet.twoBetOn = true;
                        break;
                    case "THREE":
                        threeBet = new SetBet();
                        System.out.println("Betting Ace Deuce, odds are 15:1");
                        bet = crapsScanner.nextInt();
                        threeBet.setBet(bet, bank, bankDelta);
                        threeBet.threeBetOn = true;
                        break;
                    case "TWELVE":
                        twelveBet = new SetBet();
                        System.out.println("Betting on Boxcars, odds are 30:1");
                        bet = crapsScanner.nextInt();
                        twelveBet.setBet(bet, bank, bankDelta);
                        twelveBet.twelveBetOn = true;
                        break;
                    case "HORN":
                        hornBet = new SetBet();
                        System.out.println("Betting equally on 2, 3, 11 and 12");
                        System.out.println("We pay 27:4 on 2 and 12, and 3:1 on 3 or 11");
                        System.out.println("Multiples of 4 only please; we don't give change");
                        bet = crapsScanner.nextInt();
                        hornBet.setBet(bet, bank, bankDelta);
                        hornBet.hornBetOn = true;
                        break;
                    case "CRAPS":
                        crapsBet = new SetBet();
                        System.out.println("Betting on Craps: 2, 3 or 11.");
                        System.out.println("Odds are 7:1");
                        bet = crapsScanner.nextInt();
                        crapsBet.setBet(bet, bank, bankDelta);
                        crapsBet.crapsBetOn = true;
                        break;
                    case "HARD 4":
                        hardFourBet = new SetBet();
                        hardFourOn = true;
                        hardBetOn = true;
                        System.out.println("Betting on hard 4, odds are 7:1");
                        System.out.println("How much would you like to bet?");
                        bet = crapsScanner.nextInt();
                        hardFourBet.setBet(bet, bank, bankDelta);
                        break;
                    case "HARD 10":
                        hardTenBet = new SetBet();
                        hardTenOn = true;
                        hardBetOn = true;
                        System.out.println("Betting on Hard 10, odds are 7:1");
                        System.out.println("How much would you like to bet?");
                        bet = crapsScanner.nextInt();
                        hardTenBet.setBet(bet, bank, bankDelta);
                        break;
                    case "HARD 6":
                        hardSixBet = new SetBet();
                        hardSixOn = true;
                        hardBetOn = true;
                        System.out.println("Betting on Hard 6, odds are 9:1");
                        System.out.println("How much would you like to bet?");
                        bet = crapsScanner.nextInt();
                        hardSixBet.setBet(bet, bank, bankDelta);
                        break;
                    case "HARD 8":
                        SetBet hardEightBet = new SetBet();
                        hardEightOn = true;
                        hardBetOn = true;
                        System.out.println("Betting on Hard 8, odds are 9:1");
                        System.out.println("How much would you like to bet?");
                        bet = crapsScanner.nextInt();
                        hardEightBet.setBet(bet, bank, bankDelta);
                        break;
                }
            }
            oddsBet(oddsBetOn, pointRoundResult, oddsNumber);
            fieldBet(fieldBetOn, pointRoundResult);
            hardBets(hardBetOn, pointRoundResult, die1, die2); //this handles the "hard" bets AKA doubles
        } while (pointRoundResult != point && pointRoundResult != 7);
    }

    public static void oddsBet(boolean oddsBetOn, int result, int point) {

        if (oddsBetOn) {
            if (!oddsBetPayoutOn && pass) {
                System.out.println("You've placed odds on " + point);
                System.out.println("How much would you like to bet?");
                oddsBet = crapsScanner.nextInt();
                while ((point == 6 || point == 8) && oddsBet % 6 != 0) {
                    System.out.println("Please enter a multiple of 6 for odds on 6 and 8");
                    System.out.println("We do not give change");
                    oddsBet = crapsScanner.nextInt();
                }
                while ((point == 5 || point == 9) && (oddsBet % 2 != 0 && oddsBet < 6)) {
                    System.out.println("Please enter a multiple of 2 that is 6 or greater");
                    System.out.println("We do not give change");
                    oddsBet = crapsScanner.nextInt();
                }
                bank -= oddsBet;
                bankDelta -= oddsBet;
            }
            if (!oddsBetPayoutOn && !pass) {
                System.out.println("You've placed odds against " + point);
                System.out.println("How much would you like to bet?");
                oddsBet = crapsScanner.nextInt();
                while (oddsBet % 6 != 0) {
                    System.out.println("Please enter a multiple of 6 for odds against " + point);
                    System.out.println("We do not give change.");
                    oddsBet = crapsScanner.nextInt();
                }
                bank -= oddsBet;
                bankDelta -= oddsBet;
            }
            if (pass && !oddsBetPayoutOn) {
                System.out.println("Very well, you've placed " + oddsBet + " on " + point);
                oddsBetPayoutOn = true;
            }
            if (!pass && !oddsBetPayoutOn) {
                System.out.println("Very well, you've placed " + oddsBet + " against " + point);
                oddsBetPayoutOn = true;
            }
        }
        PayBet payout = new PayBet();
        if (oddsBetPayoutOn && !oddsBetOn) {
            if (pass) {
                if (result == 6 && point == 6 || result == 8 && point == 8) {
                    System.out.println("You've paid out your odds on " + point);
                    System.out.println("You were paid out" + payout.payOddsSixEight(oddsBet));
                    bank += payout.payOddsSixEight(oddsBet);
                    bankDelta += payout.payOddsSixEight(oddsBet);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;

                }
                if (result == 5 && point == 5 || result == 9 && point == 9) {
                    System.out.println("You've paid out your odds on " + point);
                    System.out.println("You were paid out " + payout.payOddsFiveNine(oddsBet));
                    bank += payout.payOddsFiveNine(oddsBet);
                    bankDelta += payout.payOddsFiveNine(oddsBet);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;

                }
                if (result == 4 && point == 4 || result == 10 && point == 10) {
                    System.out.println("You've paid out your odds on " + point);
                    System.out.println("You were paid out " + payout.payOddsFourTen(oddsBet));
                    bank += payout.payOddsFourTen(oddsBet);
                    bankDelta += payout.payOddsFourTen(oddsBet);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;

                }
                if (result == 7) {
                    System.out.println("Unlucky, you've lost your odds");
                    System.out.println("You lost " + oddsBet);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;

                }
            }
            if (!pass) {
                if (result == 6 && point == 6 || result == 8 && point == 8) {
                    System.out.println("You've lost your odds on " + point);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;
                }
                if (result == 5 && point == 5 || result == 9 && point == 9) {
                    System.out.println("You've lost your odds on " + point);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;

                }
                if (result == 4 && point == 4 || result == 10 && point == 10) {

                    System.out.println("You've lost your odds on " + point);
                    oddsBet = 0;
                    oddsBetPayoutOn = false;


                }
                if (result == 7) {
                    System.out.println("Seven out pays your odds against " + point + "!");
                    System.out.println("You were paid ");
                    if (point == 6 || point == 8) {
                        System.out.print(payout.payAgainstSixEight(oddsBet));
                        bank += payout.payAgainstSixEight(oddsBet);
                        bankDelta += payout.payAgainstSixEight(oddsBet);
                        oddsBet = 0;
                        oddsBetPayoutOn = false;
                    }
                    if (point == 5 || point == 9) {
                        System.out.print(payout.payAgainstFiveNine(oddsBet));
                        bank += payout.payAgainstFiveNine(oddsBet);
                        bankDelta += payout.payAgainstFiveNine(oddsBet);
                        oddsBet = 0;
                        oddsBetPayoutOn = false;
                    }
                    if (point == 4 || point == 10) {
                        System.out.print(payout.payAgainstFourTen(oddsBet));
                        bank += payout.payAgainstFourTen(oddsBet);
                        bankDelta += payout.payAgainstFourTen(oddsBet);
                        oddsBet = 0;
                        oddsBetPayoutOn = false;
                    }

                }
            }
        }
    }

    public static void fieldBet(boolean fieldBetOn, int pointRoundResult) {


        if (fieldBetOn && !fieldPayoutOn) {
            System.out.println("What's your bet on field? House minimum is 5");
            fieldBet = crapsScanner.nextInt();
            while (fieldBet < 5) {
                System.out.println("House minimum is 5, ser");
                fieldBet = crapsScanner.nextInt();
            }
            bank -= fieldBet;
            bankDelta -= fieldBet;
            fieldPayoutOn = true;
        }
        int index;
        PayBet payout = new PayBet();
        for (index = 0; index < 7; index++) {
            if (pointRoundResult == fieldRelevant[index] && fieldPayoutOn) {
                if (pointRoundResult == 2) {

                    System.out.println("Field is 2, pays double");
                    System.out.println("You were paid out " + payout.payFieldTwo(fieldBet));
                    bank += payout.payFieldTwo(fieldBet);
                    bankDelta += payout.payFieldTwo(fieldBet);
                }
                if (pointRoundResult == 12) {
                    System.out.println("Field is 12, pays triple");
                    System.out.println("You were paid " + payout.payFieldTwelve(fieldBet));
                    bank += payout.payFieldTwelve(fieldBet);
                    bankDelta += payout.payFieldTwelve(fieldBet);
                } else {

                    System.out.println(pointRoundResult + " is on the field, you were paid " + payout.payEven(fieldBet));
                    bank += payout.payEven(fieldBet);
                    bankDelta += payout.payEven(fieldBet);
                }
                fieldPayoutOn = false;
            }
        }
        if (index == 7 && fieldPayoutOn) {
            System.out.println("Sorry, that number isn't on the field, you lost " + fieldBet);
            fieldBetOn = false;
        }
    }

    static boolean hardFourOn;
    static boolean hardSixOn;
    static boolean hardEightOn;
    static boolean hardTenOn;
    static SetBet hardFourBet;
    static SetBet hardSixBet;
    static SetBet hardEightBet;
    static SetBet hardTenBet;
    public static void hardBets(boolean hardBetOn, int pointRoundResult, int die1, int die2) {

        if (hardBetOn) {
            if (hardFourOn) {
                if (pointRoundResult == 4 && die1 == die2){
                    System.out.println("Four the hard way, you win!");
                    bank += hardFourBet.payFourTenHard(hardFourBet.bet);
                    bankDelta += hardFourBet.payFourTenHard(hardFourBet.bet);
                    System.out.println("You won " + hardFourBet.payFourTenHard(hardFourBet.bet));
                    hardFourOn = false;
                }
                if (pointRoundResult == 4 && die1 != die2) {
                    System.out.println("Four the easy way, better luck next time.");
                    hardFourOn = false;
                }
            }
            if (hardSixOn) {
                if (pointRoundResult == 6 && die1 == die2){
                    System.out.println("Six the hard way, you win!");
                    bank += hardSixBet.paySixEightHard(hardSixBet.bet);
                    bankDelta += hardSixBet.paySixEightHard(hardSixBet.bet);
                    System.out.println("You won " + hardSixBet.paySixEightHard(hardSixBet.bet));
                    hardSixOn = false;
                }
                if (pointRoundResult == 6 && die1 != die2) {
                    System.out.println("Six the easy way, better luck next time.");
                    hardSixOn = false;
                }
            }
            if (hardEightOn) {
                if (pointRoundResult == 8 && die1 == die2) {
                    System.out.println("Eight the hard way, you win!");
                    bank += hardEightBet.paySixEightHard(hardEightBet.bet);
                    bankDelta += hardEightBet.paySixEightHard(hardEightBet.bet);
                    System.out.println("You won " + hardEightBet.paySixEightHard(hardEightBet.bet));
                    hardEightOn = false;
                }
                if (pointRoundResult == 8 && die1 != die2) {
                    System.out.println("Eight the easy way, better luck next time.");
                    hardEightOn = false;
                }
            }
            if (hardTenOn) {
                if (pointRoundResult == 10 && die1 == die2){
                    System.out.println("10 the hard way, you win!");
                    bank += hardTenBet.payFourTenHard(hardTenBet.bet);
                    bankDelta += hardTenBet.payFourTenHard(hardTenBet.bet);
                    System.out.println("You won " + hardTenBet.payFourTenHard(hardTenBet.bet));
                    hardTenOn = false;
                }
                if (pointRoundResult == 10 && die1 != die2) {
                    System.out.println("Four the easy way, better luck next time.");
                    hardTenOn = false;
                }
            }
            if (pointRoundResult == 7) {
                System.out.println("Sevened out, hard bets are cleared.");
                hardFourOn = false;
                hardSixOn = false;
                hardEightOn = false;
                hardTenOn = false;
                hardBetOn = false;
            }
        }
    }

    private static void endGame() {
        System.out.println("You currently have " + bank + " chips");
        System.out.println("You made in total " + bankDelta + " chips this round.");
        System.out.println("Play again?");
        bankDelta = 0;

        String result;
        do {
            result = crapsScanner.next();
            if (result.equalsIgnoreCase("y")) {
                startCrapsGame();
            }// Start another game.
            if (result.equalsIgnoreCase("n")) {
                System.exit(0);
            }// Exit with return code 0. 0 means "everything worked as it should".
            else {
                System.out.println("Input y/n");
                result = crapsScanner.next();
            }
        } while (!result.equalsIgnoreCase("y") && !result.equalsIgnoreCase("n"));
    }
}







