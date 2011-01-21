package Charts;

import eawag.chart.Chart;
import SyScIslands.Dorf;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class DorfRohstoffeChart extends Chart {
	
	public Karte karte;
	
	public DorfRohstoffeChart() {
		setHTitle("Zeitschritte");
		setVTitle("Resourcenmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			int inselId = insel.getID();
			int nahrung, holz;
			if (insel.getDorf() == null) {
				nahrung = 0;
				holz = 0;
			} else {
				Dorf dorf = insel.getDorf();
				nahrung = dorf.getNahrung();
				holz = dorf.getHolz();
			}
			
			lineTo("Dorf auf Insel "+inselId+" - Nahrung", Chart.TYPE_LINE, getTop().getTime(), nahrung);
			lineTo("Dorf auf Insel "+inselId+" - Holz", Chart.TYPE_LINE, getTop().getTime(), holz);
		}
	}

}
