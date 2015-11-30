import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Emre on 11/29/2015.
 */
public class Minimax {
    int score;
    Move move;

    public Minimax(int score, Move move) {
        this.score = score;
        this.move = move;
    }


    public static Minimax minimax(int depth, GameState minimaxGameState, int alpha, int beta) {
        //int score;
        Minimax score = new Minimax(0, null);
        GameState.GameResult gameResult = GameState.getResult(minimaxGameState);
        if (gameResult == GameState.GameResult.P1Wins //Check if game is finished or maximum depth is reached
                || gameResult == GameState.GameResult.P2Wins
                || depth == 0) {
            return new Minimax(GameState.getScore(minimaxGameState), null);
        }
        ArrayList<Move> children = Game.getAllAllowedMoves(minimaxGameState);

        if (minimaxGameState.currentPlayer == Token.TokenPlayer.P1) {//Max's turn
            for (Move move : children) {
                GameState tmpGameState = new GameState(minimaxGameState);
                Move.move(tmpGameState, move);
                score = minimax(depth - 1, tmpGameState, alpha, beta);
                if (score.score > alpha) {
                    alpha = score.score;
                    score.move = move;
                }
                if (alpha >= beta) break;
            }
            return score;

        } else {//Min's turn
            for (Move move : children) {
                GameState tmpGameState = new GameState(minimaxGameState);
                Move.move(tmpGameState, move);
                score = minimax(depth - 1, tmpGameState, alpha, beta);
                if (score.score < beta) {
                    beta = score.score;
                    score.move = move;
                }
                if (alpha >= beta) break;
            }
            return score;
        }
    }
}
