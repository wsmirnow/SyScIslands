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
		Dorf dorf = getDorf();
		if (dorf == null) return;
		dorf.siedlerEntfernen(this);
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
		Dorf dorf = getDorf();
		Karte karte = dorf.getKarte();
		if (karte == null)
			return;
		
		if (amArbeiten == 0) {
			// fertig mit der arbeit
			switch(beruf) {
			case BERUF_BAUER:
				dorf.nahrung += karte.bauerErtrag;
				break;
			case BERUF_JAEGER:
				dorf.nahrung += karte.jaegerErtrag;
				break;
			case BERUF_HOLZFAELLER:
				dorf.holz += karte.holzfaellerErtrag;
				break;
			case BERUF_HAFENBAUER:
				
				break;
			case BERUF_SCHIFFSBAUER:
				
				break;
			default: break;
			}
			amArbeiten = -1;
		}
		
		int dauer = -1;
		
		switch (beruf) {
		case BERUF_BAUER:
			dauer = karte.bauerDauer;
			break;
		case BERUF_JAEGER:
			dauer = karte.jaegerDauer;
			break;
		case BERUF_HOLZFAELLER:
			dauer = karte.holzfaellerDauer;
			break;
		case BERUF_HAFENBAUER:
			dauer = karte.hafenbauerDauer;
			break;
		case BERUF_SCHIFFSBAUER:
			dauer = karte.schiffsbauerDauer;
			break;
		default:
			return;
		}
		
		Insel insel = dorf.getInsel();
		if (dauer < 0 || insel == null) return;
		// fange an zu arbeiten
		amArbeiten = dauer+(int)(dauer * insel.zugaenglichkeit);
	}
	
	public Dorf getDorf() {
		Swarm parent = getFather();
		if (parent instanceof Dorf) return (Dorf) parent;
		else return null;
	}
	
	public void action() {
		Dorf dorf = getDorf();
		if (dorf == null) return;
		
		berufAusueben();
	}
}
