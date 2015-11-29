import java.util.ArrayList;

/**
 * Created by Emre on 11/26/2015.
 */

//A game instance
public class GameState {
    public enum GameResult{P1Wins, P2Wins, Draw, Continuue};

    public Token.TokenPlayer currentPlayer;
    public Token selectedToken;
    public ArrayList<Token> tokenList = new ArrayList<Token>();
    public boolean multiStepMoveOnGo;
    public boolean isAnyTokenSelected = false;
    public Token multiStepToken;

    
    public GameState(ArrayList<Token> tokenList, Token.TokenPlayer currentPlayer){
        this.currentPlayer = currentPlayer;
        for(Token token : tokenList){
            this.tokenList.add(token);
        }

    }


    public static int getScore(GameState gamestate){
        int score = 0;
        for(Token token: gamestate.tokenList){
            if(token.player == Token.TokenPlayer.P1){//P1 tokens increase the score
                if(token.tType == Token.TokenType.Pawn){
                    score += 2;//kings count as 2
                }
                else{
                    score++;//
                }
            }
            else{//P2 tokens decrease the score
                if(token.tType == Token.TokenType.Pawn){
                    score -= 2;//kings count as 2
                }
                else{
                    score--;//
                }
            }
        }
        return score;
    }

    public static GameResult getResult(GameState gameState){
        //Check tokens
        boolean p1TokenFound = false;
        boolean p2TokenFound = false;

        for(Token token:gameState.tokenList){
            if(token.player == Token.TokenPlayer.P1) p1TokenFound = true;
            else p2TokenFound = true;
        }
        //If a player does not have anymore tokens other pllayer wins
        if(!p1TokenFound) return GameResult.P2Wins;
        if(!p2TokenFound) return GameResult.P1Wins;

        //Check possible moves to see if the current player can move
        ArrayList<Move> tmpMoveList = Game.getAllAllowedMoves(gameState);
        if(tmpMoveList == null){
            //No possible moves! current player can not move so opposing player wins
            if(gameState.currentPlayer == Token.TokenPlayer.P1) return GameResult.P2Wins;
            else return  GameResult.P1Wins;
        }

        //Draw conditions are not checked.
        //Currently draw not possible
        return GameResult.Continuue;

    }


}
