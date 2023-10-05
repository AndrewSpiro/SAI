package adversarialsearch;

import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
//         b.read("data/test-board.txt");
    }

    public void test() {

        System.out.println(b);
        State nextState = alfabeta(b, b.turn, 20, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
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

    // public State minimax(State s, int forAgent, int maxDepth, int depth) {
    // EXPLANATION: this is the previous minimax where both agents go for their max score instead of the opponent aiming for the minimal score
    //  we reasoned that this would have the same effects as the minimax below here, but it performs a bit differently
    //     // System.out.println("Current State:");
    //     // System.out.println(s);
    //     // System.out.println("Is leaf?: " + s.isLeaf());
    //     // System.out.println("Is max depth?: " + (depth == maxDepth));

    //     // TODO: explain this method in the report
    //     if (depth == maxDepth || s.isLeaf()) {
    //         // System.out.println("Returning State");
    //         return s;
    //         // return some state
    //     }

    //     Vector<String> actions = s.legalMoves();

    //     State currentHighestState = null;
    //     for (String action : actions) {
    //         // copying the parent leaf and executing the action
    //         State copied = s.copy();
    //         copied.execute(action);

    //         // the state of the board after executing the action
    //         State leafState = minimax(copied, copied.turn, maxDepth, depth + 1);
    //         // TODO: maybe we want to compare moves.size() here as well to see if we can get
    //         // in a desired state with less moves
    //         if (currentHighestState == null || leafState.value(forAgent) > currentHighestState.value(forAgent)) {
    //             currentHighestState = leafState;
    //         }
    //     }

    //     return currentHighestState;
    // }

   public State minimax(State s, int forAgent, int maxDepth, int depth) {
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

               State leafState = minimax(copy, forAgent, maxDepth, depth + 1);
               if (returnState == null || leafState.value(forAgent) > returnState.value(forAgent)) {
                   returnState = leafState;
               }
           }
       } else {
           // we will look for the minimum value of any potential leaves
           for (String action : actions) {
               State copy = s.copy();
               copy.execute(action);

               State leafState = minimax(copy, forAgent, maxDepth, depth + 1);
               if (returnState == null || leafState.value(s.turn) < returnState.value(s.turn)) {
                   returnState = leafState;
               }
           }
       }

       return returnState;
   }
    

   // alfabeta(...... alfa = Integer.MAX_VALUE, )
   public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta) {
       if (depth == maxDepth || s.isLeaf()) {
           return s;
       }

       Vector<String> actions = s.legalMoves();
       State returnState = null;
    //    alfa = Integer.MAX_VALUE;
    //    beta = Integer.MIN_VALUE;
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
               if (beta <= alfa) break;
           }
       } else {
           // we will look for the minimum value of any potential leaves
           for (String action : actions) {
               State copy = s.copy();
               copy.execute(action);

               State leafState = alfabeta(copy, forAgent, maxDepth, depth + 1, alfa, beta);
               double leafValue = leafState.value(forAgent);
               if (returnState == null || leafValue < returnState.value(s.turn)) {
                   returnState = leafState;
               }
               beta = leafValue > beta ? beta : leafValue;
               if (beta <= alfa) break; 
            }
       }

       return returnState;
   }
    

}
