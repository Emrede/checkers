import java.util.ArrayList;

/**
 * Created by Emre on 11/26/2015.
 */

//a game instance
public class GameState {
    public Token.TokenPlayer currentPlayer;
    public Token selectedToken;
    public ArrayList<Token> tokenList = new ArrayList<Token>();

    
    public GameState(ArrayList<Token> tokenList, Token.TokenPlayer currentPlayer){
        this.currentPlayer = currentPlayer;
        for(Token token : tokenList){
            this.tokenList.add(token);
        }

    }
}
