package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private final int LARGEST_NUMBER = Integer.MAX_VALUE;
	private final int SMALLEST_NUMBER = Integer.MIN_VALUE;
	private int playerNumber;
	
	public MoveCalculator(AIAlgorithm algorithm, int playerNumber) {
		this.algorithm = algorithm;
		this.playerNumber = playerNumber;
	}
	
	
	public int calculate(final GameBoard gameboard, Stone stone, int depth) {
		if(this.algorithm.equals(AIAlgorithm.MINIMAX)) return minimax(gameboard, this.playerNumber, stone, depth);
		return -1;
	}
	
	
	// TODO: fix for when only 2 players are playing
	public int minimax(final GameBoard gameboard, int playerNumber, Stone stone, int depth) {
		final int nextPlayer = playerNumber >= 2 ? 0 : playerNumber + 1;
		final ArrayList<Integer> othersPositions = new ArrayList<Integer>(Arrays.asList(gameboard.getAllStonePositionExcluding(this.playerNumber)));
		if(this.hasGameStopped(gameboard)) {
			if(gameboard.hasPlayerWon(this.playerNumber)) return 2;
			return -2;
		}

		if(depth == 0) {
			return 1;
		}
		
		int result = 0;
		for(Stone playerStone : gameboard.getStones(playerNumber)) {
			for(Integer position : playerStone.getValidMoves(gameboard)) {
				int stoneOldPos = playerStone.getPosition();
				playerStone.setPosition(position);
				
				if(playerNumber == this.playerNumber) {
					result = Math.max(Integer.MIN_VALUE, minimax(gameboard, nextPlayer, playerStone, depth - 1));

					// undo move on local gameboard
					playerStone.setPosition(stoneOldPos);

					continue;
				}
				result = Math.min(Integer.MAX_VALUE, minimax(gameboard, nextPlayer, stone, depth));
				
				// undo move on local gameboard
				playerStone.setPosition(stoneOldPos);
			}
			
			
			return result;
			
		
		}
		
		return 0;
		
	}
	
	public boolean hasGameStopped(final GameBoard gameboard) {
		Stone[] stones = gameboard.getStones(this.playerNumber);
		
		int stoneCount = stones.length;
		for(Stone stone : stones) if(stone.getPosition() == -1) stoneCount--;
		if(stoneCount <= 2) return true;
		
		if(gameboard.hasPlayerWon(0) || gameboard.hasPlayerWon(1) || gameboard.hasPlayerWon(2)) return true;
		
		return false;
	}

	
	public int evaluateGame(final GameBoard gameboard) {
		for(int playerIndex = 0; playerIndex <= 2; playerIndex++) {
			if(gameboard.hasPlayerWon(playerIndex) && playerIndex == this.playerNumber) return 2;
			if(gameboard.hasPlayerWon(playerIndex) && playerIndex != this.playerNumber) return -2;
		}
		
		
		
		return 0;
	}
}
