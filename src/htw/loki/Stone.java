package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;

public class Stone {
	
	private Integer position;
	private Integer playerNumber;
	
	public Stone(int stonePosition, int playerNumber) {
		this.setPosition(stonePosition);
		this.playerNumber = playerNumber;
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
//		System.out.println(this.position);
		Integer[] positions = new Integer[4];
		for(int index = 0; index < positions.length; index++) {
			positions[index] = stones[index].getPosition();
		}
		Arrays.sort(positions);
		ArrayList<Integer> positionList = new ArrayList<Integer>(Arrays.asList(positions));
		
		ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		for(int position : positionList) {
			if(position == this.position) continue;
			Integer[] possibleMoves = gameboard.getNeighbouringPosition(position);
			for(int possibleMove : possibleMoves) {
				if( possibleMove > -1 && !allPossibleMoves.contains(possibleMove) && !positionList.contains(possibleMove)) allPossibleMoves.add(possibleMove);
			}
		}
		
		// filter only valid move
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for(int index = 0; index < allPossibleMoves.size(); index++) {
			System.out.println("Checking " + allPossibleMoves.get(index));
			for(Integer position : positions) {
				System.out.println("Position " + position);
//				 if self, next
				if(this.position == position) {
					System.out.println("Position is self");
					continue;
				};
				
				// get neighbours of not self position
				final ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(gameboard.getNeighbouringPosition(position)));
				
//				if neighbours contains possibleMove, possible move is valid, then next
				if(neighbours.contains(allPossibleMoves.get(index))) {
					System.out.println("Neighbour contains " + allPossibleMoves.get(index) + " and is valid");
					System.out.println("Valid move");
					continue;
				}
				System.out.println("neighbours does not contain " + allPossibleMoves.get(index));
				
				ArrayList<Integer> checkPosition = positionList;
				checkPosition.remove(this.position);
				for(Integer neighbour : neighbours) {
					if(neighbour == -1) continue;
					System.out.println("Neighbour number " + neighbour);
					if(neighbour == this.position) continue;
					
					if(checkPosition.contains(neighbour)) {
						System.out.println("stone positions contains neighbour: " + neighbour);
						System.out.println("Valid move");
						if(!validMoves.contains(allPossibleMoves.get(index))) validMoves.add(allPossibleMoves.get(index));
						break;
					};
					System.out.println("Position " + position + " does not contain " + neighbour);
					System.out.println("Invalid move detected\n\n");
				}				
			}
		}
		
		for(Integer validMove : validMoves) {
			System.out.println("valid moves for stone with position "
					+ this.position + ", player number "  + this.playerNumber + " is " + validMove);			
		}
		
		
		return allPossibleMoves.toArray(new Integer[allPossibleMoves.size()]);
	}
	
	
	public boolean hasNeighbour(GameBoard gameboard, Stone[] stones) {
		final Integer[] neighbouringPositions = gameboard.getNeighbouringPosition(this.position);
		for(Stone stone : stones) {
			if (this == stone) continue;
			if((new ArrayList<Integer>(Arrays.asList(neighbouringPositions))).contains(stone.getPosition())) return true;
		}
		return false;
	}
	
	public static Integer[] createInitialStonePosition(int playerNumber) {
		switch(playerNumber) {
			case 0 : return new Integer[] {1, 5, 3, 7};
			case 1 : return new Integer[] {16, 25, 26, 27};
			case 2 : return new Integer[] {24, 33, 34, 35};
			default : return null;
		}
	}
}
