package SyScIslands;

import java.util.LinkedList;
import java.util.List;

import eawag.grid.Bug;
import eawag.grid.Grid;

public class Karte extends Grid {

	/** Ressourcen pro Feld */
	public int holzMax = 5;
	public int holzMin = 0;
	public int holzReg = 2;

	public int wildMax = 5;
	public int wildMin = 0;
	public int wildReg = 2;

	public float Wasserwahrscheinlichkeit = 0.5f;

	/** Berufsdauer */
	public int bauerDauer = 21;
	public int jaegerDauer = 5;
	public int holzfaellerDauer = 3;
	public int hafenbauerDauer = 100;
	public int schiffsbauerDauer = 50;

	/** Berufsertrag */
	public int bauerErtrag = 10; // pro Runde
	public int jaegerErtrag = 5; // pro Runde
	public int holzfaellerErtrag = 25; // pro Runde
	public int schiffsbauerVerbrauch = 2; // pro Zeitschritt
	public int hafenbauerVerbrauch = 3; // pro Zeitschritt

	/** Verbrauch der Siedler pro Zeitschritt */
	public int nahrungsVerbrauch = 2;
	public int holzVerbrauch = 1;

	/** Überlebenswerte der Siedler */
	public int nahrungsKnappheitZeit = 5;
	public int holzKnappheitZeit = 15;

	protected List<Insel> inseln = new LinkedList<Insel>();

	@Override
	public void condition() {
		super.condition();

		if (getTop().getTime() == 0) {
			// suche benachbarte Landfelder und fuege sie zur einer Insel
			// zusammen
			for (int x = 0; x < getXSize(); x++) {
				for (int y = 0; y < getYSize(); y++) {
					Bug bug = getBug(x, y, 1);
					if (bug != null && bug instanceof LandFeld) {
						LandFeld feld = (LandFeld) bug;
						if (feld.insel == null) {
							feld.setzeInsel(new Insel(this));
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
						nachbar.setzeInsel(insel);
						sucheLandNachbarn(insel, nachbar);
					}
				}
			}
	}

	public Insel getInsel(int id) {
		for (Insel insel : inseln) {
			if (insel.id == id)
				return insel;
		}
		return null;
	}

	public int getAnzahlInsel() {
		return inseln.size();
	}
	
	public List<Insel> getInseln() {
		return inseln;
	}
}
