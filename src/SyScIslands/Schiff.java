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
			if (this.z != 1) {
				// island fields are only allowed on layer 1!
				this.leave();
			}
		} else {
			this.move_Random(dx, dy);
			this.draw();
		}

	}
}
