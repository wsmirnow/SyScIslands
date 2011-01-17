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
			int xneu;
			int yneu;

			xneu = x + Grid.MOORE_DX[rnd.nextInt(7)];
			yneu = y + Grid.MOORE_DY[rnd.nextInt(7)];
			Bug b = this.getGrid().getBug(xneu, yneu, 1);
			if (b instanceof LandFeld) {
				// Land in sicht
				LandFeld land = (LandFeld) b;
				Insel insel = land.insel;
				if (insel != null) {
					try {
						insel.setDorf(new Dorf());
						// Setze Dorf-Depiction
						land.setDepiction(findDepict("Dorf"));
						zerstoereSchiff();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			} else {
				moveBug(xneu, yneu, z);
			}
		}
	}

	private void zerstoereSchiff() {
		this.leave();
	}
}
