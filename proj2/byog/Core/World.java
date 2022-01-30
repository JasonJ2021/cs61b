package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.Random;

public class World implements Serializable {
    public TETile[][] world;
    public Position player;

    public World(TETile[][] world){
        this.world = world;
    }
}
