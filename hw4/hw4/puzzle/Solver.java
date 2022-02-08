package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;


public class Solver {
    MinPQ<Node> minPQ;
    Node goal;

    public Solver(WorldState initial) {
        minPQ = new MinPQ<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return node.getPriority() - t1.getPriority();
            }
        });
        minPQ.insert(new Node(initial, 0, null));
        while (!minPQ.isEmpty()) {
            Node x = minPQ.delMin();
            if (x.isOurGoal()) {
                goal = x;
                break;
            }
            for (WorldState worldState : x.worldState.neighbors()) {
                if(x.prev != null){
                    if (!worldState.equals(x.prev.worldState)) {
                        minPQ.insert(new Node(worldState, x.moves + 1, x));
                    }
                }else{
                    minPQ.insert(new Node(worldState, x.moves + 1, x));
                }
            }
        }
    }

    public int moves() {
        return goal.moves;
    }

    public Iterable<WorldState> solution() {
        Stack<WorldState> stack = new Stack<>();
        Node x = goal;
        while (x != null) {
            stack.push(x.worldState);
            x = x.prev;
        }
        return stack;
    }
}