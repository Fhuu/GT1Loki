package htw.loki;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private int playerNumber;
	
	public MoveCalculator(AIAlgorithm algorithm, int playerNumber) {
		this.algorithm = algorithm;
		this.playerNumber = playerNumber;
	}
	
	
	public int calculate(final GameBoard gameboard, int depth) {
		if(this.algorithm.equals(AIAlgorithm.MINIMAX)) return minimax(gameboard, this.playerNumber, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return -1;
	}
	
	
	// TODO: fix for when only 2 players are playing
	public int minimax(final GameBoard gameboard, int playerNumber, int depth, Integer alpha, Integer beta) {
		final int nextPlayer = playerNumber >= 2 ? 0 : playerNumber + 1;
		
		if(this.hasGameStopped(gameboard)) {
			if(gameboard.hasPlayerWon(this.playerNumber)) return 2;
			return -2;
		}

		if(depth == 0) {
			return 1;
		}
		
		int result = playerNumber == this.playerNumber ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for(Stone playerStone : gameboard.getStones(playerNumber)) {
			for(Integer position : playerStone.getValidMoves(gameboard)) {
				int stoneOldPos = playerStone.getPosition();
				playerStone.setPosition(position);
				
				if(playerNumber == this.playerNumber) {
					Integer evaluation = minimax(gameboard, nextPlayer, depth - 1, alpha, beta);
					result = Math.max(result, evaluation);
					alpha = Math.max(alpha, evaluation);
						
					// undo move on local gameboard
					playerStone.setPosition(stoneOldPos);
					
					if(beta <= alpha) break;

					continue;
				}
				Integer evaluation = minimax(gameboard, nextPlayer, depth - 1, alpha, beta);
				result = Math.min(result, evaluation);
				beta = Math.min(result, evaluation);
				
				// undo move on local gameboard
				playerStone.setPosition(stoneOldPos);
				
				if(beta <= alpha) break;
			}
		}
		
		return result;
		
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
