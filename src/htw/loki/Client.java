package htw.loki;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lenz.htw.loki.Move;
import lenz.htw.loki.net.NetworkClient;

public class Client implements Runnable {
	
	private NetworkClient client;
	
	/**
	 * hostname, port, teamname, image path
	 * @throws IOException 
	 */
	public Client(String hostname, int clientNumber) throws IOException {
		this.client = new NetworkClient(hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image1.png")));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
}
