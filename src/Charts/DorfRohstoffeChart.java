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
			if (insel == null || insel.getDorf() == null) continue;
			int inselId = insel.getID();
			Dorf dorf = insel.getDorf();
			
			lineTo("Dorf auf Insel "+inselId+" - Nahrung", Chart.TYPE_LINE, getTop().getTime(), dorf.getNahrung());
			lineTo("Dorf auf Insel "+inselId+" - Holz", Chart.TYPE_LINE, getTop().getTime(), dorf.getHolz());
		}
	}

}
