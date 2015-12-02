import java.util.ArrayList;

/**
 * Created by Emre on 08/11/2015.
 */
public class Token {

    public int x, y;
    public TokenPlayer player;
    public TokenType tType;

    public enum TokenPlayer {P1, P2};//P1:Black/Player  P2:Red/AI

    public enum TokenType {King, Pawn};

    public Token() {
        this.x = 0;
        this.y = 0;
        this.player = TokenPlayer.P1;
        this.tType =TokenType.Pawn;
    }

    public Token(Token token){

        this.x = token.x;
        this.y = token.y;
        this.player = TokenPlayer.values()[token.player.ordinal()];
        this.tType = TokenType.values()[token.tType.ordinal()];
    }

    public Token(int x, int y, TokenPlayer player, TokenType tType) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.tType = tType;
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

    public static ArrayList<Token> createTestingTokens() {
        ArrayList<Token> newTokens = new ArrayList<Token>();
        //p1 tokens
        newTokens.add(new Token(2,2,TokenPlayer.P1,TokenType.King));

        //p2 tokens
        newTokens.add(new Token(2,8,TokenPlayer.P2,TokenType.Pawn));
//        newTokens.add(new Token(2,6,TokenPlayer.P2,TokenType.Pawn));
//        newTokens.add(new Token(1,7,TokenPlayer.P2,TokenType.Pawn));
        newTokens.add(new Token(2,4,TokenPlayer.P2,TokenType.Pawn));



        return newTokens;
    }

    //returns the opposing player
    public static TokenPlayer getOpposingPlayer(TokenPlayer p){
        if(p==TokenPlayer.P1) return TokenPlayer.P2;
        else return TokenPlayer.P1;
    }



}



