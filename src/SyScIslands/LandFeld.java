package SyScIslands;

public class LandFeld extends Feld {
	
	public Insel insel = null;
	
	public void condition() {
		super.condition();
	}
	
	public void action() {
		if (init) {
			// Initialisierungsaktionen
			init = false;
			return;
		}
	}
	
}
