package Charts;

import eawag.chart.Chart;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class InselWildChart extends Chart {
	
	public Karte karte;
	
	public InselWildChart() {
		setHTitle("Zeitschritte");
		setVTitle("Wildmenge");
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			if (insel == null) continue;
			int inselId = insel.getID();
			
			lineTo("Insel "+inselId+" Wild", Chart.TYPE_LINE, getTop().getTime(), insel.getWild());
		}
	}
}
