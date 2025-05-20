
public class Player {

	private int score;
	private double power;
	private int numCells = 1;
	private Entity observing = null;
	
	public Entity getObserving() {
		return observing;
	}

	public void setObserving(Entity observing) {
		this.observing = observing;
	}

	public Player()
	{
		
		this.score = 15;
		this.power = 100;
		this.numCells = 1;
	}
	
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
		if(power > 500)
			power = 500;
		if(power < 0)
		{
			power = 0;
		}
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}



	public int getNumCells() {
		return numCells;
	}

	public void setNumCells(int numCells) {
		this.numCells = numCells;
	}
	
}
