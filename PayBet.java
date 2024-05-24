public class PayBet {

    //this is where all my bets are gonna go, I don't think I need to abstract them out to different classes

    public int payEven(int bet) {
        return bet * 2;
    }

    public int payOddsSixEight(int bet) {
        //Payout odds are 6:5
        return (int) (bet * 2.2);
        //odds bets for 6/8 will only be allowed in multiples of 6 to avoid truncation errors when casting to int
    }

    public int payAgainstSixEight(int bet) {
        //Payout odds are 5:6
        return (int) (bet * 1.83333);
    }

    public int payOddsFiveNine(int bet) {
        //payout odds are 3:2
        return (int) (bet * 2.5);
        //similar deal for all 5/9 odds bets, except in intervals of 2
    }

    public int payAgainstFiveNine(int bet) {
        //payout odds are 2:3
        return (int) (bet * 1.6666);
    }

    public int payOddsFourTen(int bet) {
        //payout odds are 2:1
        return bet * 3;
    }

    public int payAgainstFourTen(int bet) {
        //payout odds are 1:2
        return (int) (bet * 1.5);
    }

    public int payFieldTwo(int bet) {
        //payout odds are 2:1
        return bet * 3;
    }

    public int payFieldTwelve(int bet) {
        //payout odds are 3:1
        return bet * 4;
    }

    public int payTwoTwelveSingle(int bet) {
        //payout odds are 30:1
        return bet * 30;
    }

    public int payThreeEleven(int bet) {
        //payout odds are 15:1
        return bet * 15;
    }

    public int payTwoTwelveHorn(int bet) {
        //payout odds are 27:4
        return bet * 27 / 4;
    }

    public int payThreeElevenHorn(int bet) {
        //payout odds are 3:1
        return bet * 4;
    }

    public int payCraps(int bet) {
        //payout odds are 7:1
        return bet * 8;
    }
}
