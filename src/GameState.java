import java.util.ArrayList;

/**
 * Created by Emre on 11/16/2015.
 */

//A game instance
public class GameState {
    public enum GameResult {P1Wins, P2Wins, Continue}

    public Token.TokenPlayer currentPlayer;
    public Token selectedToken;
    public ArrayList<Token> tokenList = new ArrayList<Token>();
    public boolean multiStepMoveOnGo;
    public boolean isAnyTokenSelected = false;
    public Token multiStepToken;

    public GameState(ArrayList<Token> tokenList, Token.TokenPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
        for (Token token : tokenList) {
            this.tokenList.add(token);
        }
    }
    public GameState(GameState gameState) {
        this.currentPlayer = Token.TokenPlayer.values()[gameState.currentPlayer.ordinal()];
        if (gameState.selectedToken != null) this.selectedToken = new Token(gameState.selectedToken);
        if (gameState.tokenList != null) {
            for (Token token : gameState.tokenList) {
                Token tmpToken = new Token(token);
                tokenList.add(tmpToken);
            }
        }
        this.multiStepMoveOnGo = gameState.multiStepMoveOnGo;
        if (gameState.multiStepToken != null) this.multiStepToken = new Token(gameState.multiStepToken);
    }
    public static int getScore(GameState gamestate) {
        int score = 0;
        for (Token token : gamestate.tokenList) {
            if (token.player == Token.TokenPlayer.P2) {//P2 tokens increase the score
                if (token.tType == Token.TokenType.King) {
                    score += 3;//Kings count as 3
                } else {
                    score++;//Pawns counts as 1
                }
            } else {//P2 tokens decrease the score
                if (token.tType == Token.TokenType.King) {
                    score -= 3;//Kings count as 3
                } else {
                    score--;//Pawns counts as 1
                }
            }
        }
        GameResult gameResult = getResult(gamestate);
        if (gameResult == GameResult.P1Wins) score -= 50;//Winning the game counts as 50
        if (gameResult == GameResult.P2Wins) score += 50;
        System.out.println(score);
        return score;
    }
    public static GameResult getResult(GameState gameState) {
        //Check tokens
        boolean p1TokenFound = false;
        boolean p2TokenFound = false;

        for (Token token : gameState.tokenList) {
            if (token.player == Token.TokenPlayer.P1) p1TokenFound = true;
            else p2TokenFound = true;
        }
        //If a player does not have any tokens other player wins
        if (!p1TokenFound) return GameResult.P2Wins;
        if (!p2TokenFound) return GameResult.P1Wins;

        //Check possible moves to see if the current player can move
        ArrayList<Move> tmpMoveList = Game.getAllAllowedMoves(gameState);
        if (tmpMoveList == null) {
            //No possible moves! Current player can not move so opposing player wins.
            if (gameState.currentPlayer == Token.TokenPlayer.P1) return GameResult.P2Wins;
            else return GameResult.P1Wins;
        }
        return GameResult.Continue;
    }
}