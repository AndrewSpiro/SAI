package adversarialsearch;

import java.util.*;
import java.io.RandomAccessFile;
import java.io.IOException;

public class State {

    char[][] board; // the board as a 2D character array
    int[] agentX; // the x−coordinates o f the agents [agentA_x_coordinate, agentB_x_coordinate]
    int[] agentY; // the y−coordinates o f the agents
    int[] score; // the amount o f food eaten by each agent
    int turn; // who’s turn it is , agent 0 or agent 1
    int food; // the total amount of food still available

    Vector<String> moves;; // moves performed to get to the current state

    public State() {
        this.score = new int[] { 0, 0 };
        this.turn = 0;
        this.food = 0;
        this.moves = new Vector<String>();
    }

    public State(char[][] board, int[] agentX, int[] agentY, int[] score, int turn, int food, Vector<String> moves) {
        this.board = board;
        this.agentX = agentX;
        this.agentY = agentY;
        this.score = score;
        this.turn = turn;
        this.food = food;
        this.moves = moves;
    }

    public void read(String file) {
        int width;
        int height;

        int[] coordsA = new int[2];
        int[] coordsB = new int[2];
        try {
            RandomAccessFile board = new RandomAccessFile(file, "r");

            // parse dimensions on the first line
            String firstLine = board.readLine();
            String[] dimensions = firstLine.split(" ");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);

            // parse the board
            this.board = new char[height][width];

            for (int y = 0; y < height; y++) {
                String line = board.readLine();
                if (line == null)
                    break; // no more lines to read in the file
                for (int x = 0; x < width; x++) {
                    char currentChar = line.charAt(x);
                    if (currentChar == 'A') {
                        coordsA = new int[] { x, y };
                        currentChar = ' ';
                    }
                    if (currentChar == 'B') {
                        coordsB = new int[] { x, y };
                        currentChar = ' ';
                    }
                    if (currentChar == '*') {
                        this.food++;
                    }
                    // put currentChar in this.board
                    this.board[y][x] = currentChar;
                }

            }

            board.close();

            this.agentX = new int[] { coordsA[0], coordsB[0] };
            this.agentY = new int[] { coordsA[1], coordsB[1] };

        } catch (IOException e) {
            // Handle any potential IOException, such as file not found or permission issues
            System.err.println("Error loading data: " + e.getMessage());
        }

    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                boolean agentAIsStandingHere = (this.agentY[0] == i && this.agentX[0] == j);
                boolean agentBIsStandingHere = (this.agentY[1] == i && this.agentX[1] == j);
                if (agentAIsStandingHere) {
                    result += 'A';
                } else if (agentBIsStandingHere) {
                    result += 'B';
                } else {
                    result += this.board[i][j];
                }
            }
            result += '\n';
        }

        result += "\n" + "agentX: " + Arrays.toString(this.agentX);
        result += "\n" + "agentY: " + Arrays.toString(this.agentY);
        result += "\n" + "food: " + this.food;
        result += "\n" + "score: " + Arrays.toString(this.score);
        result += "\n" + "turn: " + this.turn;

        return result;
    }

    public State copy() {
        // since the board is a 2d array, we have to make a deepcopy of this array and
        // can't just copy the references
        char[][] copiedBoard = new char[this.board.length][this.board[0].length];
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
                copiedBoard[y][x] = this.board[y][x];
            }
        }

        return new State(
                copiedBoard,
                this.agentX.clone(),
                this.agentY.clone(),
                this.score.clone(),
                this.turn,
                this.food,
                new Vector<String>(this.moves));
    }

    public Vector<String> legalMoves(int agent) {
        // TODO: explain this method in report
        int x = this.agentX[agent];
        int y = this.agentY[agent];

        if (y == 0 || x == 0) {
            System.out.println(this.toString());
        }

        Vector<String> validMoves = new Vector<String>();

        // check if the coordinates next to, above, below are accessible by the agent
        // (not walls)
        if (this.board[y][x - 1] == ' ' || this.board[y][x - 1] == '*') {
            validMoves.add("left");
        }
        if (this.board[y][x + 1] == ' ' || this.board[y][x + 1] == '*') {
            validMoves.add("right");
        }
        if (this.board[y - 1][x] == ' ' || this.board[y - 1][x] == '*') {
            validMoves.add("up");
        }
        if (this.board[y + 1][x] == ' ' || this.board[y + 1][x] == '*') {
            validMoves.add("down");
        }
        if (this.board[y][x] == ' ') {
            validMoves.add("block");
        }
        if (this.board[y][x] == '*') {
            validMoves.add("eat");
        }
        return validMoves;
    }

    public Vector<String> legalMoves() {
        return this.legalMoves(this.turn);
    }

    public void execute(String action) {
        // TODO: add an explanation of this function to the report
        int x = this.agentX[this.turn];
        int y = this.agentY[this.turn];
        switch (action) {
            case "up":
                this.agentY[this.turn] -= 1;
                break;
            case "down":
                this.agentY[this.turn] += 1;
                break;
            case "left":
                this.agentX[this.turn] -= 1;
                break;
            case "right":
                this.agentX[this.turn] += 1;
                break;
            case "eat":
                this.board[y][x] = ' ';
                this.score[this.turn]++;
                this.food--;
                break;
            case "block":
                this.board[y][x] = '#';
                break;
            default:
                System.out.println("Invalid action: " + action);
                break;

        }
        String player = this.turn == 0 ? "A" : "B";
        this.moves.add(player + " performs " + action);
        this.turn = this.turn == 0 ? 1 : 0;
    }

    public boolean isLeaf() {
        // TODO: mention this method in the report
        return this.food == 0 || this.legalMoves().isEmpty();
    }

    public double value(int agent) {
        // TODO: mention this method in the report
        int currentAgentScore;
        int opponentScore;

        currentAgentScore = this.score[agent];
        if (agent == 0) {
            opponentScore = this.score[1];
        } else {
            opponentScore = this.score[0];
        }

        // if we want to use the amount of food eaten as a heuristic:
        // return currentAgentScore - opponentScore;

        if (currentAgentScore > opponentScore) {
            return 1;
        } else if (currentAgentScore == opponentScore) {
            return 0;
        }
        return -1;
    }

}
