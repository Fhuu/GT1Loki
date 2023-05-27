package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stone {

	private Integer position;
	
	public Stone(int stonePosition) {
		this.setPosition(stonePosition);
	}


	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
	public Integer[] getEmptyNeighbors() {
		
		return null;
	}



	public Integer[] getValidMoves(int playerNumber) {
		final GameBoard gameboard = GameBoard.getInstance();
		final Stone[] stones = gameboard.getStones(playerNumber);
		final Integer[] positions = new Integer[4];
		
		for (int index = 0; index < positions.length; index++) positions[index] = stones[index].getPosition();
		Arrays.sort(positions);
		final ArrayList<Integer> positionList = new ArrayList<Integer>(Arrays.asList(positions));

		final ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		for (int position : positionList) {
			if (position == this.position) continue;
			
			Integer[] possibleMoves = gameboard.getNeighbouringPosition(position);
			for (int possibleMove : possibleMoves) if (possibleMove > -1 && !allPossibleMoves.contains(possibleMove) && !positionList.contains(possibleMove)) allPossibleMoves.add(possibleMove);
		}

		// filter only valid move
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (Integer move : allPossibleMoves) if (this.isValidMove(move, gameboard, stones)) validMoves.add(move);
    
		return validMoves.toArray(new Integer[validMoves.size()]);
	}

	public boolean isValidMove(Integer targetPosition, GameBoard gameboard, Stone[] stones) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (Stone stone : stones) {
			if (this == stone) continue;
			positions.add(stone.position);
		}

		for (Integer position : positions) {
			final ArrayList<Integer> neighbouringPositions = new ArrayList<Integer>(Arrays.asList(gameboard.getNeighbouringPosition(position)));
			boolean isValid = false;			
			
			for (Integer neighbourPosition : neighbouringPositions) if (positions.contains(neighbourPosition)) isValid = true; 
			
			if (neighbouringPositions.contains(targetPosition)) isValid = true;
			if (isValid) continue;
			
			return false;
		}

		return true;
	}
  

  
}
