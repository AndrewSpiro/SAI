package adversarialsearch;

import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {

        System.out.println(b + "\n\n");
		State resultState = alfabeta(b, b.turn, 9, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		// State resultState = minimax(b, b.turn, 14, 0);
        System.out.println(resultState);

        // testing loop for 100 different runs

        //  System.out.println("Initial state: \n" + b + "\n");
        //  for (int i = 0; i < 100; i++) {
        //    // creates a random depth between 0 and 15 (15 excluded)
        //    int randomDepth = (int) (Math.random() * 100) % 15;
        //    int randomForAgent = (int) (Math.round(Math.random()));
        //    boolean runMinimax = Math.random() < 0.5;
        //    State result;
        //    if (runMinimax) {
        //      System.out.println("Running minimax algorithm with parameters: ");
        //      System.out.println("forAgent: " + randomForAgent + "\nmaxDepth: " + randomDepth);
        //      result = minimax(b, randomForAgent, randomDepth, 0);
        //      System.out.println("Succesfully completed minimax algorithm!\n\n");
        //    } else {
        //      System.out.println("Running alfabeta algorithm with parameters: ");
        //      System.out.println("forAgent: " + randomForAgent + "\nmaxDepth: " + randomDepth);
        //      result = alfabeta(b, randomForAgent, randomDepth, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //      System.out.println("Succesfully completed alfabeta algorithm!\n\n");
        //    }
        //    System.out.println(result + "\n\n");
        //  }

        // while (!b.isLeaf()){
        // System.out.println(b.toString());
        // System.out.println("Legal moves for agent with turn:"+b.legalMoves());
        // b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
        // }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        if (depth == maxDepth || s.isLeaf()) {
            return s;
        }

        Vector<String> actions = s.legalMoves();
        State returnState = null;
        for (String action : actions) {
            State copy = s.copy();
            copy.execute(action);
            State leafState = minimax(copy, forAgent, maxDepth, depth + 1);

            if (s.turn == forAgent) {
                if (returnState == null || leafState.value(forAgent) > returnState.value(forAgent)) {
                    returnState = leafState;
                }
            } else {
                if (returnState == null || leafState.value(forAgent) < returnState.value(forAgent)) {
                    returnState = leafState;
                }
            }
        }
        return returnState;
    }

    public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta) {
        if (depth == maxDepth || s.isLeaf()) {
            return s;
        }

        Vector<String> actions = s.legalMoves();
        State returnState = null;
        for (String action : actions) {
            State copy = s.copy();
            copy.execute(action);

            State leafState = alfabeta(copy, forAgent, maxDepth, depth + 1, alfa, beta);
            double leafValue = leafState.value(forAgent);
            if (s.turn == forAgent) {
                if (returnState == null || leafValue > returnState.value(forAgent)) {
                    returnState = leafState;
                }
                alfa = leafValue < alfa ? alfa : leafValue;
            } else {
                if (returnState == null || leafValue < returnState.value(forAgent)) {
                    returnState = leafState;
                }
                beta = leafValue > beta ? beta : leafValue;
            }
            if (beta <= alfa)
                break;
        }
        return returnState;
    }

}
