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
	public int hungerSeit = 0;
	public int holzFehltSeit = 0;

	private Dorf dorf = null;

	public Siedler() {
		this(1 + new java.util.Random().nextInt(4));
	}

	public Siedler(int berufsId) {
		this.beruf = berufsId;
	}

	@Override
	public void action() {
		this.dorf = getDorf();
		if (dorf == null)
			return;

		ernaehren();
		berufAusueben();
		reproduktion();
	}

	public void sterben() {
		this.dorf = getDorf();
		if (this.dorf == null)
			return;
		this.dorf.siedlerEntfernen(this);
	}

	public void reproduktion() {

	}

	public void ernaehren() {
		Dorf dorf = getDorf();
		if (dorf == null)
			return;
		Karte karte = dorf.getKarte();
		if (karte == null)
			return;

		if (dorf.nahrung - karte.nahrungsVerbrauch > 0) {
			dorf.nahrung -= karte.nahrungsVerbrauch;
			hungerSeit = 0;
		} else {
			// nicht genug Nahrung
			if (++hungerSeit > karte.nahrungsKnappheitZeit)
				sterben();
		}

		if (dorf.holz - karte.holzVerbrauch > 0) {
			dorf.holz -= karte.holzVerbrauch;
			holzFehltSeit = 0;
		} else {
			// nicht genug Holz
			if (++holzFehltSeit > karte.holzKnappheitZeit)
				sterben();
		}

	}

	public void berufAusueben() {
		if (amArbeiten > 0) {
			// arbeitet bereits
			amArbeiten--;
			return;
		}

		Karte karte = this.dorf.getKarte();
		if (karte == null)
			return;

		int ertrag;
		if (amArbeiten == 0) {
			// fertig mit der arbeit
			switch (beruf) {
			case BERUF_BAUER:
				dorf.nahrung += karte.bauerErtrag;
				break;
			case BERUF_JAEGER:
				ertrag = dorf.getInsel().curWild < karte.jaegerErtrag ? 
						     dorf.getInsel().curWild : 
						     karte.jaegerErtrag;
				dorf.getInsel().curWild -= ertrag;
				dorf.nahrung += ertrag;
				break;
			case BERUF_HOLZFAELLER:
				ertrag = dorf.getInsel().curHolz < karte.holzfaellerErtrag ? 
						     dorf.getInsel().curHolz : 
							 karte.holzfaellerErtrag;
				dorf.getInsel().curHolz -= ertrag;
				dorf.holz += ertrag;
				break;
			case BERUF_HAFENBAUER:

				break;
			case BERUF_SCHIFFSBAUER:

				break;
			default:
				break;
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
		if (dauer < 0 || insel == null)
			return;
		// fange an zu arbeiten
		amArbeiten = dauer + (int) (dauer * insel.zugaenglichkeit);
	}

	public Dorf getDorf() {
		Swarm parent = getFather();
		if (parent instanceof Dorf)
			return (Dorf) parent;
		else
			return null;
	}
}
