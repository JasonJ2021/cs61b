package lab11.graphs;

import java.util.Stack;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] cycleTo;
    private boolean cycleFind = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        cycleTo = new int[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            cycleTo[i] = Integer.MAX_VALUE;
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        int i ;
        for(i =  0 ; i < maze.V() ; i++){
            if(maze.adj(i).iterator().hasNext()){
                break;
            }
        }
        dfs(i , -1 );
    }

    // Helper methods go here
    private void dfs(int v , int parent) {
        marked[v] = true;
        announce();
        if (cycleFind == true) return;
        for (int w : maze.adj(v)) {
            if (parent != w && marked[w]) {
                cycleFind = true;
                cycleTo[w] = v;
                colorPuple(w);
                break;
            } else if(!marked[w]){
                cycleTo[w] = v;
                dfs(w , v);
                if (cycleFind) return;
            }
        }
    }

    private void colorPuple(int v) {
        int w = v;
        edgeTo[w] = cycleTo[w];
        v = cycleTo[v];
        while (v != w) {
            edgeTo[v] = cycleTo[v];
            v = edgeTo[v];
        }
        announce();
    }
}

