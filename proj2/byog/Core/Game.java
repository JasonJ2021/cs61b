package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 81;
    public static final int HEIGHT = 31;
    public static Random RANDOM;
    public static long Seed;
    public static TETile[][] world;
    private static World savedWorld;
    private static Position PlayerPosition;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT + 4, 0, 4);
        drawMain();
        dealWithMain();
    }

    public void saveWorld() {
        File f = new File("./output.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            savedWorld = new World(world);
            savedWorld.player = PlayerPosition;


            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(savedWorld);
            fs.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWorld() {
        File f = new File("./output.txt");
        try {
            FileInputStream fs = new FileInputStream(f);
            ObjectInputStream os = new ObjectInputStream(fs);
            savedWorld = (World) os.readObject();

            world = savedWorld.world;
            PlayerPosition = savedWorld.player;

            fs.close();
            os.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drawMain() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        String Hello = "Hello";
        String s = "S : Start";
        String l = "L : Load";
        String q = "Q : Quit";

        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, Hello);

        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight - 2, s);
        StdDraw.text(midWidth, midHeight - 4, l);
        StdDraw.text(midWidth, midHeight - 6, q);
        StdDraw.show();
    }

    public void dealWithMain() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 's') {
                gameStart(true);
            } else if (key == 'q') {
                //quit
                System.exit(1);
            } else if (key == 'l') {
                //load
                loadWorld();
                gameStart(false);
            }
        }
    }

    public void seedRequireBackground() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);
        String Seed = "Please Enter a seed number followed by a S";
        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, Seed);
        StdDraw.show();
    }

    public void drawBottomInfo(String s) {
        int midWidth = WIDTH / 2;

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, 1, s);
        StdDraw.show();
    }

    public int seedBack() {
        String seed = "";
        char key = 0;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            if (key == 's') {
                break;
            }
            seed += String.valueOf(key);
        }
        return Integer.parseInt(seed);
    }

    public void gameStart(Boolean restart) {
        if (restart) {
            seedRequireBackground();
            Seed = seedBack();
            RANDOM = new Random(Seed);
            TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
            world = finalWorldFrame;
            Maze.gridGenerator(finalWorldFrame);
            Room.fillRoomArray(finalWorldFrame);
            Maze.mazeGenerator(finalWorldFrame);
            Room.connectRoom(finalWorldFrame);
            Room.transRoom(finalWorldFrame);
            Room.deleteRound(finalWorldFrame);
            Room.deleteWall(finalWorldFrame);
            Room.wrapWithWall(finalWorldFrame);
            PlayerPosition = new Position(0, 0);
            createPlayer(PlayerPosition);
        }

        ter.renderFrame(world);

        int xPos = PlayerPosition.x;
        int yPos = PlayerPosition.y;
        System.out.println(xPos);
        System.out.println(yPos);

        int mxxPos;
        int myyPos;
        String s;

        while (true) {
            mxxPos = (int) StdDraw.mouseX();
            myyPos = (int) StdDraw.mouseY();
            if (myyPos < 4) {
                s = "This is out menu";
            } else {
                s = getTileName(mxxPos, myyPos - 4);
            }

            if (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(world);
                drawBottomInfo(s);
                continue;
            }
            char key = StdDraw.nextKeyTyped();

            if (key == 'w') {
                System.out.println(getTileName(xPos, yPos));
                if (world[xPos][yPos + 1].equals(Tileset.FLOOR)) {
                    world[xPos][yPos] = Tileset.FLOOR;
                    world[xPos][yPos + 1] = Tileset.PLAYER;
                    yPos++;
                }
            } else if (key == 's') {
                if (world[xPos][yPos - 1].equals(Tileset.FLOOR)) {
                    world[xPos][yPos] = Tileset.FLOOR;
                    world[xPos][yPos - 1] = Tileset.PLAYER;
                    yPos--;
                }
            } else if (key == 'a') {
                if (world[xPos - 1][yPos].equals(Tileset.FLOOR)) {
                    world[xPos][yPos] = Tileset.FLOOR;
                    world[xPos - 1][yPos] = Tileset.PLAYER;
                    xPos--;
                }
            } else if (key == 'd') {
                if (world[xPos + 1][yPos].equals(Tileset.FLOOR)) {
                    world[xPos][yPos] = Tileset.FLOOR;
                    world[xPos + 1][yPos] = Tileset.PLAYER;
                    xPos++;
                }
            } else if (key == ':') {
                while (true) {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    char keyq = StdDraw.nextKeyTyped();
                    if (keyq == 'q') {
                        saveWorld();
                        System.exit(0);
                    } else {
                        break;
                    }
                }
            }
            PlayerPosition = new Position(xPos, yPos);
            ter.renderFrame(world);
            drawBottomInfo(s);
        }

    }

    public String getTileName(int x, int y) {
        if (y >= HEIGHT) return "";
        if (world[x][y] == Tileset.FLOOR) {
            return "Floor";
        } else if (world[x][y] == Tileset.WALL) {
            return "Wall";
        } else if (world[x][y] == Tileset.PLAYER) {
            return "Player";
        } else if (world[x][y] == Tileset.NOTHING) {
            return "Nothing";
        }
        return "";
    }

    public Game() {

    }

    public void createPlayer(Position p) {
        int xPos;
        int yPos;
        while (true) {
            xPos = RANDOM.nextInt(WIDTH);
            yPos = RANDOM.nextInt(HEIGHT);
            if (world[xPos][yPos].equals(Tileset.FLOOR)) {
                world[xPos][yPos] = Tileset.PLAYER;
                p.x = xPos;
                p.y = yPos;
                break;
            }
        }


    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        String seed = "";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) <= '9' && input.charAt(i) >= '0') {
                seed = seed + input.charAt(i);
            }
        }
        if (Seed == Long.parseLong(seed)) {
            return world;
        }
        Seed = Long.parseLong(seed);
        RANDOM = new Random(Seed);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        world = finalWorldFrame;

        Maze.gridGenerator(finalWorldFrame);
        Room.fillRoomArray(finalWorldFrame);
        Maze.mazeGenerator(finalWorldFrame);
        Room.connectRoom(finalWorldFrame);
        Room.transRoom(finalWorldFrame);
        Room.deleteRound(finalWorldFrame);
        Room.deleteWall(finalWorldFrame);
        Room.wrapWithWall(finalWorldFrame);
        return finalWorldFrame;
    }
}
