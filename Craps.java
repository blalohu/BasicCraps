import java.util.Scanner;


public class Craps {

    //this is the starting money for the player
    //it is static because in no way should it ever be instanced.
    static int bank = 100;

    public void main(String[] args) {
        comeOut();
    }


    void comeOut() {
        boolean pass;
        System.out.println("Make Your Passline Bet");

        Scanner scanner = new Scanner(System.in);
        String setPass = scanner.next();

        System.out.println("What's your bet?");
        int bet = scanner.nextInt();
        PayEvenBet comeOutBet = new PayEvenBet(bet);

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
                //win
            }
            if (!pass) {
                System.out.println(result + " on the come out, don't pass loses.");
                //loss
            }
        }
        if (result == 2 || result == 3) {
            if (pass) {
                System.out.println("Craps out, better luck next time.");//loss
            }
            if (!pass) {
                System.out.println("Craps out on the come out, don't pass pays even!");//win
            }
        }
        if (result == 12) {
            if (pass) {
                System.out.println("Boxcars craps out, pass loses.");//lose
            }
            if (!pass) {
                System.out.println("Boxcars pushes don't pass, try again.");//push
            }
        } else {
            //go to point round
            //set point to int result
        }

    }
}







