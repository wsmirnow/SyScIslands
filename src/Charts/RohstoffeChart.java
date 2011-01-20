package Charts;

import eawag.chart.Chart;
import SyScIslands.Insel;
import SyScIslands.Karte;

public class RohstoffeChart extends Chart {
	
	public Karte karte;
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		for (Insel insel : karte.getInseln()) {
			if (insel == null) continue;
			int inselId = insel.getID();
			
			lineTo("Insel "+inselId+" Wild", Chart.TYPE_LINE, getTop().getTime(), insel.curWild);
			lineTo("Insel "+inselId+" Holz", Chart.TYPE_LINE, getTop().getTime(), insel.curHolz);
		}
	}

}
