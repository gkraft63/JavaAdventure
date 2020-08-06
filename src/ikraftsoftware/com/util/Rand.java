package ikraftsoftware.com.util;

import java.util.Random;

public class Rand {
    public static int rand(int min,int max){
        if (min > max || ( max - min +1 > Integer.MAX_VALUE)){
            throw new IllegalArgumentException("Invalid integer range!");
        }
        return new Random().nextInt(max - min +1) + min;
    }
}
