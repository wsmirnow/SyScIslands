package SyScIslands;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Karte extends Grid {

	/** Ressourcen */
	public int maxHolz = 10;
	public int minHolz = 0;
	public int regHolz = 1;

	public int maxWild = 25;
	public int minWild = 0;
	public int regWild = 1;

	public int maxKorn = 300;
	public int minKorn = 0;
	public int regKorn = 1;

	public float Wasserwahrscheinlichkeit = 0.5f;

	/** Berufsdauer */
	public int bauerTime = 20;
	public int jaegerTime = 5;
	public int hafenbauTime = 50;
	public int schiffsbauTime = 25;

	@Override
	public void action() {

		if (getTop().getTime() == 0) {
			// suche benachbarte Landfelder und fuege sie zur einer Insel
			// zusammen
			for (int x = 0; x < getXSize(); x++) {
				for (int y = 0; y < getYSize(); y++) {
					Bug bug = getBug(x, y, 1);
					if (bug != null && bug instanceof LandFeld) {
						LandFeld feld = (LandFeld) bug;
						if (feld.insel == null) {
							feld.insel = new Insel(this);
							feld.join(feld.insel);
							sucheLandNachbarn(feld.insel, feld);
							feld.insel.join(this);
						}
					}
				}
			}
		}
	}

	/**
	 * Sucht rekursiv nach direkt benachbaten LandFeldern, die noch zu keiner
	 * Insel gehoeren und fuegt sie der Insel hinzu. Dieser Vorgang wird so
	 * lange durchgefuehrt, bis keine Insel-freien Nachbarfelder mehr da sind.
	 * 
	 * @param insel
	 *            Insel, welche um weitere Landfelder wachsen soll
	 * @param feld
	 *            bei welchem die Nachbarn gesucht werden sollen
	 */
	void sucheLandNachbarn(Insel insel, LandFeld feld) {
		int[] dx = MOORE_DX;
		int[] dy = MOORE_DX;

		for (int x = 0; x < dx.length; x++)
			for (int y = 0; y < dy.length; y++) {
				Bug b = getBug(feld.x + dx[x], feld.y + dy[y], 1);
				if (b != null && b instanceof LandFeld) {
					LandFeld nachbar = (LandFeld) b;
					if (nachbar.insel == null) {
						nachbar.insel = insel;
						nachbar.join(insel);
						sucheLandNachbarn(insel, nachbar);
					}
				}
			}
	}
}
