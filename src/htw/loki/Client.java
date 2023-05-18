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

public class Client extends Thread{
	
	private NetworkClient client;
	private String hostname;
	private int clientNumber;
	private GameBoard gameboard;
	private Stone[] stones;
	
	/**
	 * hostname, port, teamname, image path
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
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			final Integer[] STONEPOSITIONS = Stone.createInitialStonePosition(this.client.getMyPlayerNumber());
			for(int index = 0; index < STONEPOSITIONS.length; index++) {
				this.stones[index] = new Stone(STONEPOSITIONS[index]);
			}
			this.move();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Integer> getAllPossibleMoves() {
		Integer[] positions = new Integer[4];
		for(int index = 0; index < positions.length; index++) {
			positions[index] = this.stones[index].getPosition();
		}
		Arrays.sort(positions);		
		
		ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		for(int position : positions) {
			Integer[] possibleMoves = gameboard.getPossibleNextPositions(position);
			for(int possibleMove : possibleMoves) {
				if( possibleMove > -1 && !allPossibleMoves.contains(possibleMove) && !(new ArrayList<Integer>(Arrays.asList(positions)).contains(possibleMove))) allPossibleMoves.add(possibleMove);
			}
		}
		
		return allPossibleMoves;
	}
	
	
	public void move() {
		ArrayList<Integer> allPossibleMoves = this.getAllPossibleMoves();
//		Integer[] stonePositions = gameboard.getPlayerPositions(this.client.getMyPlayerNumber());
//		Integer movedStone = (new Random()).nextInt(stonePositions.length);
//		this.client.sendMove(new Move(stonePositions[movedStone], allPossibleMoves.get((new Random()).nextInt(allPossibleMoves.size())), 0));
		
		//TODO:
		// - check whether the stone moving is allowed to move
		// - refresh gameboard
//		gameboard.
	}
	//TODO: Movedamn
	
	
}
