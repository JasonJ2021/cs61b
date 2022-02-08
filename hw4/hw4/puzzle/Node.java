package hw4.puzzle;

public class Node {
    public WorldState worldState;
    public int moves;
    public Node prev;
    private int estimatedDistanceToGoal;
    public Node(WorldState worldState, int moves, Node prev) {
        this.worldState = worldState;
        this.moves = moves;
        this.prev = prev;
        this.estimatedDistanceToGoal = worldState.estimatedDistanceToGoal();
    }

    public int getPriority() {
        return moves + estimatedDistanceToGoal;
    }

    public boolean isOurGoal() {
        return worldState.isGoal();
    }
}
