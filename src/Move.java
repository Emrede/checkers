/**
 * Created by Emre on 11/27/2015.
 */
public class Move {
    public Token token;
    public int targetX;
    public int targetY;
    public boolean isAnEatingMove;

    public Move(Token token, int targetX, int targetY, boolean isAnEatingMove) {
        this.token = token;
        this.targetX = targetX;
        this.targetY = targetY;
        this.isAnEatingMove = isAnEatingMove;
    }

}
