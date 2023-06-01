package htw.loki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class QLearning {

	private HashMap<String, HashMap<String, Double>> qTable;
	private GameBoard gameboard;
	private Integer playerNumber;
	final private double EXPLORATION_RATE = 0.2;
	final private double LEARNING_RATE = 0.1;
	final private double DISCOUNT_FACTOR = 0.9;
	
	public QLearning(HashMap<String, HashMap<String, Double>> qTable, int playerNumber) {
		this.qTable = qTable;
		this.playerNumber = playerNumber;
		this.gameboard = new GameBoard();
	}
	
	public HashMap<String, HashMap<String, Double>> learn() {
		int currentPlayer = this.playerNumber;
		
		// While no winner
		while(!this.gameboard.hasPlayerWon(0) || !this.gameboard.hasPlayerWon(1) || !this.gameboard.hasPlayerWon(2)) {
			Evaluation nextMove = new Evaluation(null, null, null);
			
			if(currentPlayer == this.playerNumber) {
				nextMove = getNextMove();
			}
			if(currentPlayer != this.playerNumber) {
				nextMove = runMinimax(currentPlayer);
			}
			
			
			// Move to nextMove according to nextMove Evaluation
			String currentStateString = this.gameboard.createStringIndex();
			
			// FROM HERE IS NEW STATE
			int reward = 0;			
			int push = 255;
			Stone target = gameboard.getStoneFrom(nextMove.targetPosition);
			if(target != null) {
				Integer[] emptyNeighbours = target.getAllEmptyNeighbour(gameboard);
				// Need minimax here
				push = emptyNeighbours[(new Random()).nextInt(emptyNeighbours.length)];
				target.setPosition(push);
				this.gameboard.updateGameboard();
				
				// reward is 1 because it kills other stone
				reward = 1;
			}
			
			Stone stone = this.gameboard.getStoneFrom(nextMove.stone.getPosition());
			stone.setPosition(nextMove.targetPosition);
			
			for(Integer player : this.gameboard.getActivePlayers()) {
				if(this.gameboard.hasPlayerWon(player)) {
					reward = -2;
					break;
				}
				if(this.gameboard.hasPlayerWon(player) && this.playerNumber == player) {
					reward = 2;
					break;
				}
			}

			this.updateQValue(currentStateString, nextMove.createIndex(), reward, this.gameboard.createStringIndex());
			System.out.println(currentStateString);
			currentPlayer = currentPlayer >= 2 ? 0 : currentPlayer + 1; 
		}
		
		return this.qTable;
	}
	
	
	private Evaluation runMinimax(int playerNumber) {
		MoveCalculator moveCalculator = new MoveCalculator(playerNumber);
		
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
						Double evaluationValue = (double) moveCalculator.minimax(gameBoardClone, playerNumber, 9, Integer.MIN_VALUE, Integer.MAX_VALUE);
						
						
						
						Evaluation evaluation = new Evaluation(stone, move, (int) (evaluationValue * 1000));
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
		
		
		return bestMove;
	}
	
	
	private Evaluation getNextMove() {
		Stone[] stones  = this.gameboard.getStones(this.playerNumber);
		
		if(Math.random() < this.EXPLORATION_RATE) {
			Stone chosenStone = stones[(new Random()).nextInt(4)];
			Integer[] possibleMoves;
			
			while((possibleMoves = chosenStone.getValidMoves(this.gameboard)).length == 0) {
				int target  = (new Random()).nextInt(5);
				chosenStone = stones[target];
			}
			
			int target = possibleMoves[(new Random()).nextInt(possibleMoves.length)];
			return new Evaluation(chosenStone, target, null);
		}
		
		
		// get avail
		Evaluation evaluation = new Evaluation(null, null, Integer.MIN_VALUE);
		for(Stone stone : stones) {
			Integer[] possibleMoves = stone.getValidMoves(this.gameboard);
			for(Integer possibleMove : possibleMoves) {
				double qValue = getQValue(this.gameboard.createStringIndex(), stone.getPosition() + ":" + possibleMove);
				if(qValue > evaluation.evaluation) {
					evaluation.stone = stone;
					evaluation.targetPosition = possibleMove;
					evaluation.evaluation = (int) qValue * 1000;
				}
			}
		}
		
		
		
		return evaluation;
	}

	
	private void updateQValue(String stateString, String actionString, int reward, String nextStateString) {
		double currentQValue = this.getQValue(stateString, actionString);
		double maxNextQValue = this.getMaxQValue(nextStateString);
        double newQValue = (1 - this.LEARNING_RATE) * currentQValue + this.LEARNING_RATE * (reward + this.DISCOUNT_FACTOR * maxNextQValue);
        
        this.qTable.get(stateString).put(actionString, newQValue);
	}
	
	
	private Double getQValue(String stateString, String actionString) {
		this.qTable.putIfAbsent(stateString, new HashMap<String, Double>());
		return this.qTable.get(stateString).getOrDefault(actionString, 0.0);
	}
	
	private double getMaxQValue(String stateString) {
        if (!this.qTable.containsKey(stateString)) {
            return 0.0;
        }
        return this.qTable.get(stateString).values().stream().max(Double::compareTo).orElse(0.0);
    }
}
