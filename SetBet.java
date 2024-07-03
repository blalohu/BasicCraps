
public class SetBet extends PayBet {
    static boolean hornBetOn;
    static boolean crapsBetOn;
    static boolean twoBetOn, threeBetOn, elevenBetOn, twelveBetOn; // to avoid things getting all null pointery.

    int bet;
    public void setBet(int bet, int bank, int bankDelta) {
        if (hornBetOn) {
            while (bet % 4 != 0) {
                System.out.println("Sorry, bets on the horn must be in multiples of 4");
                System.out.println("We can't give change");
                bet = Craps.crapsScanner.nextInt();

            }
        }
        Craps.bank -= bet;
        Craps.bankDelta -= bet;
    }
}
