package SyScIslands;

import java.util.HashMap;
import java.util.Map;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Schiff extends Bug {
	java.util.Random rnd = new java.util.Random();
	private Map<Integer, Integer> siedler = new HashMap<Integer, Integer>();
	int faterInselId = -1;
	
	protected Dorf dorf = null;
	
	protected int nahrung = 0;
	protected int holz = 0;

	private int bauzeit;

	public Schiff() {
		this.bauzeit = -1;
	}

	public Schiff(int bauzeit) {
		this.bauzeit = bauzeit;
	}

	@Override
	public void action() {
		if (this.getTop().getTime() == 0) {
			if (this.z != 0) {
				// Schiffe agieren nur auf der ersten Ebene
				this.leave();
			}
		} else {
			if (bauzeit > 0)
				return;

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

				// wenn an der erbaungsinsel angekommen
				if (faterInselId >= 0 && insel.id == faterInselId) {
					moveBug(xneu, yneu, z);
					return;
				}

				if (insel != null) {
					try {
						if (siedler != null && !siedler.isEmpty())
							insel.setDorf(new Dorf(xneu, yneu, siedler.size(), nahrung, holz));
						else 
							insel.setDorf(new Dorf(xneu, yneu));
						if (dorf != null) {
							dorf.schiffe.remove(this);
						}
						zerstoereSchiff();
					} catch (IllegalAccessException e) {
						// Siedler zum Dorf hinzufuegen
						if (siedler == null || siedler.isEmpty())
							return;
						for (Integer s : this.siedler.keySet())
							insel.dorf.siedlerHinzufuegen(new Siedler(s));
						zerstoereSchiff();
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

	public synchronized boolean stecheInSee(Dorf dorf) {
		if (dorf == null)
			return false;
		if (dorf.schiffe.size() < 5 && siedler.isEmpty()) {
			for (int i = 0; i < 10 && siedler.size() < 10; i++) {
				Siedler s = dorf.getRandomSiedler();
				if (s == null)
					continue;
				int beruf = s.beruf;
				int holz = (dorf.getKarte().holzVerbrauch * dorf.getKarte().holzKnappheitZeit);
				int nahrung = dorf.getKarte().nahrungsVerbrauch * dorf.getKarte().nahrungsKnappheitZeit;
				if (nahrung < dorf.getNahrung() && holz < dorf.getHolz()) {
					dorf.verringereNahrung(dorf.getKarte().nahrungsVerbrauch * dorf.getKarte().nahrungsKnappheitZeit);
					dorf.verringereHolz(dorf.getKarte().holzVerbrauch * dorf.getKarte().holzKnappheitZeit);
					this.nahrung += nahrung;
					this.holz += holz;
				} else continue;
				
				s.sterben();
				Integer anz = siedler.get(beruf);
				if (anz == null)
					anz = 0;
				siedler.put(beruf, ++anz);
			}
		}
		if (siedler.isEmpty()
				|| dorf.getKarte().getBug(dorf.xPos, dorf.yPos, 0) instanceof Schiff) {
			// return false;
			setActive(false);
			return false;
		}

		faterInselId = dorf.getInsel().id;
		this.dorf = dorf;
		join(dorf.getInsel().karte);
		moveBug(dorf.xPos, dorf.yPos, 0);
		//setActive(true);
		return true;
	}

	public synchronized int getBauzeit() {
		return bauzeit;
	}

	public synchronized int verringereBauzeit(int differenz) {
		bauzeit -= differenz;
		return bauzeit;
	}
}
