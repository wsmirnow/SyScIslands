package SyScIslands;

import eawag.grid.Bug;

public class Feld extends Bug {
	
	boolean init = true;
	
	public void condition() {
		super.condition();
		if (init) {
			if (this.z != 1) {
				// island fields are only allowed on layer 1!
				this.leave();
			}
		}
	}
}
