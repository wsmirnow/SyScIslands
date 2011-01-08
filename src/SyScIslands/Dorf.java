package SyScIslands;

import java.util.LinkedList;
import java.util.List;

import eawag.grid.Bug;
import eawag.model.Agent;
import eawag.model.Swarm;

public class Dorf extends Swarm {
	
	public int nahrung;
	public int holz;
	public int wasser;
	public boolean hafen;
	public List<Schiff> schiffe;
	
	public Dorf() {
		this.nahrung = 0;
		this.holz = 0;
		this.wasser = 0;
		this.hafen = false;
		this.schiffe = new LinkedList<Schiff>();
	}
	
	public void condition() {
		super.condition();
	}
	
	public void action() {
		
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
			return ((Insel)parent).karte;
		} else return null;
	}
	
	public Insel getInsel() {
		Swarm parent = getFather();
		if (parent != null && parent instanceof Insel) {
			return (Insel)parent;
		} else return null;
	}
	
	public void aufloesen() {
		for (Object kind : getChilds()) {
			if (kind != null && kind instanceof Siedler){
				((Siedler)kind).sterben();
			}
		}
	}
}
