package byog.Core;

import java.util.Random;

/*
*     public static final int WIDTH = 80;
      public static final int HEIGHT = 30;
    *
    * */
public class Position {
    public int x , y;


    public Position(int x , int y ){
        this.x = x;
        this.y = y;
    }

    public static Position randomPosition(){
        int xPos = Game.RANDOM.nextInt(Game.WIDTH);
        int yPos = Game.RANDOM.nextInt(Game.HEIGHT);
        return new Position(xPos , yPos);
    }
}
