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

    public void refreshGui() {
        gameGui.refreshTheGui(actualGameState);
    }

    void restartGame() {
        ArrayList<Token> tokenList;
        tokenList = Token.createTokens();
        actualGameState = new GameState(tokenList, Token.TokenPlayer.P1);//by default P1 is the first player
    }


    public static ArrayList<Move> getAllAlowedMoves(GameState gameState) {
        ArrayList<Move> moveList = new ArrayList<Move>();
        for (Token token : gameState.tokenList) {
            if (token.player == gameState.currentPlayer) {//check if the token is from the current player

            }
        }
    }

    public static ArrayList<Move> getPossibleMovesForToken(GameState gameState, Token token) {
        boolean fl = false, fr = false, bl = false, br = false;
        ArrayList<Move> moveList = new ArrayList<Move>();
        Token tmpToken;

        //check for borders
        if (token.x < 8) {
            if(token.y<8){
                fr = true;
            }
            if(token.y>1){
                br = true;
            }
        }
        if (token.x > 1) {
            if(token.y<8){
                fl = true;
            }
            if(token.y>1){
                bl = true;
            }
        }

        //check for token type and player
        if(token.tType != Token.TokenType.King){//king can move in every direction
            if(token.player == Token.TokenPlayer.P1){//P1 can move only forward
                br = false;
                bl = false;
            }
            else{//P2 can move only backwards
                fr = false;
                fl = false;
            }
        }

        //check the possible moves
        if(fr){//forward right x++, y++
            tmpToken = isThereAnyTokenAtLocation(token.x + 1, token.y+1, gameState.tokenList);
            if(canEatToken(token, tmpToken)){//check if token can be eaten

            }
        }



        return
    }


    public static boolean move(Token token, int newLocX, int newLocY, Token.TokenPlayer currentPlayer, ArrayList<Token> tokenList) {
//        if (token.player == currentPlayer) {//is this the right token to move
//            if (isThereAnyTokenAtLocation(newLocX, newLocY, tokenList)) {//check the next cell
//                return false;
//                //moving P1 fw-left and fw-right
//            } else if (currentPlayer == Token.TokenPlayer.P1 && (token.x - newLocX == 1 && token.y - newLocY == -1) || (token.x - newLocX == -1 && token.y - newLocY == -1)) {
//                token.x = newLocX;
//                token.y = newLocY;
//                return true;
//                //moving P2 fw-left and fw-right
//            } else if (currentPlayer == Token.TokenPlayer.P2 && (token.x - newLocX == 1 && token.y - newLocY == 1) || (token.x - newLocX == -1 && token.y - newLocY == 1)) {
//                token.x = newLocX;
//                token.y = newLocY;
//                return true;
//            }
//        }

        return false;
    }

    public static Token isThereAnyTokenAtLocation(int newLocX, int newLocY, ArrayList<Token> tokenList) {
//        Token tTestP1 = new Token(newLocX, newLocY, Token.TokenPlayer.P1, Token.TokenType.Pawn);
//        Token tTestP2 = new Token(newLocX, newLocY, Token.TokenPlayer.P2, Token.TokenType.Pawn);
//        Token tTestP1K = new Token(newLocX, newLocY, Token.TokenPlayer.P1, Token.TokenType.King);
//        Token tTestP2K = new Token(newLocX, newLocY, Token.TokenPlayer.P2, Token.TokenType.King);
//        if (tokenList.contains(tTestP1) || tokenList.contains(tTestP2) || tokenList.contains(tTestP1K) || tokenList.contains(tTestP2K)) {//Is there any token in new cell?
//            //System.out.println("There is another token. You can't move to this cell.");
//            return true;
//        } else
//            return false;

        for(Token token : tokenList){
            if(token.x == newLocX && token.y == newLocY){
                return token;
            }
        }
        return null;
    }

    //check if currentT can eat the eatenT
    static boolean canEatToken(Token currentT, Token eatenT){
        //check if tokens are adjacent
        if((Math.abs(currentT.x - eatenT.x)!=1)||(Math.abs(currentT.y - eatenT.y)!=1)){
            return false;
        }

        //check if tokens are from opposing players
        if(currentT.player == eatenT.player){
            return false;
        }

        //calculate the new position
        //newLoc = 2*eatenT.pos - currentT.pos
        int newX,newY;
        newX = 2*eatenT.x - currentT.x;
        newY = 2*eatenT.y - currentT.y;

    }
}
