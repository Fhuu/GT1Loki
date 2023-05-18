package htw.loki;

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
	
	
	public Integer getNeighbour() {
		return 1;
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
