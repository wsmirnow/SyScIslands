package SyScIslands;

import java.util.HashMap;
import java.util.Map;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Schiff extends Bug {
	java.util.Random rnd = new java.util.Random();
	private Map<Integer, Integer> siedler = new HashMap<Integer, Integer>();
	int faterInselId = -1;
	
	private int bauzeit;
	
	public Schiff() {
		this.bauzeit = -1;
		setActive(true);
	}
	
	public Schiff(int bauzeit) {
		this.bauzeit = bauzeit;
		setActive(false);
	}

	@Override
	public void action() {
		// Fahre
		if (this.getTop().getTime() == 0) {
			if (this.z != 0) {
				// Schiffe agieren nur auf der zweiten Ebene
				this.leave();
			}
		} else {
			if (bauzeit > 0) return;
			
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
				if (faterInselId >= 0 && insel.id == faterInselId)
					moveBug(xneu, yneu, z);
				
				if (insel != null) {
					try {
						insel.setDorf(new Dorf(xneu, yneu));
						zerstoereSchiff();
					} catch (IllegalAccessException e) {
						// Siedler zum Dorf hinzufuegen
						if (siedler == null || siedler.isEmpty()) return;
						for (Integer s : this.siedler.keySet())
							insel.dorf.siedlerHinzufuegen(new Siedler(s));
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
		if (dorf == null || isActive()) return false;
		if (siedler.isEmpty()) {
			for (int i = 0; i < 20; i++) {
				Siedler s = dorf.getRandomSiedler();
				if (s == null) continue;
				int beruf = s.beruf;
				s.sterben();
				Integer anz = siedler.get(beruf);
				if (anz == null) anz = 0;
				siedler.put(beruf, ++anz);
			}
		}
		if (siedler.isEmpty() || 
		    dorf.getKarte().getBug(dorf.xPos, dorf.yPos, 0) instanceof Schiff) 
			return false;
		
		faterInselId = dorf.getInsel().id;
		join(dorf.getInsel().karte);
		moveBug(dorf.xPos, dorf.yPos, 0);
		setActive(true);
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
