package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stone {

	private Integer position;
	
	public Stone(int stonePosition) {
		this.setPosition(stonePosition);
	}
	
	public Integer getFrom() {
		return from;
	}

	public Integer getTo() {
		return to;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}


	public void moveStone(Integer to) {
		setFrom(this.position);
		setTo(to);
		setPosition(to);
	}
	
	public Integer[] getValidMoves(int clientNumber) {
		GameBoard gameboard = GameBoard.getInstance();
		final Stone[] stones = gameboard.getStones(clientNumber);
  
  }
	public Integer[] getValidMoves(GameBoard gameboard, Stone[] stones) {
		Integer[] positions = new Integer[4];
		for (int index = 0; index < positions.length; index++) {
			positions[index] = stones[index].getPosition();
		}
		Arrays.sort(positions);
		ArrayList<Integer> positionList = new ArrayList<Integer>(Arrays.asList(positions));

		ArrayList<Integer> allPossibleMoves = new ArrayList<>();
		for (int position : positionList) {
			if (position == this.position)
				continue;
			Integer[] possibleMoves = gameboard.getNeighbouringPosition(position);
			for (int possibleMove : possibleMoves) {
				if (possibleMove > -1 && !allPossibleMoves.contains(possibleMove)
						&& !positionList.contains(possibleMove))
					allPossibleMoves.add(possibleMove);
			}
		}

		// filter only valid move
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (Integer move : allPossibleMoves) {
			if (this.isValidMove(move, gameboard, stones))
				validMoves.add(move);
		}
    
		return validMoves.toArray(new Integer[validMoves.size()]);
	}

	public boolean isValidMove(Integer targetPosition, GameBoard gameboard, Stone[] stones) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (Stone stone : stones) {
			if (this == stone)
				continue;
			positions.add(stone.position);
		}

		for (Integer position : positions) {
			final ArrayList<Integer> neighbouringPositions = new ArrayList<Integer>(
					Arrays.asList(gameboard.getNeighbouringPosition(position)));
			boolean isValid = false;
			for (Integer neighbourPosition : neighbouringPositions) {
				if (positions.contains(neighbourPosition)) {
					isValid = true;
				}
			}

			if (neighbouringPositions.contains(targetPosition))
				isValid = true;
			if (isValid)
				continue;
			return false;
		}

		return true;
	}
  
	public static Stone[] updateStonePosition(Stone[] stones, Integer from, Integer To) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for(Stone stone : stones) {
			if(stone.position == from) {
				stone.setPosition(To);
				break;
			}
		}
		for(Stone stone : stones) {
			positions.add(stone.position);
		}
		System.out.println("new array position: " + positions.toString());
		return stones;
		
	}
  
}
