package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    public Position position;

    enum direction {
        LEFT, RIGHT, UP, DOWN;
    }

    public int width;
    public int height;
    public static ArrayList<Room> rooms = new ArrayList<>();
    private static final int WIDTH = 6;
    private static final int HEIGHT = 16;
    private static TETile[][] roomPosition = new TETile[Game.WIDTH][Game.HEIGHT];

    public Room(int width, int height, Position position) {
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public static int getRandomWidth() {
        return Game.RANDOM.nextInt(WIDTH) + 2;
    }

    public static int getRandomHeight() {
        return Game.RANDOM.nextInt(HEIGHT) + 2;
    }

    public static Room generateRoom() {
        return new Room(getRandomWidth(), getRandomHeight(), Position.randomPosition());
    }

    /* if a and b intersects , return True;
     fill RoomArray
     and draw Tile in world

     */
    public static void fillRoomArray(TETile[][] world) {
        int times = Game.RANDOM.nextInt(20) + 3;
        for (int i = 0; i < times; i++) {
            Room room = generateRoom();
            if (roomOutside(room)) {
                continue;
            }
            if (roomCross(room, world)) {
                continue;
            }
            rooms.add(room);
            drawRoom(world, room);
        }
    }

    public static boolean roomOutside(Room room) {
        if (room.position.x + room.width + 2 >= Game.WIDTH) {
            return true;
        }
        if (room.position.y + room.height + 2 >= Game.HEIGHT) {
            return true;
        }
        return false;
    }

    /*a wall wrap a room*/
    public static boolean roomCross(Room a, TETile[][] world) {
        int startX = a.position.x;
        int startY = a.position.y;
        int aWidth = a.width;
        int aHeight = a.height;

        for (int i = 0; i <= aWidth + 1; i++) {
            for (int j = 0; j <= aHeight + 1; j++) {
                if (roomPosition[startX + i][startY + j] == Tileset.TREE || roomPosition[startX + i][startY + j] == Tileset.WALL) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void drawRoom(TETile[][] world, Room room) {
        int xStart = room.position.x;
        int yStart = room.position.y;
        for (int i = 0; i <= room.width + 1; i++) {
            for (int j = 0; j <= room.height + 1; j++) {
                if (i == 0 || i == room.width + 1 || j == 0 || j == room.height + 1) {
                    world[xStart + i][yStart + j] = Tileset.WALL;
                    roomPosition[xStart + i][yStart + j] = Tileset.WALL;
                    continue;
                }
                world[xStart + i][yStart + j] = Tileset.TREE;
                roomPosition[xStart + i][yStart + j] = Tileset.TREE;
            }
        }
    }

    /*from (x , y )
     * judge if there is a way out ?
     * */
    public static void connectRoom(TETile[][] world) {
        for (Room r : rooms) {
            while (true) {
                int direc = Game.RANDOM.nextInt(4);
                boolean flag = false;
                switch (direc) {

                    case 0: {
                        //left
                        int x = r.position.x + 1;
                        int y = r.position.y + 1;
                        y += Game.RANDOM.nextInt(r.height);
                        if (aWayOut(direction.LEFT, world, x, y)) {
                            digOut(direction.LEFT, world, x, y);
                            flag = true;
                        }
                        break;
                    }
                    case 1: {
                        //right
                        int x = r.position.x + r.width;
                        int y = r.position.y + 1;
                        y += Game.RANDOM.nextInt(r.height);
                        if (aWayOut(direction.RIGHT, world, x, y)) {
                            digOut(direction.RIGHT, world, x, y);
                            flag = true;
                        }
                        break;
                    }
                    case 2: {
                        //up
                        int x = r.position.x + 1;
                        int y = r.position.y + r.height;
                        x += Game.RANDOM.nextInt(r.width);
                        if (aWayOut(direction.UP, world, x, y)) {
                            digOut(direction.UP, world, x, y);
                            flag = true;
                        }
                        break;
                    }
                    case 3: {
                        //down
                        int x = r.position.x + 1;
                        int y = r.position.y + 1;
                        x += Game.RANDOM.nextInt(r.width);
                        if (aWayOut(direction.DOWN, world, x, y)) {
                            digOut(direction.DOWN, world, x, y);
                            flag = true;
                        }
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    public static boolean aWayOut(direction dir, TETile[][] world, int x, int y) {
        if (dir == direction.LEFT) {
            x--;
            while (x > 0) {
                if (world[x][y] == Tileset.FLOOR) {
                    return true;
                } else if (world[x][y] == Tileset.WALL) {
                    x--;
                } else {
                    return false;
                }
            }
        } else if (dir == direction.RIGHT) {
            x++;
            while (x < Game.WIDTH) {
                if (world[x][y] == Tileset.FLOOR) {
                    return true;
                } else if (world[x][y] == Tileset.WALL) {
                    x++;
                } else {
                    return false;
                }
            }
        } else if (dir == direction.UP) {
            y++;
            while (y < Game.HEIGHT) {
                if (world[x][y] == Tileset.FLOOR) {
                    return true;
                } else if (world[x][y] == Tileset.WALL) {
                    y++;
                } else {
                    return false;
                }
            }
        } else if (dir == direction.DOWN) {
            y--;
            while (y > 0) {
                if (world[x][y] == Tileset.FLOOR) {
                    return true;
                } else if (world[x][y] == Tileset.WALL) {
                    y--;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void digOut(direction dir, TETile[][] world, int x, int y) {
        while (true) {
            if (dir == direction.LEFT) {
                x--;
                if (world[x][y] == Tileset.FLOOR) {
                    break;
                }
                world[x][y] = Tileset.FLOOR;
            }
            if (dir == direction.RIGHT) {
                x++;
                if (world[x][y] == Tileset.FLOOR) {
                    break;
                }
                world[x][y] = Tileset.FLOOR;
            }
            if (dir == direction.UP) {
                y++;
                if (world[x][y] == Tileset.FLOOR) {
                    break;
                }
                world[x][y] = Tileset.FLOOR;
            }
            if (dir == direction.DOWN) {
                y--;
                if (world[x][y] == Tileset.FLOOR) {
                    break;
                }
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    public static void transRoom(TETile[][] world) {
        for (Room r : rooms) {
            int startX = r.position.x + 1;
            int startY = r.position.y + 1;
            for (int i = 0; i < r.width; i++) {
                for (int j = 0; j < r.height; j++) {
                    world[startX + i][startY + j] = Tileset.FLOOR;
                }
            }
        }
    }

    public static void deleteRound(TETile[][] world) {
        while (true) {
            boolean flag = true;
            for (int i = 0; i < Game.WIDTH; i++) {
                for (int j = 0; j < Game.HEIGHT; j++) {
                    if (world[i][j] == Tileset.FLOOR) {
                        int cnt = 0;
                        if (world[i - 1][j] == Tileset.WALL) {
                            cnt++;
                        }
                        if (world[i + 1][j] == Tileset.WALL) {
                            cnt++;
                        }
                        if (world[i][j + 1] == Tileset.WALL) {
                            cnt++;
                        }
                        if (world[i][j - 1] == Tileset.WALL) {
                            cnt++;
                        }
                        if (cnt >= 3) {
                            flag = false;
                            world[i][j] = Tileset.WALL;
                        }
                    }
                }
            }
            if (flag == true) {
                break;
            }
        }
    }

    public static void deleteWall(TETile[][] world) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (world[i][j] == Tileset.WALL) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public static void wrapWithWall(TETile[][] world) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    if (world[i - 1][j] == Tileset.NOTHING) {
                        world[i - 1][j] = Tileset.WALL;
                    }
                    if (world[i + 1][j] == Tileset.NOTHING) {
                        world[i + 1][j] = Tileset.WALL;
                    }
                    if (world[i][j + 1] == Tileset.NOTHING) {
                        world[i][j + 1] = Tileset.WALL;
                    }
                    if (world[i][j - 1] == Tileset.NOTHING) {
                        world[i][j - 1] = Tileset.WALL;
                    }
                    if (world[i-1][j - 1] == Tileset.NOTHING) {
                        world[i-1][j - 1] = Tileset.WALL;
                    }
                    if (world[i-1][j + 1] == Tileset.NOTHING) {
                        world[i-1][j + 1] = Tileset.WALL;
                    }
                    if (world[i+1][j - 1] == Tileset.NOTHING) {
                        world[i+1][j - 1] = Tileset.WALL;
                    }
                    if (world[i+1][j + 1] == Tileset.NOTHING) {
                        world[i+1][j + 1] = Tileset.WALL;
                    }
                }
            }
        }
    }
}
