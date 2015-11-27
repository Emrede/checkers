import java.util.ArrayList;

/**
 * Created by Emre on 08/11/2015.
 */
public class Token {

    public int x, y;
    public TokenPlayer player;
    public TokenType tType;

    public enum TokenPlayer {P1, P2};

    public enum TokenType {King, Pawn};

    public Token() {
        this.x = 0;
        this.y = 0;
        this.player = TokenPlayer.P1;
    }

    public Token(int x, int y, TokenPlayer player, TokenType tType) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.tType = TokenType.Pawn;
    }


    public static ArrayList<Token> createTokens() {

        ArrayList<Token> newToken = new ArrayList<Token>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 3; y++) {
                if (x % 2 == 1 && y % 2 == 1 || x % 2 == 0 && y % 2 == 0) {
                    Token p1Token = new Token();
                    p1Token.x = x;
                    p1Token.y = y;
                    p1Token.player = TokenPlayer.P1;
                    newToken.add(p1Token);
                }
            }
        }
        for (int x = 1; x <= 8; x++) {
            for (int y = 6; y <= 8; y++) {
                if (x % 2 == 0 && y % 2 == 0 || x % 2 == 1 && y % 2 == 1) {
                    Token p2Token = new Token();
                    p2Token.x = x;
                    p2Token.y = y;
                    p2Token.player = TokenPlayer.P2;
                    newToken.add(p2Token);
                }
            }
        }
        return newToken;
    }



}



