package SyScIslands;

import eawag.grid.Bug;
import eawag.model.Swarm;

public class Siedler extends Bug {
	
	public final static int BERUF_BAUER = 1;
	public final static int BERUF_JAEGER = 2;
	public final static int BERUF_HOLZFAELLER = 3;
	public final static int BERUF_HAFENBAUER = 4;
	public final static int BERUF_SCHIFFSBAUER = 5;
	
	public int beruf = 1;
	public int amArbeiten = 0;
	public int hungerhungerSeit = 0;
	
	public Siedler() {
		this(1+(4*(int)Math.random()));
	}
	
	public Siedler(int berufsId) {
		this.beruf = berufsId;
	}
	
	public void sterben() {
		Swarm parent = getFather();
		if (parent != null && parent instanceof Dorf) {
			((Dorf)parent).siedlerEntfernen(this);
		}
	}
	
	public void reproduktion() {
		
	}
	
	public void berufAusueben() {
		if (amArbeiten > 0) {
			// arbeitet bereits
			amArbeiten--;
			return;
		}
		
		Swarm parent = getFather();
		if (parent != null && parent instanceof Dorf) {
			Dorf dorf = (Dorf)parent;
			
			if (amArbeiten == 0) {
				// fertig mit der arbeit
				switch(beruf) {
				case BERUF_BAUER:
					
				}

			}
			
			int dauer = -1;
			Karte karte = dorf.getKarte();
			if (karte == null)
				return;
			
			switch (beruf) {
			case BERUF_BAUER:
				dauer = karte.bauerTime;
				break;
			case BERUF_JAEGER:
				dauer = karte.jaegerTime;
				break;
			case BERUF_HOLZFAELLER:
				//dauer = karte.holzfaellerDauer;
				break;
			case BERUF_HAFENBAUER:
				dauer = karte.hafenbauTime;
				break;
			case BERUF_SCHIFFSBAUER:
				dauer = karte.schiffsbauTime;
				break;
			default:
				return;
			}
			
			Insel insel = dorf.getInsel();
			if (dauer < 0 || insel == null) return;
			// fange an zu arbeiten
			amArbeiten = dauer+(int)(dauer * insel.zugaenglichkeit);
		}
	}
}
