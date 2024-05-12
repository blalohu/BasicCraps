import java.util.Scanner;


public class Craps {

    //this is the starting money for the player
    //it is static because in no way should it ever be instanced.
    int bank = 100;

    public void main(String[] args) {
        comeOut();
    }

    //consider making this function return int in order to pass a bet variable to main
    void comeOut() {
        boolean pass;
        System.out.println("Pass or Don't Pass?");

        Scanner scanner = new Scanner(System.in);
        String setPass = scanner.next();

        System.out.println("What's your bet?");
        int bet = scanner.nextInt();
        PayEvenBet comeOutBet = new PayEvenBet();
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
            //go to point round
            //set point to int result
        }
    //set up a loop from here to top
    }
}







