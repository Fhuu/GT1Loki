package htw.loki;

import java.util.ArrayList;

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
	private Integer[] player0stones = new Integer[4];
	private Integer[] player1stones = new Integer[4];
	private Integer[] player2stones = new Integer[4];
	
	// TODO
	private GameBoard() {
		// Initial player positions
		
		this.generateBoard();
		this.createInitialStonePosition();
	}
	
	public Integer[] getPlayer0stones() {
		return this.player0stones;
	}
	
	public Integer[] getPlayer1stones() {
		return this.player1stones;
	}
	
	public Integer[] getPlayer2stones() {
		return this.player2stones;
	}
	
	public void setPlayer0stones(Integer[] stones) {
		this.player0stones = stones;
	}
	
	public void setPlayer1stones(Integer[] stones) {
		this.player1stones = stones;
	}
	
	public void setPlayer2stones(Integer[] stones) {
		this.player2stones = stones;
	}
	
	public void updateBoard(Integer player, Integer from, Integer to) {
		ArrayList<Integer> newPositions = new ArrayList<Integer>(); 
		if(player == 0){
			Integer[] playerPosition = getPlayer0stones();
			for(Integer pos : playerPosition) {
				if(from == pos) continue;
				newPositions.add(pos);
				newPositions.add(to);
			}
			this.setPlayer0stones(playerPosition);
		}
		if(player == 1){
			Integer[] playerPosition = getPlayer1stones();
			for(Integer pos : playerPosition) {
				if(from == pos) continue;
				newPositions.add(pos);
				newPositions.add(to);
			}
			this.setPlayer1stones(playerPosition);
		}
		if(player == 2){
			Integer[] playerPosition = getPlayer2stones();
			for(Integer pos : playerPosition) {
				if(from == pos) continue;
				newPositions.add(pos);
				newPositions.add(to);
			}
			this.setPlayer2stones(playerPosition);
		}
		
	}
	
	public void createInitialStonePosition() {
		setPlayer0stones(new Integer[] { 0, 1, 2, 3 });
		setPlayer1stones(new Integer[] { 16, 25, 26, 27 });
		setPlayer2stones(new Integer[] { 24, 33, 34, 35 });
	}
	
	public static GameBoard getInstance() {
		if(gameBoard == null) gameBoard = new GameBoard();
		return gameBoard;
	}
	
	
	
	
	// returns all the neighbouring positions of a position
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
	

}
