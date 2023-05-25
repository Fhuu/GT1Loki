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
			final Integer PLAYERNUMBER = this.client.getMyPlayerNumber();
			final Integer[] STONEPOSITIONS = Stone.createInitialStonePosition(PLAYERNUMBER);
			for (int index = 0; index < STONEPOSITIONS.length; index++) {
				this.stones[index] = new Stone(STONEPOSITIONS[index], PLAYERNUMBER);
			}
			
			move();
			
	        while (true) {
	            while ((client.receiveMove()) != null) {
	            	// update stone & gameboard
	            }
	            // berechne genialen eigenen Zug
	            move();
	        }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void move() {
		long time = System.currentTimeMillis();
		ArrayList<Integer> positions = new ArrayList<Integer>();
		Stone movedStone = null;
		for (Stone stone : stones) {
			final Integer[] possibleMoves = stone.getValidMoves(this.gameboard, this.stones);
			if(possibleMoves.length == 0) continue;
			positions.add(stone.getPosition());

		}
		Integer movedStonePosition = positions.get(new Random().nextInt(positions.size()));
		
		for (Stone stone : stones) {
			if(stone.getPosition() == movedStonePosition) {
				movedStone = stone;
			}
		}
		
		Integer[] possibleMoves = movedStone.getValidMoves(this.gameboard, this.stones);
		this.client.sendMove(new Move(movedStone.getPosition(),possibleMoves[(new Random()).nextInt(possibleMoves.length)], 0 ));
		this.stones = Stone.updateStonePosition(stones, movedStone.getPosition(), possibleMoves[(new Random()).nextInt(possibleMoves.length)]);
		System.out.println("Player " + this.client.getMyPlayerNumber() + " took " + (System.currentTimeMillis() - time)
				+ " ms to finish processing valid moves");

	}

}
