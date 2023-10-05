package adversarialsearch;

import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        // b.read("data/board.txt");
        b.read("data/test-board.txt");
    }

    public void test() {

        System.out.println(b);
        State nextState = minimax(b, b.turn, 11, 0);
        // TODO: change this back to the earlier version of this test:
        // System.out.println(minimax(b, b.turn, 11, 0);
        System.out.println("Moves: " + nextState.moves);
        System.out.println(nextState);

        // while (!b.isLeaf()){
        // System.out.println(b.toString());
        // System.out.println("Legal moves for agent with turn:"+b.legalMoves());
        // b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
        // }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        // System.out.println("Current State:");
        // System.out.println(s);
        // System.out.println("Is leaf?: " + s.isLeaf());
        // System.out.println("Is max depth?: " + (depth == maxDepth));

        // TODO: explain this method in the report
        if (depth == maxDepth || s.isLeaf()) {
            // System.out.println("Returning State");
            return s;
            // return some state
        }

        Vector<String> actions = s.legalMoves();

        State currentHighestState = null;
        for (String action : actions) {
            // copying the parent leaf and executing the action
            State copied = s.copy();
            copied.execute(action);

            // the state of the board after executing the action
            State leafState = minimax(copied, copied.turn, maxDepth, depth + 1);
            if (currentHighestState == null || leafState.value(forAgent) > currentHighestState.value(forAgent)) {
                currentHighestState = leafState;
            }
        }
        
        return currentHighestState;
    }

}
