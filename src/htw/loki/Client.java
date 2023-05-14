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
	
	/**
	 * hostname, port, teamname, image path
	 * @throws IOException 
	 */
	public Client(String hostname, int clientNumber) {
		this.hostname = hostname;
		this.clientNumber = clientNumber;
		this.gameboard = GameBoard.getInstance();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			this.checkAllPossibleMoves();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void checkAllPossibleMoves() {
		Integer[] positions = gameboard.getPlayerPositions(client.getMyPlayerNumber());
		Arrays.sort(positions);
		System.out.println(Arrays.toString(positions));
		
		ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		
		for(int position : positions) {
			Integer[] possibleMoves = gameboard.getPossibleNextPositions(position);
			for(int possibleMove : possibleMoves) {
				if( possibleMove > -1 && !allPossibleMoves.contains(possibleMove) && !(new ArrayList<Integer>(Arrays.asList(positions)).contains(possibleMove))) 
					allPossibleMoves.add(possibleMove);

			}
				
		}
		
		System.out.println(allPossibleMoves.toString());
		
		for(Integer possibleMove : allPossibleMoves) {
			System.out.println(this.client.getMyPlayerNumber() + " can move to " + possibleMove);
		}
	}
	
	public Integer chooseStoneToMove(ArrayList<Integer> allPossibleMoves, Integer[] positions) {
		Random ran = new Random();
		
		int indexPush = ran.nextInt(positions.length);
		int to = allPossibleMoves.get(indexPush);
		Integer[] neighbors = gameboard.getPossibleNextPositions(to);
		int targetElement = -1;
		
	    if (this.client.getMyPlayerNumber() == 0) {
	        targetElement = 0;
	    } else if (this.client.getMyPlayerNumber() == 1) {
	        targetElement = 25;
	    } else if (this.client.getMyPlayerNumber() == 2) {
	        targetElement = 35;
	    }
		
	    for (int element : positions) {
	        if (element == targetElement) {
	            return element;
	        }

	        for (int neighbor : neighbors) {
	            if (element != neighbor) {
	                return element;
	            }
	        }
	    }
	    
		return null;
	}
	
	
	public void move() {
//		Move move = new Move(from, to, push);
	}
	//TODO: Move
	
}
