
public class SetBet extends PayBet{
    static boolean hornBetCheck;
    static boolean tableMinimumCheck;


    SetBet(int bet) {
        this.bet = bet;
        while (hornBetCheck && bet % 4 != 0) {
            System.out.println("Horn bets must be in multiples of 4, please try again.");
            bet = Craps.crapsScanner.nextInt();
        }

        while (tableMinimumCheck && bet < 5) {
            System.out.println("The table minimum is 5, please try again.");
            bet = Craps.crapsScanner.nextInt();
        }
        tableMinimumCheck = false;
        hornBetCheck = false;
        Craps.bank -= bet;
        Craps.bankDelta -= bet;
    }


    int bet;
    static boolean singleBetFlag;
}
