public class PayEvenBet extends Bet {

    int payoutEven(int bet) {
        return bet * 2;
    }

    int payoutPush(int bet) {
        return bet;
    }


}
