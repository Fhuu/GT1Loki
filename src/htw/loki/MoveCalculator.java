package htw.loki;

public class MoveCalculator {

    private AIAlgorithm algorithm;
    private GameBoard gameboard;
    private final int LARGEST_NUMBER = 10000;
    private final int SMALLEST_NUMBER = -10000;
    private int calculationDepth;

    public MoveCalculator(AIAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.gameboard = GameBoard.getInstance();
        this.calculationDepth = 2;
    }

    public int calculate() {
        if (this.algorithm.equals(AIAlgorithm.MINIMAX))
            return minimax(true, this.calculationDepth, 1);
        return -1;
    }

    public void setCalculationDepth(int newDepth) {
        this.calculationDepth = newDepth;
    }

    public int minimax(boolean isMaximizing, int depth, int playerNumber) {
        if (depth == 0) {
            // TODO: Implement the evaluation function to assign a score to the current game state
            return evaluateGameBoard();
        }

        if (isMaximizing) {
            int bestScore = SMALLEST_NUMBER;
            for (int position : gameboard.getNeighbouringPosition(playerNumber)) {
                // TODO: Make a move on the game board and update player's position

                int score = minimax(false, depth - 1, getNextPlayer(playerNumber));
                bestScore = Math.max(bestScore, score);

                // TODO: Undo the move on the game board and restore player's position
            }
            return bestScore;
        } else {
            int bestScore = LARGEST_NUMBER;
            for (int position : gameboard.getNeighbouringPosition(playerNumber)) {
                // TODO: Make a move on the game board and update player's position

                int score = minimax(true, depth - 1, getNextPlayer(playerNumber));
                bestScore = Math.min(bestScore, score);

                // TODO: Undo the move on the game board and restore player's position
            }
            return bestScore;
        }
    }

    private int getNextPlayer(int currentPlayer) {
        // TODO: Implement the logic to determine the next player based on the current player number
        return 0;
    }

    private int evaluateGameBoard() {
        // TODO: Implement the evaluation function to assign a score to the current game state
        return 0;
    }
}
