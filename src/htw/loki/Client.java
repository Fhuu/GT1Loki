package htw.loki;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Arrays;

import javax.imageio.ImageIO;

import lenz.htw.loki.Move;
import lenz.htw.loki.net.NetworkClient;

public class Client extends Thread {

	private NetworkClient client;
	private String hostname;
	private int clientNumber;
	private GameBoard gameboard;


	/**
	 * hostname, port, teamname, image path
	 * 
	 * @throws IOException
	 */
	public Client(String hostname, int clientNumber) {
		this.hostname = hostname;
		this.clientNumber = clientNumber;
		this.gameboard = new GameBoard();
	}


	@Override
	public void run() {
	    System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname
	            + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
	    try {
	        this.client = new NetworkClient(this.hostname, "player" + clientNumber,
	                ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));

	        boolean receivedMoveComplete = false;
	        while (true) {
	            Move receivedMove = this.client.receiveMove();
	            while (receivedMove != null) {
	                // update push if target is not empty
	                if (this.gameboard.getStoneFrom(receivedMove.to) != null) {
	                    System.out.println("push called");
	                    this.gameboard.getStoneFrom(receivedMove.to).setPosition(receivedMove.push);
	                }

	                if (this.gameboard.getStoneFrom(receivedMove.from) != null) {
	                    System.out.println("updating Local from " + this.gameboard.getStoneFrom(receivedMove.from).getPosition() + " to " + receivedMove.to);
	                    this.gameboard.getStoneFrom(receivedMove.from).setPosition(receivedMove.to);
	                    gameboard.checkDeadStone();
	                }
	                receivedMove = this.client.receiveMove();
	            }
	            receivedMoveComplete = true;
	            
	            if (receivedMoveComplete) {
	                this.move();
	                receivedMoveComplete = false;
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void move() {
		final int playerNumber = this.client.getMyPlayerNumber();
		Stone[] stones = this.gameboard.getStones(playerNumber);
		ArrayList<Integer> testPos = new ArrayList<Integer>();
		for (Stone stone : stones) {
			testPos.add(stone.getPosition());
		}
		System.out.println("Player " + this.clientNumber +" stone pos: "+ testPos.toString());
		
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (Stone stone : stones) {
			if (stone.getPosition() == -1) continue;
			Integer[] moves = stone.getValidMoves(this.gameboard);
			if(moves.length == 0) continue;
			positions.add(stone.getPosition());
			String stringResult = "";
			for (Integer move : moves) {
				stringResult = stringResult + ", " + move;
			}

		}
		

		Integer movedStonePosition = positions.get(new Random().nextInt(positions.size()));

		for (Stone stone : stones) {
			if (stone.getPosition() == movedStonePosition && stone.getPosition() != -1) {
				Integer[] possibleMoves = stone.getValidMoves(this.gameboard);
				Integer selectedMove = possibleMoves[(new Random()).nextInt(possibleMoves.length)];
				Stone targetPos = gameboard.getStoneFrom(selectedMove);
				
				if (targetPos == null) {
					this.client.sendMove(new Move(stone.getPosition(), selectedMove, 255));
					System.out.println("updating Server from " + stone.getPosition() + " to " + selectedMove);
				} else {
					Integer[] possiblePushes = targetPos.allEmptyNeighbour(this.gameboard);
					System.out.println("possibleushes: " + new ArrayList<>(Arrays.asList(possiblePushes)).toString());
					Integer selectedPush = possiblePushes[(new Random()).nextInt(possiblePushes.length)];
					this.client.sendMove(new Move(stone.getPosition(), selectedMove, selectedPush));
				}

				break;
			}
		}

	}

}
