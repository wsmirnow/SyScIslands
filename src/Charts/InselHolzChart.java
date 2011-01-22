package Charts;

import eawag.chart.Chart;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class InselHolzChart extends Chart {
	
	public Karte karte;
	
	public InselHolzChart() {
		setHTitle("Zeitschritte");
		setVTitle("Holznmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			if (insel == null) continue;
			int inselId = insel.getID();
			
			lineTo("Insel "+inselId+" Holz", Chart.TYPE_LINE, getTop().getTime(), insel.getHolz());
		}
	}

}
