package SyScIslands;

import eawag.grid.Depiction;

public class WasserFeld extends Feld {
	
	public void condition() {
		super.condition();
		this.init = false;
	}
	
	public void updateDepiction(Depiction pic) {
		
	}
}
