package htw.loki;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
	final String[] names = new String[] {"alpha", "beta", "pruning"};


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
			this.client = new NetworkClient(this.hostname, names[clientNumber], ImageIO.read(new File(".\\image\\image" + (this.clientNumber + 1) + ".png")));
			this.moveCalculator = new MoveCalculator(AIAlgorithm.MINIMAX, this.client.getMyPlayerNumber());
			this.move();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.moveCalculator = new MoveCalculator(AIAlgorithm.MINIMAX, this.client.getMyPlayerNumber());
		
	}
	
	public void move() {
		Move receivedMove;
		while(true) {
			while((receivedMove = this.client.receiveMove()) != null) {
				if(receivedMove.push != 255){
					Stone pushedStone = this.gameboard.getStoneFrom(receivedMove.to);
					pushedStone.setPosition(receivedMove.push);
				}
				
				Stone movedStone = this.gameboard.getStoneFrom(receivedMove.from);
				movedStone.setPosition(receivedMove.to);
				
				this.gameboard.updateGameboard();
				
				System.out.println(this.gameboard.toString());
				
			}
			
			Evaluation bestMove = this.getBestMove();
			Stone target = gameboard.getStoneFrom(bestMove.targetPosition);
			
			int push = 255;
			if(target != null) {
				Integer[] emptyNeighbours = target.getAllEmptyNeighbour(gameboard);
				// Need minimax here
				push = emptyNeighbours[(new Random()).nextInt(emptyNeighbours.length)];
			}
			
			this.client.sendMove(new Move(bestMove.stone.getPosition(), bestMove.targetPosition, push));
		}
	}
	
	public Evaluation getBestMove() {
		
		long time = System.currentTimeMillis();
		final int playerNumber = this.client.getMyPlayerNumber();
		final Stone[] stones = this.gameboard.getStones(playerNumber);
		
		ArrayList<FutureTask<Evaluation>> tasks = new ArrayList<FutureTask<Evaluation>>();
	
		for(Stone stone : stones) {
			if(stone.getPosition() == -1) continue;
			Integer[] moves = stone.getValidMoves(gameboard);
			for(Integer move : moves) {
				GameBoard gameBoardClone = this.gameboard.clone();
				
				Callable<Evaluation> calculator = new Callable<Evaluation>() {

					@Override
					public Evaluation call() throws Exception {
						Stone clonedStone = gameBoardClone.getStoneFrom(stone.getPosition());
						
						clonedStone.setPosition(move);
						Integer evaluationValue = moveCalculator.calculate(gameBoardClone, 9);
						
						
						
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
		
		Evaluation bestMove = new Evaluation(null, null, Integer.MIN_VALUE);
		for(FutureTask<Evaluation> task : tasks) {
			try {
				Evaluation evaluation = task.get();
				Stone targetStone;
				if((targetStone = this.gameboard.getStoneFrom(evaluation.targetPosition)) != null) {
					// if(!this.gameboard.getActivePlayers().contains(targetStone.getPlayerNumber())) continue;
					Integer[] emptyNeighbours = targetStone.getAllEmptyNeighbour(this.gameboard);
					if(emptyNeighbours.length == 0) continue;
				}
				if(bestMove.evaluation == evaluation.evaluation) bestMove = (new Random()).nextInt(2) == 0 ? bestMove : evaluation;
				if(bestMove.evaluation < evaluation.evaluation) bestMove = evaluation;
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Player " + playerNumber + " took " + (System.currentTimeMillis() - time) + " ms to finish processing valid moves");
		return bestMove;
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
