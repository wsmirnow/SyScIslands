package Charts;

import eawag.chart.Chart;
import SyScIslands.Dorf;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class DorfNahrungChart extends Chart {
	
	public Karte karte;
	
	public DorfNahrungChart() {
		setHTitle("Zeitschritte");
		setVTitle("Nahrungsmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			int inselId = insel.getID();
			int nahrung;
			if (insel.getDorf() == null) {
				nahrung = 0;
			} else {
				Dorf dorf = insel.getDorf();
				nahrung = dorf.getNahrung();
			}
			
			lineTo("Dorf auf Insel "+inselId+" - Nahrung", Chart.TYPE_LINE, getTop().getTime(), nahrung);
		}
	}

}
