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


    public static Minimax minimax(int depth, GameState actualGameState, int alpha, int beta) {
        //int score;
        Minimax score = new Minimax(0, null);
        Minimax minimaxResult;
        GameState.GameResult gameResult = GameState.getResult(actualGameState);
        if (gameResult == GameState.GameResult.P1Wins //Check if game is finished or maximum depth is reached
                || gameResult == GameState.GameResult.P2Wins
                || depth == 0) {
            return new Minimax(GameState.getScore(actualGameState), null);
        }


        ArrayList<Move> successorEvaluations = Game.getAllAllowedMoves(actualGameState);
        //use getAllAllowedMoves function for minimax to get successor evaluations.

        if (actualGameState.currentPlayer == Token.TokenPlayer.P2) {//Max's turn
            score.score=Integer.MIN_VALUE;
            for (Move move : successorEvaluations) {
                //Move moveBckUp = new Move(move);//create a copy of move
                GameState minimaxGameState = new GameState(actualGameState);
                ArrayList<Move> tmpMovesList = Game.getAllAllowedMoves(minimaxGameState);
                Move moveFromTmpList = Move.matchMoveBetweenStates(tmpMovesList,move);
                Move.move(minimaxGameState, moveFromTmpList);
                minimaxResult = minimax(depth - 1, minimaxGameState, alpha, beta);
                if(minimaxResult.score > score.score){
                    score.score = minimaxResult.score;
                    score.move = move;
                }
                if(minimaxResult.score > alpha){
                    alpha = minimaxResult.score;
                }
                if(beta <= alpha) break;

            }
            System.out.println("Alpha: "+alpha+" Beta: "+ beta + " Score: "+score.score);
            return score;

        } else {//Min's turn
            score.score=Integer.MAX_VALUE;
            for (Move move : successorEvaluations) {
                //Move moveBckUp = new Move(move);
                GameState minimaxGameState = new GameState(actualGameState);
                ArrayList<Move> tmpMovesList = Game.getAllAllowedMoves(minimaxGameState);
                Move moveFromTmpList = Move.matchMoveBetweenStates(tmpMovesList,move);
                Move.move(minimaxGameState, moveFromTmpList);
                minimaxResult = minimax(depth - 1, minimaxGameState, alpha, beta);
                if(minimaxResult.score < score.score){
                    score.score = minimaxResult.score;
                    score.move = move;
                }
                if(minimaxResult.score < beta){
                    beta = minimaxResult.score;
                }
                if(beta <= alpha) break;


            }
            System.out.println("Alpha: "+alpha+" Beta: "+ beta + " Score: "+score.score);
            return score;
        }

//        //Find the move in the actual game state and return it
//        ArrayList<Move> movesFromActualGameState = Game.getAllAllowedMoves(actualGameState);
//        Move actualMove = null;
//        if(movesFromActualGameState != null && score.move != null){
//            actualMove = Move.matchMoveBetweenStates(movesFromActualGameState,score.move);
//
//        }
//        score.move = actualMove;


//        System.out.println("Alpha: "+alpha+" Beta: "+ beta + " Score: "+score.score);
//        return score;

    }

}
