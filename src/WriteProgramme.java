import java.util.ArrayList;

public class WriteProgramme {

	public double positionX;
	public double positionY;
	public double positionZ;
	
	String displayProg;
	
	ArrayList prog = new ArrayList();
	
	WriteProgramme(Object data) {
		for(int i = 0; i < 15; i++) {
			this.prog.add("N" + i);
			this.ProgrammeDisplay();
		}
	}
	
	public void ProgrammeDisplay() {
		this.displayProg += "";
	}
	
}
