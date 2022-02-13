package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        queue.add(s);
        while(!queue.isEmpty() && !targetFound){
            int q = queue.poll();
            for(int w : maze.adj(q)){
                if(!marked[w]){
                    queue.add(w);
                    distTo[w] = distTo[q] + 1;
                    edgeTo[w] = q;
                    marked[w] = true;
                    announce();
                    if(w == t){
                        targetFound = true;
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

