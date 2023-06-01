package htw.loki;

public class Evaluation {
	
	
	protected Stone stone;
	protected Integer targetPosition;
	protected Integer evaluation;
	
	protected Evaluation(Stone stone, Integer targetPosition, Integer evaluation) {
		this.stone = stone;
		this.targetPosition = targetPosition;
		this.evaluation = evaluation;
	}
	
	public String toString() {
		return this.stone.getPosition() + ", " + this.targetPosition + ", " + this.evaluation;
	}
	
	public String createIndex() {
		if(stone != null && targetPosition != null)return this.stone.getPosition() + ":" + this.targetPosition;
		return "invalid";
	}
}