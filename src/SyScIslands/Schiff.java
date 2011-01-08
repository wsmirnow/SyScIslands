package SyScIslands;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Schiff extends Bug {
	java.util.Random rnd = new java.util.Random();

	@Override
	public void action() {
		// Fahre
		if (this.getTop().getTime() == 0) {
			if (this.z != 1) {
				// schiff nur auf ebene 1
				this.leave();
			}
		} else {
			// bewegungsphase
			this.x += Grid.MOORE_DX[rnd.nextInt(7)];
			this.y += Grid.MOORE_DY[rnd.nextInt(7)];
		}
	}
}
