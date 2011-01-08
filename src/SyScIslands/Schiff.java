package SyScIslands;

import eawag.grid.Bug;

public class Schiff extends Bug {
	java.util.Random rnd = new java.util.Random();
	int[] dx = this.getGrid().MOORE_DX;
	int[] dy = this.getGrid().MOORE_DY;

	@Override
	public void action() {

		// Fahre
		if (this.getTop().getTime() == 0) {
			if (this.z != 0) {
				// Schiffe agieren nur auf der zweiten Ebene
				this.leave();
			}
		} else {
			this.moveRandom(dx, dy);
			this.draw();
		}
	}
}
