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
	private Stone stonePlayed;

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

	// public void setActivePlayer(Integer player) {
	// this.activePlayer = player;
	// }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname
				+ " with image from .\\image\\image" + (this.clientNumber+1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber,
					ImageIO.read(new File(".\\image\\image" + (this.clientNumber +1 ) + ".png")));

			while (true) {
				Move receivedMove = this.client.receiveMove();
				while (receivedMove != null) {	    
					Stone movedStone = this.gameboard.getStoneFrom(receivedMove.from);
					Stone targetPos = this.gameboard.getStoneFrom(receivedMove.to);
					
					// update push if target is not empty
					if (targetPos != null) {
						targetPos.setPosition(receivedMove.push);
						System.out.println("to: " + targetPos.getPosition());
					}
					
					if (movedStone != null) {
						System.out.println("from: " + movedStone.getPosition());
					}
					
					movedStone.setPosition(receivedMove.to);
					
	            	break;
				}
				this.move();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveTest() {
		this.client.sendMove(new Move(1, 7, 7));
	}
	
	public void move() {
		
		long time = System.currentTimeMillis();
		final int playerNumber = this.client.getMyPlayerNumber();
		Stone[] stones = this.gameboard.getStones(playerNumber);
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (Stone stone : stones) {
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
			if (stone.getPosition() == movedStonePosition) {
				Integer[] possibleMoves = stone.getValidMoves(this.gameboard);
				Integer selectedMove = possibleMoves[(new Random()).nextInt(possibleMoves.length)];
				System.out.println(
						"Player " + playerNumber + " Moving Stone " + stone.getPosition() + " to " + selectedMove);
				Stone targetPos = gameboard.getStoneFrom(selectedMove);
				
				if (targetPos == null) {
					this.client.sendMove(new Move(stone.getPosition(), selectedMove, selectedMove));
					stone.setPosition(selectedMove);
				} else {
					Integer[] possiblePushes = targetPos.allEmptyNeighbour(this.gameboard);
					Integer selectedPush = possiblePushes[(new Random()).nextInt(possiblePushes.length)];
					this.client.sendMove(new Move(stone.getPosition(), selectedMove, selectedPush));
					// to - push
					targetPos.setPosition(selectedPush);
					// from - to
					stone.setPosition(selectedMove);
				}

				break;
			}
		}

	}

}
