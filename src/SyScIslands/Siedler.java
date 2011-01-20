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
	
	Schiff schiff = null;
	// Lock-Variable fuer den Zugriff auf das schiff;
	static Object lockSchiff = new Object();

	public Siedler() {
		this(1 + new java.util.Random().nextInt(5));
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
		setActive(false);
	}

	public void reproduktion() {
		if (dorf == null) return; 
		
		// Reproduktion wenn Nahrung ausreichend fuer 10 Siedler und Holz fuer 5
		if (dorf.getNahrung() > dorf.getKarte().nahrungsVerbrauch * 2 && 
			dorf.getHolz() > dorf.getKarte().holzVerbrauch * 2 &&
			dorf.getChildCount() < dorf.getInsel().groesse * 1) {
			
			dorf.verringereNahrungUm(dorf.getKarte().nahrungsVerbrauch);
			dorf.verringereHolzUm(dorf.getKarte().holzVerbrauch);
			dorf.siedlerHinzufuegen(new Siedler());
		}
	}

	public void ernaehren() {
		Dorf dorf = getDorf();
		if (dorf == null)
			return;
		Karte karte = dorf.getKarte();
		if (karte == null)
			return;

		if (dorf.getNahrung() - karte.nahrungsVerbrauch > 0) {
			dorf.verringereNahrungUm(karte.nahrungsVerbrauch);
			hungerSeit = 0;
		} else {
			// nicht genug Nahrung
			if (++hungerSeit > karte.nahrungsKnappheitZeit)
				sterben();
		}

		if (dorf.getHolz() - karte.holzVerbrauch > 0) {
			dorf.verringereHolzUm(karte.holzVerbrauch);
			holzFehltSeit = 0;
		} else {
			// nicht genug Holz
			if (++holzFehltSeit > karte.holzKnappheitZeit)
				sterben();
		}

	}

	public void berufAusueben() {
		if (getDorf() == null) return;
		Karte karte = getDorf().getKarte();
		if (karte == null) return;
		
		if (amArbeiten > 0) {
			// arbeitet bereits
			switch (beruf) {
			case BERUF_HAFENBAUER:
				if (dorf.getHolz() >= karte.hafenbauerVerbrauch) {
					dorf.verringereHolzUm(karte.hafenbauerVerbrauch);
					amArbeiten--;
				}
				break;
			default:
				amArbeiten--;
			}
			return;
		}

		int ertrag;
		if (amArbeiten == 0) {
			// fertig mit der arbeit
			switch (beruf) {
			case BERUF_BAUER:
				dorf.erhoeheNahrungUm(karte.bauerErtrag);
				break;
			case BERUF_JAEGER:
				ertrag = dorf.getInsel().curWild < karte.jaegerErtrag ? 
						     dorf.getInsel().curWild : 
						     karte.jaegerErtrag;
				dorf.getInsel().curWild -= ertrag;
				dorf.erhoeheNahrungUm(ertrag);
				break;
			case BERUF_HOLZFAELLER:
				ertrag = dorf.getInsel().curHolz < karte.holzfaellerErtrag ? 
						     dorf.getInsel().curHolz : 
							 karte.holzfaellerErtrag;
				dorf.getInsel().curHolz -= ertrag;
				dorf.erhoeheHolzUm(ertrag);
				break;
			case BERUF_HAFENBAUER:
				dorf.hafen = true;
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
			if (getSchiff() == null)
				setSchiff(new Schiff((int)(karte.schiffsbauerDauer * getDorf().getInsel().zugaenglichkeit)));
			else {
				if (getDorf().getHolz() > karte.schiffsbauerVerbrauch) {
					getDorf().verringereHolzUm(karte.schiffsbauerVerbrauch);
					if (getSchiff().verringereBauzeit(1) < 1) {
						if (getDorf().hafen && getSchiff().stecheInSee(getDorf())) {
							setSchiff(null);
						}
					}
				}
			}
			dauer = Integer.MIN_VALUE;
			break;
		default:
			return;
		}

		Insel insel = dorf.getInsel();
		if (dauer < 0)
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
	
	public Schiff getSchiff() {
		Schiff schiff;
		synchronized (lockSchiff) {
			schiff = this.schiff;
		}
		return schiff;
	}
	
	public void setSchiff(Schiff schiff) {
		synchronized (lockSchiff) {
			this.schiff = schiff;
		}
	}
}
