
public class SetBet extends PayBet{
    static boolean hornBetCheck;


    SetBet(int bet, boolean on) {
       this.on = on; 
this.bet = bet;
        while (hornBetCheck && bet % 4 != 0) {
            System.out.println("Horn bets must be in multiples of 4, please try again.");
            bet = Craps.crapsScanner.nextInt();
        }
        hornBetCheck = false;
        Craps.bank -= bet;
        Craps.bankDelta -= bet;
    }

boolean on;
    int bet;
    static boolean singleBetFlag;
}
