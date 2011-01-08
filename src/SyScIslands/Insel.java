package SyScIslands;

import eawag.model.Swarm;

public class Insel extends Swarm {
	
	private static int autoid = 0;
	
	int id;

	
	public Insel() {
		this.id = autoid++;
		//System.out.println("Insel mit der id "+id+" wurde erstellt");
	}
	
	public int getGroesse() {
		return getChildCount();
	}
	
	public void action() {
		//System.out.println("meine id ist "+id);
	}
}
