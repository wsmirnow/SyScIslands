package SyScIslands;

import java.util.Random;

import eawag.model.Swarm;

public class Insel extends Swarm {
	
	private static int autoid = 0;
	
	private boolean init = true; 
	
	/** Erlaubt den zugriff auf die hinterlegte Werte */
	protected Karte karte;
	
	/** Insel Id */
	int id;
	
	/** Zugaenglichkeit der Insel entscheidet ueber die Daeur der Berufsausuebung (0=lang, 1=kurz)*/
	float zugaenglichkeit = 1f;

	
	public Insel(Karte karte) {
		this.id = autoid++;
		this.karte = karte;
		//System.out.println("Insel mit der id "+id+" wurde erstellt");
	}
	
	public int getGroesse() {
		return getChildCount();
	}
	
	public void action() {
		if (init) {
			if (karte == null)
				return;
			
			Random rand = getTop().getRandom();
			zugaenglichkeit = rand.nextFloat();
		}
	}
}
