package htw.loki;

public class Loki {
	
	private static Boolean hasWinner = false;
	private static GameBoard gameBoard = GameBoard.getInstance();
	public static int playerCount = 3;

	public static void hasWinner() {
		Loki.hasWinner = true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // Vorbereitung ohne Server
        // Startbrettkonfiguration erstellen
		
		// Create three clients from different threads
		Client[] clients = new Client[1];
		for(int index = 0; index < clients.length; index++) {
			clients[index] = new Client("127.0.0.1", index);
			clients[index].start();
		}

        // in diesem Moment läuft das Spiel

        // clients.getMyPlayerNumber();
        // clients.getExpectedNetworkLatencyInMilliseconds();
        // clients.getTimeLimitInSeconds();

        // Move move;

        // while (true) {
            // while ((move = client.receiveMove()) != null) {
                // verarbeite Zug
                // Brettkonfiguration aktualisieren
            // }
            // berechne genialen eigenen Zug
            // client.sendMove();
        // }
        
       
	}	

}
