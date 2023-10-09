package adversarialsearch;

import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
        // b.read("data/test-board.txt");
    }

    public void test() {

        System.out.println(b);
//        State resultState = alfabeta(b, b.turn, 11, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
         State resultState = minimax(b, b.turn, 13, 0);
        System.out.println("Moves: " + resultState.moves);
        System.out.println(resultState);

        // while (!b.isLeaf()){
        // System.out.println(b.toString());
        // System.out.println("Legal moves for agent with turn:"+b.legalMoves());
        // b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
        // }
    }

    // public State minimax(State s, int forAgent, int maxDepth, int depth) {
    //     if (depth == maxDepth || s.isLeaf()) {
    //         return s;
    //     }

    //     Vector<String> actions = s.legalMoves();
    //     State returnState = null;
    //     if (s.turn == forAgent) {
    //         // we will look for the max value of any potential leaves
    //         for (String action : actions) {
    //             State copy = s.copy();
    //             copy.execute(action);

    //             State leafState = minimax(copy, forAgent, maxDepth, depth + 1);
    //             if (returnState == null || leafState.value(forAgent) > returnState.value(forAgent)) {
    //                 returnState = leafState;
    //             }
    //         }
    //     } else {
    //         // we will look for the minimum value of any potential leaves
    //         for (String action : actions) {
    //             State copy = s.copy();
    //             copy.execute(action);

    //             State leafState = minimax(copy, forAgent, maxDepth, depth + 1);
    //             if (returnState == null || leafState.value(forAgent) < returnState.value(forAgent)) {
    //                 returnState = leafState;
    //             }
    //         }
    //     }

    //     return returnState;
    // }

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

            if (s.turn == forAgent){
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
        if (s.turn == forAgent) {
            // we will look for the max value of any potential leaves
            for (String action : actions) {
                State copy = s.copy();
                copy.execute(action);

                State leafState = alfabeta(copy, forAgent, maxDepth, depth + 1, alfa, beta);
                double leafValue = leafState.value(forAgent);
                if (returnState == null || leafValue > returnState.value(forAgent)) {
                    returnState = leafState;
                }
                alfa = leafValue < alfa ? alfa : leafValue;
                if (beta <= alfa)
                    break;
            }
        } else {
            // we will look for the minimum value of any potential leaves
            for (String action : actions) {
                State copy = s.copy();
                copy.execute(action);

                State leafState = alfabeta(copy, forAgent, maxDepth, depth + 1, alfa, beta);
                double leafValue = leafState.value(forAgent);
                if (returnState == null || leafValue < returnState.value(forAgent)) { // Changed from returnState.value(s.turn) to returnState.value(forAgent)
                    returnState = leafState;
                }
                beta = leafValue > beta ? beta : leafValue;
                if (beta <= alfa)
                    break;
            }
        }

        return returnState;
    }

}
