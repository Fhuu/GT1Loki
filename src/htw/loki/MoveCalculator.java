package htw.loki;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private int playerNumber;
	
	public MoveCalculator(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
	// TODO: fix for when only 2 players are playing
	public int minimax(final GameBoard gameboard, int playerNumber, int depth, Integer alpha, Integer beta) {
		final int nextPlayer = playerNumber >= 2 ? 0 : playerNumber + 1;
		if(!gameboard.getActivePlayers().contains(playerNumber)) return minimax(gameboard, playerNumber >= 2 ? 0 : playerNumber + 1, depth, alpha, beta);
		
		for(Stone playerStone : gameboard.getStones(playerNumber)) {
			if(playerStone.getPosition() == -1) continue;
			for(Integer position : playerStone.getValidMoves(gameboard)) {
				if(gameboard.getStoneFrom(position) != null && playerNumber != this.playerNumber) return -1 - depth;
			}
		}
		
		if(this.hasGameStopped(gameboard)) {
			if(gameboard.hasPlayerWon(this.playerNumber)) return 2 + depth;
			return -2;
		}

		if(depth == 0) {
			
			return 0;
		}
		
		int result = playerNumber == this.playerNumber ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for(Stone playerStone : gameboard.getStones(playerNumber)) {
			if(playerStone.getPosition() == -1) continue;
			for(Integer position : playerStone.getValidMoves(gameboard)) {
				if(gameboard.getStoneFrom(position) != null && playerNumber == this.playerNumber) return 1 + depth;
				
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
