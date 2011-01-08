package SyScIslands;

import eawag.model.Swarm;

public class Dorf extends Swarm {
		
	public int nahrung;
	public int holz;
	public int wasser;
	public boolean hafen;
	
	
	public Dorf() {
		this.nahrung = 0;
		this.holz = 0;
		this.wasser = 0;
		this.hafen = false;
	}
	
	public void condition() {
		super.condition();
	}
	
	public void action() {
	}
}
