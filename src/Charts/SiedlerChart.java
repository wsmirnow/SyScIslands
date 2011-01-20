package Charts;

import java.util.HashMap;

import SyScIslands.Insel;
import SyScIslands.Karte;
import eawag.chart.Chart;

public class SiedlerChart extends Chart {

	public Karte karte;

	public SiedlerChart() {
		removeAllData();
		setHTitle("Zeitschritte");
		setVTitle("Anzahl Siedler pro Insel");
		setComment("Stellt die Anzahl an Siedlern fuer jede Insel dar.");

	}

	@Override
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1)
			return;

		HashMap<Integer, Integer> siedlerImDorf = new HashMap<Integer, Integer>();
		
		for (Insel insel : karte.getInseln()) {
			int inselId = insel.getID();
			if (insel == null)
				continue;
			if (insel.getDorf() == null) {
				siedlerImDorf.put(inselId, 0);
			} else {
				int siedlerAnz = insel.getDorf().getAnzahlSiedler();
				siedlerImDorf.put(inselId, siedlerAnz);
			}
		}

		for (Integer inselId : siedlerImDorf.keySet()) {
			lineTo("Siedler auf der Insel " + inselId, TYPE_LINE, getTop()
					.getTime(), siedlerImDorf.get(inselId));
		}
	}

}
