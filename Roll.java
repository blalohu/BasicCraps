import java.util.Random;

public class Roll {
        int die1 = new Random().nextInt(6) + 1;
        int die2 = new Random().nextInt(6) + 1;
        public int getResult(){
            return die1 + die2;
        }


}

