import java.util.ArrayList;

/**
 * Created by Emre on 11/23/2015.
 */

public class Game {

    GameState actualGameState;
    Gui gameGui = new Gui();

    public Game() {
        restartGame();//create new tokens
        gameGui.refreshTheGui(actualGameState);
        refreshGui();
    }

    public void refreshGui(){
         gameGui.refreshTheGui(actualGameState);
    }

    void restartGame() {
        ArrayList<Token> tokenList;
        tokenList = Token.createTokens();
        actualGameState = new GameState(tokenList, Token.TokenPlayer.P1);//by default P1 is the first player
    }


    public static boolean move(Token token, int newLocX, int newLocY, Token.TokenPlayer currentPlayer, ArrayList<Token> tokenList) {
        if (token.player == currentPlayer) {//is this the right token to move
            if (isThereAnyToken(newLocX, newLocY, tokenList)) {//check the next cell
                return false;
                //moving P1 fw-left and fw-right
            } else if (currentPlayer == Token.TokenPlayer.P1 && (token.x - newLocX == 1 && token.y - newLocY == -1) || (token.x - newLocX == -1 && token.y - newLocY == -1)) {
                token.x = newLocX;
                token.y = newLocY;
                return true;
                //moving P2 fw-left and fw-right
            } else if (currentPlayer == Token.TokenPlayer.P2 && (token.x - newLocX == 1 && token.y - newLocY == 1) || (token.x - newLocX == -1 && token.y - newLocY == 1)) {
                token.x = newLocX;
                token.y = newLocY;
                return true;
            }
        }

        return false;
    }

    public static boolean isThereAnyToken(int newLocX, int newLocY, ArrayList<Token> tokenList) {
        Token tTestP1 = new Token(newLocX, newLocY, Token.TokenPlayer.P1, Token.TokenType.Pawn);
        Token tTestP2 = new Token(newLocX, newLocY, Token.TokenPlayer.P2, Token.TokenType.Pawn);
        Token tTestP1K = new Token(newLocX, newLocY, Token.TokenPlayer.P1, Token.TokenType.King);
        Token tTestP2K = new Token(newLocX, newLocY, Token.TokenPlayer.P2, Token.TokenType.King);

        if (tokenList.contains(tTestP1) || tokenList.contains(tTestP2) || tokenList.contains(tTestP1K) || tokenList.contains(tTestP2K)) {//Is there any token in new cell?
            System.out.println("There is another token. You can't move to this cell.");
            return true;
        } else
            return false;


    }
}
