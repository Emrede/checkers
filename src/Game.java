import java.util.ArrayList;

/**
 * Created by Emre on 11/23/2015.
 */

public class Game {

    GameState actualGameState;
    Gui gameGui = new Gui(this);

    //Difficulty Level Changer: Easy:1  Medium: 2 Hard: 3
    public int difficultyLevel = 2;//Default: Medium
    boolean isGamePaused = false;

    public Game() {
        restartGame();//create new tokens
        gameGui.refreshTheGui(actualGameState);
        refreshGui();

        ArrayList<Move> movelist = getAllAllowedMoves(actualGameState);
        int i = 0;
        int y;
    }

    public void refreshGui() {
        gameGui.refreshTheGui(actualGameState);
    }

    void restartGame() {
        ArrayList<Token> tokenList;
        tokenList = Token.createTokens();
        actualGameState = new GameState(tokenList, Token.TokenPlayer.P1);//by default P1 is the first player
    }


    public static ArrayList<Move> getAllAllowedMoves(GameState gameState) {
        ArrayList<Move> nonEatingMoveList = new ArrayList<>();
        ArrayList<Move> eatingMoveList = new ArrayList<>();
        if(gameState.multiStepMoveOnGo) {//check if multistep move in progress
            ArrayList<Move> tmpMoveList = getPossibleMovesForToken(gameState, gameState.multiStepToken);//get possible moves for a token
            if (tmpMoveList != null) {//check if the move list is empty
                for (Move move : tmpMoveList) {
                    if (move.isAnEatingMove) {//check if move is an eating move
                        eatingMoveList.add(move);
                    }
                }
            }
            if(eatingMoveList.isEmpty()){//a mutistep move can only eat.
                return null;//if no eating moves are possible return null
            }
        }
        else{//no multistep move in progress
            for (Token token : gameState.tokenList) {//get possible moves for each token on the board
                if (token.player == gameState.currentPlayer) {//check if the token is from the current player
                    ArrayList<Move> tmpMoveList = getPossibleMovesForToken(gameState, token);//get possible moves for a token
                    if (tmpMoveList != null) {//check if the move list is empty
                        for (Move move : tmpMoveList) {
                            if (move.isAnEatingMove) {//check if move is an eating move
                                eatingMoveList.add(move);
                            } else {
                                nonEatingMoveList.add(move);
                            }
                        }
                    }
                }
            }
        }

        //if there are eating moves, only eating moves can be executed
        if (!eatingMoveList.isEmpty()) {
            return eatingMoveList;
        } else {
            if (nonEatingMoveList.isEmpty()) {
                return null;//if no moves are possible return null
            } else {
                return nonEatingMoveList;
            }
        }
    }


    //Returns possible moves for a token
    //If no moves are possible return null
    public static ArrayList<Move> getPossibleMovesForToken(GameState gameState, Token token) {
        boolean fl = false, fr = false, bl = false, br = false;
        ArrayList<Move> moveList = new ArrayList<>();
        Move tmpMove;
        Token tmpToken;

        //Check for borders
        if (token.x < 8) {
            if (token.y < 8) {
                fr = true;
            }
            if (token.y > 1) {
                br = true;
            }
        }
        if (token.x > 1) {
            if (token.y < 8) {
                fl = true;
            }
            if (token.y > 1) {
                bl = true;
            }
        }

        //check for token type and player
        if (token.tType != Token.TokenType.King) {//king can move in every direction
            if (token.player == Token.TokenPlayer.P1) {//P1 can move only forward
                br = false;
                bl = false;
            } else {//P2 can move only backwards
                fr = false;
                fl = false;
            }
        }

        //check the possible moves
        tmpMove = null;
        if (fr) {//forward right x++, y++
            int newX = token.x + 1;
            int newY = token.y + 1;
            tmpToken = isThereAnyTokenAtLocation(newX, newY, gameState.tokenList);
            if (tmpToken != null) {//there is a token at the target location
                if (canEatToken(token, tmpToken)) {//check if token can be eaten
                    tmpMove = new Move(token, newX, newY, true);
                }
            } else {//target location is empty
                tmpMove = new Move(token, newX, newY, false);
            }

            if (tmpMove != null) {
                moveList.add(tmpMove);
            }
        }


        tmpMove = null;//empty the moveList for next check
        if (fl) {//forward left x--, y++
            int newX = token.x - 1;
            int newY = token.y + 1;
            tmpToken = isThereAnyTokenAtLocation(newX, newY, gameState.tokenList);
            if (tmpToken != null) {//there is a token at the target location
                if (canEatToken(token, tmpToken)) {//check if token can be eaten
                    tmpMove = new Move(token, newX, newY, true);
                }
            } else {//target location is empty
                tmpMove = new Move(token, newX, newY, false);
            }

            if (tmpMove != null) {
                moveList.add(tmpMove);
            }
        }

        tmpMove = null;//empty the moveList for next check
        if (br) {//backwards right x++, y--
            int newX = token.x + 1;
            int newY = token.y - 1;
            tmpToken = isThereAnyTokenAtLocation(newX, newY, gameState.tokenList);
            if (tmpToken != null) {//there is a token at the target location
                if (canEatToken(token, tmpToken)) {//check if token can be eaten
                    tmpMove = new Move(token, newX, newY, true);
                }
            } else {//target location is empty
                tmpMove = new Move(token, newX, newY, false);
            }

            if (tmpMove != null) {
                moveList.add(tmpMove);
            }
        }

        tmpMove = null;//empty the moveList for next check
        if (bl) {//backwards left x--, y--
            int newX = token.x - 1;
            int newY = token.y - 1;
            tmpToken = isThereAnyTokenAtLocation(newX, newY, gameState.tokenList);
            if (tmpToken != null) {//there is a token at the target location
                if (canEatToken(token, tmpToken)) {//check if token can be eaten
                    tmpMove = new Move(token, newX, newY, true);
                }
            } else {//target location is empty
                tmpMove = new Move(token, newX, newY, false);
            }

            if (tmpMove != null) {
                moveList.add(tmpMove);
            }
        }

        if (moveList.isEmpty()) {
            return null;
        } else {
            return moveList;
        }
    }


