package adversarialsearch;

import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {

    	System.out.println(b);
		State nextState = minimax(b, b.turn, 11, 0);
    	for (int i = 0; i < 9; i++) {
			System.out.println(nextState);
			nextState = minimax(nextState, nextState.turn, 11, 0);
    	}

        // while (!b.isLeaf()){
        // System.out.println(b.toString());
        // System.out.println("Legal moves for agent with turn:"+b.legalMoves());
        // b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
        // }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        // TODO: explain this method in the report
        if (depth == maxDepth || s.isLeaf()) {
            return s;
            // return some state
        }

        Vector<String> actions = s.legalMoves(forAgent);

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
