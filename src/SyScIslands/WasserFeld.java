package SyScIslands;

import eawag.grid.Bug;
import eawag.grid.Depiction;

public class WasserFeld extends Feld {
	
	public void condition() {
		super.condition();
		this.init = false;
	}
	
	public void action() {
		updateDepiction();
	}
	
	public void updateDepiction() {
		Bug b = ((Karte)getFather()).getBug(x, y, 0);
		Depiction d;
		if (b != null && b instanceof Schiff) {
			d = findDepict("Schiff");
		} else {
			d = findDepict("Wasser");
		}
		if (d != null) setDepiction(d);
	}
}
