package SyScIslands;

import java.util.LinkedList;
import java.util.List;

import eawag.model.Swarm;

public class Dorf extends Swarm {
	
	public int nahrung;
	public int holz;
	public int wasser;
	public boolean hafen;
	public List<Siedler> siedler;
	public List<Schiff> schiffe;
	
	public Dorf() {
		this.nahrung = 0;
		this.holz = 0;
		this.wasser = 0;
		this.hafen = false;
		this.siedler = new LinkedList<Siedler>();
		this.schiffe = new LinkedList<Schiff>();
	}
	
	public void condition() {
		super.condition();
	}
	
	public void action() {
		
	}
	
	public boolean siedlerHinzufuegen(Siedler siedler) {
		if (!this.siedler.contains(siedler))
			return this.siedler.add(siedler);
		else return false;
	}
	
	public boolean siedlerEntfernen(Siedler siedler) {
		if (this.siedler.contains(siedler))
			return this.siedler.remove(siedler);
		else return false;
	}
}
