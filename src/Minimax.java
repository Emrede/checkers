import java.util.ArrayList;

import static java.util.Collections.shuffle;

/**
 * Created by Emre on 11/20/2015.
 */
public class Minimax {
    int score;
    Move move;

    public Minimax(int score, Move move) {
        this.score = score;
        this.move = move;
    }

    public static Minimax minimax(int depth, GameState gameState, int alpha, int beta) {
        Minimax v = new Minimax(0, null); //Initial state
        Minimax minimaxResult;
        GameState.GameResult gameResult = GameState.getResult(gameState);

        if (gameResult == GameState.GameResult.P1Wins //Check if game is finished or maximum depth is reached
                || gameResult == GameState.GameResult.P2Wins
                || depth == 0) {
            Minimax m = new Minimax(GameState.getScore(gameState), null); //Leaf
            return m;
        }

        ArrayList<Move> successorEvaluations = Game.getAllAllowedMoves(gameState);
        shuffle(successorEvaluations); //Randomise nodes.
        //Use getAllAllowedMoves function for minimax to get successor evaluations.
        //Use shuffle function to randomise the move list for nodes which gives same score.

        if (gameState.currentPlayer == Token.TokenPlayer.P2) {//#MAX'S TURN#
            v.score = Integer.MIN_VALUE;
            for (Move move : successorEvaluations) {
                GameState minimaxGameState = new GameState(gameState); //Clone the board according to last minimax state.
                ArrayList<Move> tmpMovesList = Game.getAllAllowedMoves(minimaxGameState);
                Move moveFromTmpList = Move.matchMoveBetweenStates(tmpMovesList, move);
                Move.move(minimaxGameState, moveFromTmpList);
                minimaxResult = minimax(depth - 1, minimaxGameState, alpha, beta); //MinimaxResult = m;
                if (minimaxResult.score > v.score) {
                    v.score = minimaxResult.score;
                    v.move = move;
                }//#ALPHA-BETA PRUNING
                if (minimaxResult.score > alpha) {//Compare the possible score with alpha
                    alpha = minimaxResult.score; //Replace if it's higher than alpha
                }
                if (beta <= alpha)
                    break; //If beta <= alpha, don't go for the rest!
            }
            System.out.println("Alpha: " + alpha + " Beta: " + beta + " Score: " + v.score);
            return v;

        } else {//#MIN'S TURN#
            v.score = Integer.MAX_VALUE;
            for (Move move : successorEvaluations) {
                GameState minimaxGameState = new GameState(gameState); //Clone the board according to last minimax state.
                ArrayList<Move> tmpMovesList = Game.getAllAllowedMoves(minimaxGameState);
                Move moveFromTmpList = Move.matchMoveBetweenStates(tmpMovesList, move);
                Move.move(minimaxGameState, moveFromTmpList);
                minimaxResult = minimax(depth - 1, minimaxGameState, alpha, beta);
                if (minimaxResult.score < v.score) {
                    v.score = minimaxResult.score;
                    v.move = move;
                }//#ALPHA-BETA PRUNING
                if (minimaxResult.score < beta) {//Compare the possible score with beta
                    beta = minimaxResult.score;//Replace if it's lower than beta
                }
                if (beta <= alpha)
                    break; //If beta <= alpha, don't go for the rest!
            }
            System.out.println("Alpha: " + alpha + " Beta: " + beta + " Score: " + v.score);
            return v;
        }
    }
}