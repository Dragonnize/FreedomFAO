
public class Cordonates {

	double valueX;
	double valueY;
	double valueZ;
	int NPT;
	
	public Cordonates(int nPT, double currentCordX, double currentCordY, double currentCordZ) {
		this.setValueNPT(nPT);
		this.setValueX(currentCordX);
		this.setValueZ(currentCordZ);
		this.setValueY(currentCordY);
	}

	public void setValueX(double currentCordX) { this.valueX = currentCordX; }
	public void setValueY(double currentCordY) { this.valueY = currentCordY; }
	public void setValueZ(double currentCordZ) { this.valueZ = currentCordZ; }
	
	public double getValueX() { return this.valueX; }
	public double getValueY() {	return this.valueY;	}
	public double getValueZ() { return this.valueZ; }
	
	public void setValueNPT(int NPT) { this.NPT = NPT; }
	public int getValueNPT() { return this.NPT;	}
}
