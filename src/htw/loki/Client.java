package htw.loki;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

import lenz.htw.loki.Move;
import lenz.htw.loki.net.NetworkClient;

public class Client extends Thread {

	private NetworkClient client;
	private String hostname;
	private int clientNumber;
	private GameBoard gameboard;
	private MoveCalculator moveCalculator;
	private Integer timeLimit;

	/**
	 * hostname, port, teamname, image path
	 * 
	 * @throws IOException
	 */
	public Client(String hostname, int clientNumber) {
		this.hostname = hostname;
		this.clientNumber = clientNumber;
		this.gameboard = new GameBoard();
//		this.client = new NetworkClient(this.hostname, "player" + clientNumber, ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
		this.timeLimit = 7;
		this.moveCalculator = new MoveCalculator(AIAlgorithm.MINIMAX, 0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.clientNumber + " connecting to " + this.hostname + " with image from .\\image\\image" + (this.clientNumber + 1) + ".png");
//		try {

			this.move();
			
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void move() {
		long time = System.currentTimeMillis();
		final int playerNumber = this.clientNumber;
		final Stone[] stones = this.gameboard.getStones(playerNumber);
		
		ArrayList<FutureTask<Evaluation>> tasks = new ArrayList<FutureTask<Evaluation>>();
	
		for(Stone stone : stones) {
			Integer[] moves = stone.getValidMoves(gameboard);
			for(Integer move : moves) {
//				int oldPosition = stone.getPosition();
				GameBoard gameBoardClone = this.gameboard.clone();
				
//				stone.setPosition(move);
//				Integer evaluationValue = moveCalculator.calculate(gameboard, 11);
//				stone.setPosition(oldPosition);
				
				Callable<Evaluation> calculator = new Callable<Evaluation>() {

					@Override
					public Evaluation call() throws Exception {
						// TODO Auto-generated method stub
						Stone clonedStone = gameBoardClone.getStoneFrom(stone.getPosition());
						
						clonedStone.setPosition(move);
						Integer evaluationValue = moveCalculator.calculate(gameBoardClone, 11);
						
						Evaluation evaluation = new Evaluation(stone, move, evaluationValue);
						return evaluation;
					}
					
				};
				
				// create task for the callable
				FutureTask<Evaluation> task = new FutureTask<Evaluation>(calculator);
				tasks.add(task);
				
				// Run task in thread
				(new Thread(task)).start();
			}
		}
		
		
		for(FutureTask<Evaluation> task : tasks) {
			try {
				Evaluation evaluation = task.get();
				System.out.println(evaluation.toString());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Player " + playerNumber + " took " + (System.currentTimeMillis() - time) + " ms to finish processing valid moves");
	}

	protected class Evaluation {
		protected Evaluation(Stone stone, Integer targetPosition, Integer evaluation) {
			this.stone = stone;
			this.targetPosition = targetPosition;
			this.evaluation = evaluation;
		}
		
		protected Stone stone;
		protected Integer targetPosition;
		protected Integer evaluation;
		
		public String toString() {
			return this.stone.getPosition() + ", " + this.targetPosition + ", " + this.evaluation;
		}
	}
}
