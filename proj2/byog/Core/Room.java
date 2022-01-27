package byog.Core;
import byog.SaveDemo.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    public Position position;
    public int width;
    public int height;
    public static ArrayList<Room> rooms = new ArrayList<>();
    private static final int WIDTH = 6;
    private static final int HEIGHT = 16;

    public Room(int width , int height , Position position ){
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public static int getRandomWidth(){
        return Game.RANDOM.nextInt(WIDTH)+2;
    }
    public static int getRandomHeight(){
        return Game.RANDOM.nextInt(HEIGHT)+2;
    }
    public static Room generateRoom(){
        return new Room(getRandomWidth() , getRandomHeight(), Position.randomPosition());
    }
    /* if a and b intersects , return True;
     fill RoomArray
     and draw Tile in world
     
     */
    public static void  fillRoomArray(TETile[][] world){
        int times = Game.RANDOM.nextInt(20) + 3;
        for(int i = 0 ; i < times ; i++ ){
            Room room = generateRoom();
            if(roomOutside(room)){
                continue;
            }
            if(roomCross(room , world)){
                continue;
            }
            rooms.add(room);
            drawRoom(world,room);
        }
    }
    public static boolean roomOutside(Room room){
        if(room.position.x + room.width >= Game.WIDTH){
            return true;
        }
        if(room.position.y + room.height >= Game.HEIGHT){
            return true;
        }
        return false;
    }
    public static boolean roomCross(Room a , TETile[][] world){
        int startX = a.position.x;
        int startY = a.position.y;
        int aWidth = a.width;
        int aHeight = a.height;
        if(startX + aWidth + 1 >= Game.WIDTH){
            aWidth++;
        }
        if(startX > 0 ){
            startX--;
            aWidth++ ;
        }
        if(startY + aHeight + 1 >= Game.HEIGHT){
            aHeight++;
        }
        if(startY > 0 ){
            startY--;
            aHeight++ ;
        }
        for(int i = 0 ; i < aWidth; i++) {
            for(int j = 0 ; j < aHeight ; j++){
                if(world[startX+i][startY + j] == Tileset.TREE){
                    return true;
                }
            }
        }
        return false;
    }


    public static void drawRoom(TETile[][] world , Room room){
        int xStart = room.position.x;
        int yStart = room.position.y;
        for(int i = 0 ; i < room.width;i++){
            for(int j = 0 ; j < room.height;j++){
                world[xStart+i][yStart+j] = Tileset.TREE;
            }
        }
    }

}
