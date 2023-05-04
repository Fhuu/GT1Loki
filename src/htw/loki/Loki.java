package htw.loki;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lenz.htw.loki.Move;
import lenz.htw.loki.net.NetworkClient;

public class Loki {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 // Vorbereitung ohne Server
        // Startbrettkonfiguration erstellen
		
		Client[] clients = new Client[3];
		for(int index = 0; index < clients.length; index++) {
//			threads[index] = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						NetworkClient client = new NetworkClient("127.0.0.1", "Die allerbesten", ImageIO.read(new File("C:\\Users\\dirob\\eclipse-workspace\\GT2Loki\\image\\image1.png")));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
			System.out.println(index);
			clients[index] = new Client("127.0.0.1", index);
			clients[index].start();
//			threads[index] = new Thread(new Client("127.0.0.1", index));
//			threads[index].run();
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
