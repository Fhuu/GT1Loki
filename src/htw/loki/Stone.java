package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	
	public Integer[] getNeighbours() {
		return new Integer[] {};
	}
	
	
	public Integer[] getAllPossibleMoves(GameBoard gameboard, Stone[] stones) {
		Integer[] positions = new Integer[4];
		for(int index = 0; index < positions.length; index++) {
			positions[index] = stones[index].getPosition();
		}
		Arrays.sort(positions);
		
		ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		for(int position : positions) {
			if(position == this.position) continue;
			Integer[] possibleMoves = gameboard.getPossibleNextPositions(position);
			for(int possibleMove : possibleMoves) {
				if( possibleMove > -1 && !allPossibleMoves.contains(possibleMove) && !(new ArrayList<Integer>(Arrays.asList(positions)).contains(possibleMove))) allPossibleMoves.add(possibleMove);
			}
		}
		
		return allPossibleMoves.toArray(new Integer[allPossibleMoves.size()]);
	}
	
	
	public boolean hasNeighbour(GameBoard gameboard, Stone[] stones) {
		return true;
	}
	
	public static Integer[] createInitialStonePosition(int playerNumber) {
		switch(playerNumber) {
			case 0 : return new Integer[] {0, 1, 2, 3};
			case 1 : return new Integer[] {16, 25, 26, 27};
			case 2 : return new Integer[] {24, 33, 34, 35};
			default : return null;
		}
	}
}
