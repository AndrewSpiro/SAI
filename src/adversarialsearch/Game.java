package adversarialsearch;

import java.util.Vector;

public class Game {
	State b;
	public Game() {
		b=new State();
		b.read("data/board.txt");
	}

	public void test() {
		
		System.out.println(minimax(b, b.turn, 11, 0));
		
		// while (!b.isLeaf()){
		// 	System.out.println(b.toString());
		// 	System.out.println("Legal moves for agent with turn:"+b.legalMoves());
		// 	b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
		// }
	}

	public State minimax(State s, int forAgent, int maxDepth, int depth) {
		// TODO: explain this method in the report
		if (depth == maxDepth || s.isLeaf()) {
			return s;
			// return some state
		}


		// TODO: copy the original state
		// TODO: get the current possible actions that the agent can do in this state.
		Vector<String> actions = s.legalMoves(forAgent);
		// TODO: pass one of the actions to the execute methods - this will be the branch of the tree that is evaluated
		State currentHighestState = null;
		for (String action : actions) {
			State copied = s.copy();
			copied.execute(action);
			System.out.println(s.toString());
			State leafState = minimax(copied, copied.turn, maxDepth, depth + 1);
			if (currentHighestState == null || leafState.value(forAgent) > currentHighestState.value(forAgent)) {
				currentHighestState = leafState;
			}
		}
		return currentHighestState;
	}
	
}
