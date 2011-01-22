package Charts;

import eawag.chart.Chart;
import SyScIslands.Dorf;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class DorfHolzChart extends Chart {
	
	public Karte karte;
	
	public DorfHolzChart() {
		setHTitle("Zeitschritte");
		setVTitle("Holzmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			int inselId = insel.getID();
			int holz;
			if (insel.getDorf() == null) {
				holz = 0;
			} else {
				Dorf dorf = insel.getDorf();
				holz = dorf.getHolz();
			}
			
			lineTo("Dorf auf Insel "+inselId+" - Holz", Chart.TYPE_LINE, getTop().getTime(), holz);
		}
	}

}
