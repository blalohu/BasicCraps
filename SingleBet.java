public class SingleBet extends PayBet {
    int bet;
    boolean hornBetOn;
    public int setBet(int bet, boolean hornBetOn) {
        if (hornBetOn) {
            while (bet % 4 != 0) {
                System.out.println("Sorry, bets on the horn must be in multiples of 4");
                System.out.println("We can't give change");
                bet = Craps.crapsScanner.nextInt();
            }
        }
        return bet;
    }
}
