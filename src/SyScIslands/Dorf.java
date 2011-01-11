package SyScIslands;

import java.util.LinkedList;
import java.util.List;

import eawag.model.Swarm;

public class Dorf extends Swarm {

	public int nahrung;
	public int holz;
	public int wasser;
	public boolean hafen;
	public List<Schiff> schiffe;

	public Dorf() {
		this(10);
	}
	
	public Dorf(int siedlerAnz) {
		this.nahrung = 0;
		this.holz = 0;
		this.wasser = 0;
		this.hafen = false;
		this.schiffe = new LinkedList<Schiff>();
		for (int i = 0; i < siedlerAnz; i++) {
			siedlerHinzufuegen(new Siedler());
		}
	}

	@Override
	public void condition() {
		super.condition();
	}

	@Override
	public void action() {
		if (getAnzahlSiedler() == 0) {
			Insel insel = getInsel();
			if (insel != null)
				insel.entferneDorf();
		}
	}

	public synchronized void siedlerHinzufuegen(Siedler siedler) {
		if (siedler.getFather() != this)
			siedler.join(this);
	}

	public synchronized void siedlerEntfernen(Siedler siedler) {
		if (siedler.getFather() == this) {
			siedler.leave();
		}
	}

	public int getAnzahlSiedler() {
		return getChildCount();
	}

	public Karte getKarte() {
		Swarm parent = getFather();
		if (parent != null && parent instanceof Insel) {
			return ((Insel) parent).karte;
		} else
			return null;
	}

	public Insel getInsel() {
		Swarm parent = getFather();
		if (parent != null && parent instanceof Insel) {
			return (Insel) parent;
		} else
			return null;
	}

	public void aufloesen() {
		for (Object kind : getChilds()) {
			if (kind != null && kind instanceof Siedler) {
				((Siedler) kind).sterben();
			}
		}
		leave();
	}
}
