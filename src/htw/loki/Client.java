package htw.loki;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import javax.imageio.ImageIO;

import lenz.htw.loki.Move;
import lenz.htw.loki.net.NetworkClient;

public class Client extends Thread {

	private NetworkClient client;
	private String hostname;
	private int clientNumber;
	private GameBoard gameboard;
	private Stone[] stones;

	/**
	 * hostname, port, teamname, image path
	 * 
	 * @throws IOException
	 */
	public Client(String hostname, int clientNumber) {
		this.hostname = hostname;
		this.clientNumber = clientNumber;
		this.gameboard = GameBoard.getInstance();
		this.stones = new Stone[4];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname
				+ " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber,
					ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			final Integer[] STONEPOSITIONS = Stone.createInitialStonePosition(this.client.getMyPlayerNumber());
			for (int index = 0; index < STONEPOSITIONS.length; index++) {
				this.stones[index] = new Stone(STONEPOSITIONS[index]);
			}

		      // Call getAllPossibleMoves and convert the result to a string
			for(Stone stone:stones) {
		        Integer[] allPossibleMoves = stone.getAllPossibleMoves(gameboard, stones);
		        StringBuilder movesString = new StringBuilder();
		        for (int i = 0; i < allPossibleMoves.length; i++) {
		            movesString.append(allPossibleMoves[i]);
		            if (i < allPossibleMoves.length - 1) {
		                movesString.append(", ");
		            }
		        }
		        System.out.println("All Possible Moves: " + stone.getPosition()+ " can go to " +movesString);
			}

	        
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	// TODO: Movedamn

}
