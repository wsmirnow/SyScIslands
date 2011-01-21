package Charts;

import eawag.chart.Chart;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class InselRohstoffeChart extends Chart {
	
	public Karte karte;
	
	public InselRohstoffeChart() {
		setHTitle("Zeitschritte");
		setVTitle("Resourcenmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			if (insel == null) continue;
			int inselId = insel.getID();
			
			lineTo("Insel "+inselId+" Wild", Chart.TYPE_LINE, getTop().getTime(), insel.getWild());
			lineTo("Insel "+inselId+" Holz", Chart.TYPE_LINE, getTop().getTime(), insel.getHolz());
		}
	}

}
