package SyScIslands;

public class LandFeld extends Feld {
	
	public Insel insel = null;
	
	public void condition() {
		super.condition();
		
		if (insel != null && insel.dorf != null && 
			x == insel.dorf.xPos && y == insel.dorf.yPos) {
			
			setDepiction(findDepict("Dorf"));
		} else 
			setDepiction(findDepict("Land"));
	}
	
	public void action() {
		if (init) {
			// Initialisierungsaktionen
			init = false;
			return;
		}
	}
	
	public void setzeInsel(Insel insel) {
		join(insel);
		this.insel = insel;
		insel.setGroesse(insel.getGroesse()+1);
	}
}