    //
    //

    /**
     * Moves the token to the correct place.
     * This function does not check if the move is a valid one.
     * This function changes the current player
     * This function takes multistep moves in to action and sets the gameState accourdingly
     *
     * @param gameState
     * @param move
     */
    public static void move(GameState gameState, Move move) {
        if (move.isAnEatingMove) {//Eat a token and move
            Token tokenToBeEaten = isThereAnyTokenAtLocation(move.targetX, move.targetY, gameState.tokenList);
            if (tokenToBeEaten == null) {//If this is true, something went wrong
                System.out.println("Move failed. Could not find a token at the target location x:" + move.targetX + " , y:" + move.targetY);
                return;
            }
            //calculate the new location
            int newX, newY;
            newX = 2 * tokenToBeEaten.x - move.token.x;
            newY = 2 * tokenToBeEaten.y - move.token.y;
            //update the token location
            move.token.x = newX;
            move.token.y = newY;

            gameState.tokenList.remove(tokenToBeEaten);//remove the eaten token from the list

            //check if this token can eat another token(multistep)
            ArrayList<Move> tmpMoveList;
            tmpMoveList = getPossibleMovesForToken(gameState, move.token);
            if (tmpMoveList == null) {//no more moves possible
                gameState.multiStepMoveOnGo = false;//multistep finished or not possible
                gameState.multiStepToken = null;
            } else {//check if token can eat more tokens at the new location
                boolean canEatMore = false;
                for (Move tmpMove : tmpMoveList) {
                    if (tmpMove.isAnEatingMove) canEatMore = true;
                }
                if (canEatMore) {
                    gameState.multiStepMoveOnGo = true;//
                    gameState.multiStepToken = move.token;
                } else {
                    gameState.multiStepMoveOnGo = false;//
                    gameState.multiStepToken = null;
                    gameState.currentPlayer = Token.getOpposingPlayer(gameState.currentPlayer);
                }
            }
        } else {//move the token
            move.token.x = move.targetX;
            move.token.y = move.targetY;
            gameState.currentPlayer = Token.getOpposingPlayer(gameState.currentPlayer);
        }
    }

    public static Token isThereAnyTokenAtLocation(int newLocX, int newLocY, ArrayList<Token> tokenList) {


        for (Token token : tokenList) {
            if (token.x == newLocX && token.y == newLocY) {
                return token;
            }
        }
        return null;
    }

    //check if currentT can eat the eatenT
    static boolean canEatToken(Token currentT, Token eatenT) {
        //check if tokens are adjacent
        if ((Math.abs(currentT.x - eatenT.x) != 1) || (Math.abs(currentT.y - eatenT.y) != 1)) {
            return false;
        }

        //check if tokens are from opposing players
        if (currentT.player == eatenT.player) {
            return false;
        }

        //calculate the new position
        //newLoc = 2*eatenT.pos - currentT.pos
        int newX, newY;
        newX = 2 * eatenT.x - currentT.x;
        newY = 2 * eatenT.y - currentT.y;
        //check if location is out of board
        if (newY < 1 || newX < 1 || newX > 8 || newY > 8) {
            return false;
        }

        return true;

    }
}
