package SyScIslands;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Schiff extends Bug {
	java.util.Random rnd = new java.util.Random();

	@Override
	public void action() {
		// Fahre
		if (this.getTop().getTime() == 0) {
			if (this.z != 0) {
				// Schiffe agieren nur auf der zweiten Ebene
				this.leave();
			}
		} else {
			// bewegungsphase
			int xneu = x + Grid.MOORE_DX[rnd.nextInt(7)];
			int yneu = y + Grid.MOORE_DY[rnd.nextInt(7)];
			moveBug(xneu, yneu, z);
		}
	}
}
