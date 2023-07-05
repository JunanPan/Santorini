package edu.cmu.cs214.hw3;
import java.io.IOException;
import java.util.Map;
import edu.cmu.cs214.hw3.map.godCards.Card;
import fi.iki.elonen.NanoHTTPD;
// NOTE: If you're using NanoHTTPD >= 3.0.0 the namespace is different,
//       instead of the above import use the following:
// import org.nanohttpd.NanoHTTPD;

public class App extends NanoHTTPD {
    private static final int PORT = 8080;
    private Game game;
    private Card player1Card = Card.None;
    private Card player2Card = Card.None;
    public App() throws IOException {
        super(PORT);
        
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        // game = new Game();
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/card")) {// e.g., /card?player1Card=Demeter&player2Card=None
            String card1 = params.get("player1Card");
            String card2 = params.get("player2Card");
            if(card1.equals("Pan")&&card2.equals("None")){
                player1Card = Card.valueOf("None");
                player2Card = Card.valueOf("Pan");
            }
            else if(card1.equals("None")&&card2.equals("Pan"))
            {
                player1Card = Card.valueOf("Pan");
                player2Card = Card.valueOf("None");
            }
            else if(card1.equals("Athena")&&card2.equals("None"))
            {
                player1Card = Card.valueOf("None");
                player2Card = Card.valueOf("Athena");
            }
            else if(card1.equals("None")&&card2.equals("Athena"))
            {
                player1Card = Card.valueOf("Athena");
                player2Card = Card.valueOf("None");
            }
            else{
            player1Card = Card.valueOf(card1);
            player2Card = Card.valueOf(card2);
            }
            
            return newFixedLengthResponse("success");
            // game = new Game(player1Card,player2Card);
        }
        if (uri.equals("/newgame")) {
            game = new Game(player1Card,player2Card);
        }
        else if (uri.equals("/undo")) {
            // e.g., /undo
            game = game.undo();
        }
        else if (uri.equals("/play")) {
            // e.g., /play?x=1&x=1
            Game newGame = game.play(Integer.parseInt(params.get("x")),Integer.parseInt(params.get("y")));
            if(game.equals(newGame)){
                return newFixedLengthResponse("invalid");
            }
            else{
                game = newGame;
            }
        }
        return newFixedLengthResponse(game.getLatestGameStatus().toJson());
    }
}
