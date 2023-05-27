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
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
		try {
			this.client = new NetworkClient(this.hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			this.move();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void move() {
		long time = System.currentTimeMillis();
		final int playerNumber = this.client.getMyPlayerNumber();
		final Stone[] stones = this.gameboard.getStones(playerNumber);
		for(Stone stone : stones) {
			Integer[] moves = stone.getValidMoves(this.gameboard);
			String stringResult = "";
			for(Integer move : moves) {
				stringResult = stringResult + ", " + move;
			}
			System.out.println("Player " + playerNumber + " can move stone " + stone.getPosition() + " to " + moves.toString());
		}
		System.out.println("Player " + this.client.getMyPlayerNumber() + " took " + (System.currentTimeMillis() - time) + " ms to finish processing valid moves");
	}

}
