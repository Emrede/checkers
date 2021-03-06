import java.util.ArrayList;

/**
 * Created by Emre on 11/16/2015.
 */
public class Move{
    public Token token;//Current token
    public int targetX;
    public int targetY;
    public boolean isAnEatingMove;

    public Move(Token token, int targetX, int targetY, boolean isAnEatingMove) {
        this.token = token;
        this.targetX = targetX;
        this.targetY = targetY;
        this.isAnEatingMove = isAnEatingMove;
    }
    public Move(Move move){
        Token token = new Token(move.token);
        this.token = token;
        this.targetX = move.targetX;
        this.targetY = move.targetY;
        this.isAnEatingMove = move.isAnEatingMove;
    }
    /**
     * Moves the token to the correct place.
     * This function does not check if the move is a valid one.
     * This function changes the current player
     * This function takes multistep moves into action and sets the gameState accordingly
     *
     * @param gameState
     * @param movement
     */
    public static void move(GameState gameState, Move movement) {
        if (movement.isAnEatingMove) {//Eat a token and move
            Token tokenToBeEaten = Game.isThereAnyTokenAtLocation(movement.targetX, movement.targetY, gameState.tokenList);
            if (tokenToBeEaten == null) {//If this is true, something went wrong
                System.out.println("Move failed. Could not find a token at the target location x:" + movement.targetX + " , y:" + movement.targetY);
                return;
            }
            //Calculate the new location
            int newX, newY;
            newX = 2 * tokenToBeEaten.x - movement.token.x;
            newY = 2 * tokenToBeEaten.y - movement.token.y;
            //Update the token location
            movement.token.x = newX;
            movement.token.y = newY;
            if(newY == 8 || newY ==1){//Check if token becomes a king
                movement.token.tType = Token.TokenType.King;
            }
            gameState.tokenList.remove(tokenToBeEaten);//Remove the eaten token from the list

            //Check if this token can eat another token (Multistep)
            ArrayList<Move> tmpMoveList;
            tmpMoveList = Game.getPossibleMovesForToken(gameState, movement.token);
            if (tmpMoveList == null) {//No more moves possible
                gameState.multiStepMoveOnGo = false;//Multistep finished or not possible
                gameState.multiStepToken = null;
                gameState.currentPlayer = Token.getOpposingPlayer(gameState.currentPlayer);
            } else {//Check if token can eat more tokens at the new location
                boolean canEatMore = false;
                for (Move tmpMove : tmpMoveList) {
                    if (tmpMove.isAnEatingMove) canEatMore = true;
                }
                if (canEatMore) {
                    gameState.multiStepMoveOnGo = true;//
                    gameState.multiStepToken = movement.token;
                } else {
                    gameState.multiStepMoveOnGo = false;//
                    gameState.multiStepToken = null;
                    gameState.currentPlayer = Token.getOpposingPlayer(gameState.currentPlayer);
                }
            }
        } else {//Move the token
            movement.token.x = movement.targetX;
            movement.token.y = movement.targetY;
            if(movement.token.y == 8 || movement.token.y ==1){//Check if token becomes a king
                movement.token.tType = Token.TokenType.King;
            }
            gameState.currentPlayer = Token.getOpposingPlayer(gameState.currentPlayer);
        }
    }
    //Finds a move in a list. Move is in a different instance of game state.
    public static Move matchMoveBetweenStates(ArrayList<Move> moveList,  Move moveToBeFound){
        for(Move move:moveList){
            if(move.targetX == moveToBeFound.targetX){
                if(move.targetY == moveToBeFound.targetY){
                    if(move.token.x == moveToBeFound.token.x){
                        if(move.token.y== moveToBeFound.token.y){
                            return move;
                        }
                    }
                }
            }
        }
        return null;
    }
}