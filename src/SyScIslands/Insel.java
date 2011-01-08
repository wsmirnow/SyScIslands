package SyScIslands;

import java.util.Random;

import eawag.model.Swarm;

public class Insel extends Swarm {

	private static int autoid = 0;
	private boolean init = true;

	public int curHolz = 0;
	public boolean wasser = false;
	public int curWild = 0;
	public int curKorn = 0;
	/**
	 * Zugaenglichkeit der Insel entscheidet ueber die Daeur der Berufsausuebung
	 * (0=kurz, 1=lang)
	 */
	public float zugaenglichkeit = 1f;

	/** Erlaubt den zugriff auf die hinterlegte Werte */
	protected Karte karte;
	
	protected Dorf dorf = null;

	/** Insel Id */
	int id;

	public Insel(Karte karte) {
		this.id = autoid++;
		this.karte = karte;
		// System.out.println("Insel mit der id "+id+" wurde erstellt");

		// Ressourcen Initalisierung
		java.util.Random rnd = new java.util.Random();
		this.curHolz = rnd.nextInt(karte.holzMax) + karte.holzMin;
		this.curWild = rnd.nextInt(karte.wildMax) + karte.wildMin;
		this.curKorn = rnd.nextInt(karte.kornMax) + karte.kornMin;
		if (rnd.nextFloat() > karte.Wasserwahrscheinlichkeit)
			wasser = true;
		zugaenglichkeit = rnd.nextFloat();

	}

	public int getGroesse() {
		return getChildCount();
	}

	@Override
	public void action() {
		if (init) {
			if (karte == null)
				return;

			Random rand = getTop().getRandom();
			zugaenglichkeit = rand.nextFloat();
		}
	}

	@Override
	public void condition() {
		// Regenerationsphase
		regenerateResorces();
	}

	private void regenerateResorces() {
		curHolz += karte.holzReg;
		curWild += karte.wildReg;
		curKorn += karte.kornReg;
	}
	
	public void setDorf(Dorf dorf) throws IllegalAccessException {
		if (dorf != null) throw new IllegalAccessException("Dorf schon vorhanden!");
		this.dorf = dorf;
		dorf.join(this);
	}
	
	public void entferneDorf() {
		dorf.leave();
		dorf.aufloesen();
		dorf = null;
	}
}
