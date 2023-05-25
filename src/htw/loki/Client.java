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
	private Integer[] player0stones;
	private Integer[] player1stones;
	private Integer[] player2stones;
	private Integer activePlayer;

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
		this.player0stones = gameboard.getPlayer0stones();
		this.player1stones = gameboard.getPlayer1stones();
		this.player2stones = gameboard.getPlayer2stones();
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
			
			Integer[] STONEPOSITIONS = null;
			
			
			if(PLAYERNUMBER == 0) {
				STONEPOSITIONS = gameboard.getPlayer0stones();
			} else if (PLAYERNUMBER == 1) {
				STONEPOSITIONS = gameboard.getPlayer1stones();
			} else {
				STONEPOSITIONS = gameboard.getPlayer2stones();
			}
			
			for (int index = 0; index < STONEPOSITIONS.length; index++) {
				this.stones[index] = new Stone(STONEPOSITIONS[index], PLAYERNUMBER);
			}
			
			
	        while (true) {
	        	Move receivedMove = this.client.receiveMove();
	            while (receivedMove != null) {
	            	// check which player is moving
	            	System.out.println(this.client.getMyPlayerNumber() +" test");
	            	
	  
	            	System.out.println(receivedMove.from);
	            	System.out.println(receivedMove.to);
	            	
	            	for(Integer pos : this.player0stones ) {
	            		if(receivedMove.from == pos) {
	            			this.activePlayer = 0;
	            			// update gameboard from player 0
	            			break;
	            		}
	            	}
	            	for(Integer pos : this.player1stones ) {
	            		if(receivedMove.from == pos) {
	            			this.activePlayer = 1;
	            			// update gameboard from player 1
	            			break;
	            		}
	            	}
	            	for(Integer pos : this.player2stones ) {
	            		if(receivedMove.from == pos) {
	            			this.activePlayer = 2;
	            			// update gameboard from player 2
	            			break;
	            		}
	            	}
	            	
	            	this.gameboard.updateBoard(this.activePlayer, receivedMove.from, receivedMove.to);
	            	break;
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
		Integer selectedMove = possibleMoves[(new Random()).nextInt(possibleMoves.length)];
		this.client.sendMove(new Move(movedStone.getPosition(),selectedMove, 0 ));
		this.gameboard.updateBoard(client.getMyPlayerNumber(), movedStone.getPosition(), selectedMove);

		this.stones = Stone.updateStonePosition(stones, movedStone.getPosition(), selectedMove);
		
		System.out.println("Player " + this.client.getMyPlayerNumber() + " took " + (System.currentTimeMillis() - time)
				+ " ms to finish processing valid moves");
	}

}
