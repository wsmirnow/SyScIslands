package SyScIslands;

import eawag.model.Swarm;

public class Insel extends Swarm {

	public static int autoid = 0;
	public float maxHolz = 10f;

	int id;

	public Insel() {
		this.id = autoid++;
		System.out.println("Insel mit der id " + id + " wurde erstellt");
	}

	public int getGroesse() {
		return getChildCount();
	}

	@Override
	public void action() {
		// System.out.println("meine id ist "+id);
	}
}
