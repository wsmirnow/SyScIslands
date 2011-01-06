package SyScIslands;
import eawag.grid.Bug;
import eawag.grid.Grid;

public class Karte extends Grid {
	
	
	public float maxGamePF = 10f;
	public float maxWoodPF = 10f;
	public float freshWaterProp = 0.2f;
	
	public float gameRegeneration = 0.1f;
	public float woodRegeneration = 0.1f;
	
	public void action() {
		
		if (getTop().getTime() == 0) {
			// suche benachbarten Landfelder und erstelle daraus eine Insel
			for (int x = 0; x < getXSize(); x++) {
				for (int y = 0; y < getYSize(); y++) {
					Bug bug = getBug(x, y, 1);
					if (bug != null && bug instanceof LandFeld) {
						LandFeld feld = (LandFeld) bug;
						if (feld.insel == null) {
							feld.insel = new Insel();
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
	 * Sucht rekursiv nach direkt benachbaten LandFeldern, 
	 * die noch zu keiner Insel gehoeren und fuegt sie der Insel hinzu.
	 * Dieser Vorgang wird so lange durchgefuehrt, 
	 * bis keine Insel-freien Nachbarfelder mehr da sind.
	 * 
	 * @param insel Insel, welche um weitere Landfelder wachsen soll
	 * @param feld bei welchem die Nachbarn gesucht werden sollen
	 */
	void sucheLandNachbarn(Insel insel, LandFeld feld) {
		int[] dx = MOORE_DX;
		int[] dy = MOORE_DX;
		
		for (int x = 0; x < dx.length; x++) 
			for (int y = 0; y < dy.length; y++) {
				Bug b = getBug(feld.x+dx[x], feld.y+dy[y], 1);
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
