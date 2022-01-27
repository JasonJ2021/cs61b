package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Bag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Maze {
    private static ArrayList<Position> frontier = new ArrayList<>();

    public static void gridGenerator(TETile[][] world) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public static void mazeGenerator(TETile[][] world) {
        mark(1, 1, world, frontier);
        while (!frontier.isEmpty()) {
            Position p = frontier.remove(Game.RANDOM.nextInt(frontier.size()));
            LinkedList<Position> neighbors = neighbors(p.x, p.y, world);
            Position randomIn = neighbors.get(Game.RANDOM.nextInt(neighbors.size()));
            makePassage(p, randomIn, world);
            mark(p.x, p.y, world, frontier);
        }
    }

    private static void makePassage(Position front, Position in, TETile[][] world) {
        if (in.x == front.x - 2) {
            world[front.x - 1][front.y] = Tileset.FLOOR;
        } else if (in.x == front.x + 2) {
            world[front.x + 1][front.y] = Tileset.FLOOR;
        } else if (in.y == front.y + 2) {
            world[front.x][front.y + 1] = Tileset.FLOOR;
        } else if (in.y == front.y - 2) {
            world[front.x][front.y - 1] = Tileset.FLOOR;
        }
    }

    /*mark a grid is in*/
    public static void mark(int x, int y, TETile[][] grid, ArrayList<Position> frontier) {
        grid[x][y] = Tileset.FLOOR;
        add_frontier(x - 2, y, grid, frontier);
        add_frontier(x + 2, y, grid, frontier);
        add_frontier(x, y - 2, grid, frontier);
        add_frontier(x, y + 2, grid, frontier);

    }

    public static void add_frontier(int x, int y, TETile[][] grid, ArrayList<Position> frontier) {
        if (x > 0 && x < Game.WIDTH && y > 0 && y < Game.HEIGHT && grid[x][y] == Tileset.NOTHING) {
            frontier.add(new Position(x, y));
        }
    }

    /*returns all the in neighbors of a given frontier cell
     * */
    public static LinkedList<Position> neighbors(int x, int y, TETile[][] grid) {
        LinkedList<Position> list = new LinkedList<>();
        if (x > 1 && grid[x - 2][y] == Tileset.FLOOR) {
            list.add(new Position(x - 2, y));
        } else if (x + 2 < Game.WIDTH && grid[x + 2][y] == Tileset.FLOOR) {
            list.add(new Position(x + 2, y));
        } else if (y > 1 && grid[x][y - 2] == Tileset.FLOOR) {
            list.add(new Position(x, y - 2));
        } else if (y + 2 < Game.HEIGHT && grid[x][y + 2] == Tileset.FLOOR) {
            list.add(new Position(x, y + 2));
        }
        return list;
    }
}
