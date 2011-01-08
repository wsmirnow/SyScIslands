package SyScIslands;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Schiff extends Bug {
	private static java.util.Random rnd = new java.util.Random();

	@Override
	public void action() {
		// Fahre
		if (this.getTop().getTime() == 0) {
			if (this.z != 2) {
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
