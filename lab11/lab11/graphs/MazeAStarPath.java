package lab11.graphs;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }
    private class Node{
        private int id ;
        private int weight;
        public Node(int id , int weight){
            this.id = id;
            this.weight = weight;
        }
    }
    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return node.weight - t1.weight;
            }
        });
        marked[s] = true;
        queue.add(new Node(s,0));
        while(!queue.isEmpty() && !targetFound){
            Node node = queue.poll();
            int q = node.id;
            for(int w : maze.adj(q)){
                if(!marked[w]){
                    queue.add(new Node(w,h(w)));
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
        astar(s);
    }

}

