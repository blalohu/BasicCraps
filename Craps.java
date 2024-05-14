import java.util.Scanner;


public class Craps {

    //this is the starting money for the player
    //it is static because in no way should it ever be instanced.
    static int bank = 100;
    int point;
    int result;
    int bet;
    boolean pass;
    //boolean oddsBetActive;
    //int oddsBet;
    PayEvenBet comeOutBet = new PayEvenBet();

    public void main(String[] args) {
        do {
            comeOut();
        } while (result == 7 || result == 11 || result == 2 || result == 3 || result == 12);
        while (point != result && point != 7) {
            pointRound();
        }
    }

    void comeOut() {
        boolean pass;
        while (result == 7 || result == 11 || result == 2 || result == 3 || result == 12) { /* figure out how to array
        this list for neatness later */
            System.out.println("Pass or Don't Pass?");

            Scanner scanner = new Scanner(System.in);
            String setPass = scanner.next();

            System.out.println("What's your bet?");
            int bet = scanner.nextInt();
            bank = bank - bet;


            if (setPass.equalsIgnoreCase("pass")) {
                pass = true;
                System.out.println(bet + " on the " + setPass + " line.");
            }
            if (setPass.equalsIgnoreCase("don't pass")) {
                pass = false;
                System.out.println(bet + " on the " + setPass + " line.");
            }

            Roll comeOutRoll = new Roll();
            int result = comeOutRoll.getResult();

            if (result == 7 || result == 11) {
                if (pass) {
                    System.out.println(result + " on the come out, pass pays even!");
                    bank = bank + comeOutBet.payoutEven(bet);
                    //win
                }
                if (!pass) {
                    System.out.println(result + " on the come out, don't pass loses.");
                    bank = bank - comeOutBet.payoutEven(bet);
                    //loss
                }
            }
            if (result == 2 || result == 3) {
                if (pass) {
                    System.out.println("Craps out, better luck next time.");
                    bank = bank - comeOutBet.payoutEven(bet);//loss
                }
                if (!pass) {
                    System.out.println("Craps out on the come out, don't pass pays even!");
                    bank = bank + comeOutBet.payoutEven(bet);//win
                }
            }
            if (result == 12) {
                if (pass) {
                    System.out.println("Boxcars craps out, pass loses.");
                    bank = bank - comeOutBet.payoutEven(bet);//lose
                }
                if (!pass) {
                    System.out.println("Boxcars pushes don't pass, try again.");
                    bank = bank + comeOutBet.payoutPush(bet); //push
                }
            } else {
                int point = result;
            }
        }
    }

            void pointRound() {

            while (result != point && result != 7) {
                Roll pointRoll = new Roll();
                int result = pointRoll.getResult();
                if (pass) {
                    if (result == point) {
                    bank = bank + comeOutBet.payoutEven(bet);
                    //do the motions here
                    }
                    if (result == 7) {
                        bank = bank - comeOutBet.payoutEven(bet);
                        //end loop
                    }
                }
                if (!pass) {
                    if (result == point) {
                        bank = bank - comeOutBet.payoutEven(bet);
                    }
                    if (result == 7) {
                        bank = bank + comeOutBet.payoutEven(bet);
                }
            }
        }
    }
}






