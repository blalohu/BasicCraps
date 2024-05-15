public class payBet {

    //this is where all my bets are gonna go, I don't think I need to abstract them out to different classes

    public int payEven(int bet){
        return bet * 2;
    }
    public int payOddsSixEight(int bet) {
        //Payout odds are 6:5
        return (int) (bet * 2.2);
        //odds bets for 6/8 will only be allowed in multiples of 6 to avoid truncation errors when casting to int
    }
    public int payOddsFiveNine(int bet) {
        //payout odds are 3:2
        return (int) (bet * 2.5);
        //similar deal for all 5/9 odds bets, except in intervals of 5
    }
    public int payOddsFourTen(int bet) {
        //payout odds are 2:1
        return bet * 3;
    }
}
