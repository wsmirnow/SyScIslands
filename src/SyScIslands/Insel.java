package SyScIslands;

import eawag.model.Swarm;

public class Insel extends Swarm {

	private static int autoid = 0;
	private boolean init = true;

	public int curHolz = 0;
	public boolean wasser = false;
	public int curWild = 0;
	public int curKorn = 0;

	public int holzMax = 1;
	public int wildMax = 1;
	public int kornMax = 1;
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
		// Erstelle random max-Werte fuer Ressourcen
		this.holzMax = rnd.nextInt(karte.holzMax) + karte.holzMin;
		this.wildMax = rnd.nextInt(karte.wildMax) + karte.wildMin;
		this.kornMax = rnd.nextInt(karte.kornMax) + karte.kornMin;

		this.curHolz = rnd.nextInt(holzMax);
		this.curWild = rnd.nextInt(wildMax);
		this.curKorn = rnd.nextInt(kornMax);
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
		}
	}

	@Override
	public void condition() {
		super.condition();
		// Regenerationsphase
		regenerateResorces();
	}

	private void regenerateResorces() {

		curHolz += karte.holzReg;
		if (curHolz > holzMax)
			curHolz = holzMax;
		curWild += karte.wildReg;
		if (curWild > wildMax)
			curWild = wildMax;
		curKorn += karte.kornReg;
		if (curKorn > kornMax)
			curKorn = kornMax;
	}

	public void setDorf(Dorf dorf) throws IllegalAccessException {
		if (dorf != null)
			throw new IllegalAccessException("Dorf schon vorhanden!");
		this.dorf = dorf;
		dorf.join(this);
	}

	public void entferneDorf() {
		dorf.leave();
		dorf.aufloesen();
		dorf = null;
	}
}
