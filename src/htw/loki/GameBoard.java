package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * DOKU:
 * Considered ArrayList for the linked neighbouring position because some position has only 1 or 2 neighbours
 * Decided to use two dimentional array because array has better performance compared to arraylist
 * becaue arraylist.get(index) is a function call and it takes more performance comparing to referencing the index of an array
 * v
 * TODO 
 * - Generate board neighbouring positions either in constructor or in a separated function
 * - preferably constructor
 * @author dirob
 *
 */
public class GameBoard {

	private static GameBoard gameBoard;
	private Integer[][] neighbours = new Integer[36][3];
	private Stone[][] stones;
	// TODO
	private GameBoard() {
		// Initial player positions
		
		this.generateBoard();
		
		this.stones = new Stone[3][4];
		this.stones[0][0] = new Stone(0, 0);
		this.stones[0][1] = new Stone(0, 1);
		this.stones[0][2] = new Stone(0, 2);
		this.stones[0][3] = new Stone(0, 3);

		this.stones[1][0] = new Stone(1, 16);
		this.stones[1][1] = new Stone(1, 25);
		this.stones[1][2] = new Stone(1, 26);
		this.stones[1][3] = new Stone(1, 27);

		this.stones[2][0] = new Stone(2, 24);
		this.stones[2][1] = new Stone(2, 33);
		this.stones[2][2] = new Stone(2, 34);
		this.stones[2][3] = new Stone(2, 35);
	}

	
	public static GameBoard getInstance() {
		if(gameBoard == null) gameBoard = new GameBoard();
		return gameBoard;
	}
	
	
	public Stone[][] getAllStones() {
		return this.stones;
	}
	
	
	public Integer[] getAllStonePositionExcluding(int playerNumber) {
		Integer[] positions = new Integer[8];
		
		int indexPosition = 0;
		for(int index = 0; index <= 2; index++) {
			if(index == playerNumber) continue;
			for(int stoneIndex = 0; stoneIndex < 4; stoneIndex++) {
				positions[indexPosition] = this.stones[index][stoneIndex].getPosition(); 
				indexPosition++;
			}
		}
		
		return positions;
	}
	
	
	public Stone[] getStones(int playerNumber) {
		return this.stones[playerNumber];
	}
	
	
	public Stone getStoneFrom(int position) {
		for(Stone[] playerStone : this.stones) for(Stone stone : playerStone) if(stone.getPosition() == position) return stone;
		return null;
	}
	
	
	/**
	 * 
	 * @param index boardPosition
	 * @return Array of neighbour of boardPositiion
	 */
	public Integer[] getNeighbouringPosition(Integer index) {
		return this.neighbours[index];
	}
	
	
	private void generateBoard() {
		this.neighbours[0][0] = 2;
		this.neighbours[0][1] = -1;
		this.neighbours[0][2] = -1;
		
		this.neighbours[1][0] = 2;
		this.neighbours[1][1] = 5;
		this.neighbours[1][2] = -1;
		
		this.neighbours[2][0] = 1;
		this.neighbours[2][1] = 3;
		this.neighbours[2][2] = -1;
		
		this.neighbours[3][0] = 2;
		this.neighbours[3][1] = 7;
		this.neighbours[3][2] = -1;
				
		this.neighbours[4][0] = 10;
		this.neighbours[4][1] = 5;
		this.neighbours[4][2] = -1;
					
		this.neighbours[5][0] = 1;
		this.neighbours[5][1] = 4;
		this.neighbours[5][2] = 6;
				
		this.neighbours[6][0] = 5;
		this.neighbours[6][1] = 7;
		this.neighbours[6][2] = 12;
				
		this.neighbours[7][0] = 3;
		this.neighbours[7][1] = 6;
		this.neighbours[7][2] = 8;
				
		this.neighbours[8][0] = 7;
		this.neighbours[8][1] = 14;
		this.neighbours[8][2] = -1;
				
		this.neighbours[9][0] = 10;
		this.neighbours[9][1] = 17;
		this.neighbours[9][2] = -1;
				
		this.neighbours[10][0] = 4;
		this.neighbours[10][1] = 9;
		this.neighbours[10][2] = 11;
				
		this.neighbours[11][0] = 10;
		this.neighbours[11][1] = 12;
		this.neighbours[11][2] = 19;
		
		this.neighbours[12][0] = 6;
		this.neighbours[12][1] = 11;
		this.neighbours[12][2] = 13;
		
		this.neighbours[13][0] = 12;
		this.neighbours[13][1] = 14;
		this.neighbours[13][2] = 21;
		
		this.neighbours[14][0] = 8;
		this.neighbours[14][1] = 13;
		this.neighbours[14][2] = 15;
		
		this.neighbours[15][0] = 14;
		this.neighbours[15][1] = 23;
		this.neighbours[15][2] = -1;
		
		this.neighbours[16][0] = 17;
		this.neighbours[16][1] = 26;
		this.neighbours[16][2] = -1;
		
		this.neighbours[17][0] = 9;
		this.neighbours[17][1] = 16;
		this.neighbours[17][2] = 18;
		
		this.neighbours[18][0] = 17;
		this.neighbours[18][1] = 19;
		this.neighbours[18][2] = 28;
		
		this.neighbours[19][0] = 11;
		this.neighbours[19][1] = 18;
		this.neighbours[19][2] = 20;
		
		this.neighbours[20][0] = 19;
		this.neighbours[20][1] = 21;
		this.neighbours[20][2] = 30;
		
		this.neighbours[21][0] = 13;
		this.neighbours[21][1] = 20;
		this.neighbours[21][2] = 22;
		
		this.neighbours[22][0] = 21;
		this.neighbours[22][1] = 23;
		this.neighbours[22][2] = 32;
		
		this.neighbours[23][0] = 15;
		this.neighbours[23][1] = 22;
		this.neighbours[23][2] = 24;
		
		this.neighbours[24][0] = 23;
		this.neighbours[24][1] = 34;
		this.neighbours[24][2] = -1;
		
		this.neighbours[25][0] = 26;
		this.neighbours[25][1] = -1;
		this.neighbours[25][2] = -1;
		
		this.neighbours[26][0] = 16;
		this.neighbours[26][1] = 25;
		this.neighbours[26][2] = 27;
		
		this.neighbours[27][0] = 26;
		this.neighbours[27][1] = 28;
		this.neighbours[27][2] = -1;
		
		this.neighbours[28][0] = 18;
		this.neighbours[28][1] = 27;
		this.neighbours[28][2] = 29;
		
		this.neighbours[29][0] = 28;
		this.neighbours[29][1] = 30;
		this.neighbours[29][2] = -1;
		
		this.neighbours[30][0] = 20;
		this.neighbours[30][1] = 29;
		this.neighbours[30][2] = 31;
		
		this.neighbours[31][0] = 30;
		this.neighbours[31][1] = 32;
		this.neighbours[31][2] = -1;
		
		this.neighbours[32][0] = 22;
		this.neighbours[32][1] = 31;
		this.neighbours[32][2] = 33;
		
		this.neighbours[33][0] = 32;
		this.neighbours[33][1] = 34;
		this.neighbours[33][2] = -1;
		
		this.neighbours[34][0] = 24;
		this.neighbours[34][1] = 33;
		this.neighbours[34][2] = 35;
		
		this.neighbours[35][0] = 34;
		this.neighbours[35][1] = -1;
		this.neighbours[35][2] = -1;
	}
	
	
	public boolean hasPlayerWon(int playerNumber) {
        Stone[] stones = this.stones[playerNumber];
        
        ArrayList<Integer> winningPosition = new ArrayList<Integer>(Arrays.asList(playerNumber == 0 ? new Integer[] {25,26,27,28,29,30,31,32,33,34,35} : playerNumber == 1 ? new Integer[] {0,2,3,7,8,14,15,23,24,34,35} : new Integer[] {0,2,1,5,4,10,9,17,16,26,25}));
        
        for(Stone stone : stones) if(!winningPosition.contains(stone.getPosition())) return false;
        
        return true;
    }
}
