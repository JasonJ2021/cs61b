package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random random = new Random(2873123);
    private static class Position {
        private int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    /**
     * @param i ith row from 1
     * @param s
     * @param p
     * @return
     */
    private static int startX(int i, int s, Position p) {
        int startx = p.x;
        if (i <= s) {
            startx = startx + 1 - i;
        } else {
            startx = startx + i - 2 * s;
        }
        return startx;
    }

    private static int rowWidth(int i, int s) {
        int rowWide = s;
        if (i <= s) {
            rowWide = rowWide + 2 * (i - 1);
        } else {
            rowWide = rowWide + 2 * (s - 1) - 2 * (i - s - 1);
        }
        return rowWide;
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        for (int i = 1; i <= 2 * s; i++) {
            int x = startX(i, s, p);
            int y = p.y + i - 1;
            for (int j = 0; j < rowWidth(i, s); j++) {
                world[x + j][y] = t;
            }

        }
    }

    /**
     * given that we have known start
     * left 2 right 2 middle 1
     *
     * @param world
     * @param start
     */
    private static void drawTes(TETile[][] world, Position start, int s) {
        //middle first
        drawVirtical(world , start , 1,s);
        //then left
        for(int i = 2 ; i <= s; i++){
            drawVirtical(world , NStart(start , i , s ,1) , i,s);
        }
        for(int i = 2 ; i <= s; i++){
            drawVirtical(world , NStart(start , i , s ,0) , i,s);
        }
    }

    private static Position NStart(Position start, int n, int s, int left) {
        if (left == 1) {
            return new Position(start.x - (n - 1) * (2 * s - 1), start.y - s * (n - 1));
        }else{
            return new Position(start.x + (n - 1) * (2 * s - 1), start.y - s * (n - 1));
        }
    }

    /**
     * @param world
     * @param start where a vertical starts
     * @param i     1 ~ s
     * @param s     length
     */
    private static void drawVirtical(TETile[][] world, Position start, int i, int s) {
        TETile tile = Tileset.FLOWER;
        for (int j = 1; j <= 2 * s - i; j++) {
            addHexagon(world, new Position(start.x, start.y - 2 * s * (j - 1)), s, randomTile());
        }
    }
    private static TETile randomTile(){
        int i = random.nextInt(4);
        switch (i){
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
        }
        return Tileset.NOTHING;
    }
    @Test
    public void testWidth() {
        assertEquals(3, rowWidth(1, 3));
        assertEquals(4, rowWidth(2, 3));
        assertEquals(5, rowWidth(3, 3));
        assertEquals(5, rowWidth(4, 3));
        assertEquals(4, rowWidth(5, 3));
        assertEquals(3, rowWidth(6, 3));
    }

    public static void main(String[] args) {
        int WIDTH = 100;
        int HEIGHT = 40;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(30,24);
        drawTes(world , p , 3);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
