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
		this.gameboard = GameBoard.getInstance();
	}
	
//	public void setActivePlayer(Integer player) {
//		this.activePlayer = player;
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			
			while(true) {
				Move receivedMove = this.client.receiveMove();
				while(receivedMove != null) {
	            	break;
					
				}
				this.move();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void move() {
		long time = System.currentTimeMillis();
		final int playerNumber = this.client.getMyPlayerNumber();
		Stone[] stones = this.gameboard.getStones(playerNumber);
		ArrayList<Integer> positions = new ArrayList<Integer>();
		Stone movedStone = null;
		for(Stone stone : stones) {
			Integer[] moves = stone.getValidMoves();
			if(moves.length == 0) continue;
			positions.add(stone.getPosition());
			String stringResult = "";
			for(Integer move : moves) {
				stringResult = stringResult + ", " + move;
			}
			//System.out.println("Player " + playerNumber + " can move stone " + stone.getPosition() + " to " + stringResult);
		}
		
		Integer movedStonePosition = positions.get(new Random().nextInt(positions.size()));
		
		for (Stone stone : stones) {
			if(stone.getPosition() == movedStonePosition) {
				Integer[] possibleMoves = stone.getValidMoves();
				Integer selectedMove = possibleMoves[(new Random()).nextInt(possibleMoves.length)];
				System.out.println("Player " + playerNumber + " Moving Stone " + stone.getPosition() + " to " + selectedMove );
				
				if(gameboard.isTargetEmpty(playerNumber, movedStonePosition) == true) {
					this.client.sendMove(new Move(stone.getPosition(),selectedMove, 0 ));
					stone.setPosition(selectedMove);
					
				} 
				
				if(gameboard.isTargetEmpty(playerNumber, movedStonePosition) == false) {
					Integer[] possiblePush = gameboard.getNeighbouringPosition(selectedMove);
					for(Integer push : possiblePush) {
						if(gameboard.isTargetEmpty(playerNumber, push) == true) {
							this.client.sendMove(new Move(stone.getPosition(),selectedMove, push ));
							gameboard.updateOtherPlayer(selectedMove, push);
							stone.setPosition(selectedMove);
						}
					}
				}
				
				break;
			}
		}
				
		
	}

}
