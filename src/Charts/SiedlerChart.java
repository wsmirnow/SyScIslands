package Charts;

import java.util.HashMap;
import java.util.List;

import SyScIslands.Insel;
import SyScIslands.Karte;
import eawag.chart.Chart;

public class SiedlerChart extends Chart {
	
	public Karte karte;
	
	public SiedlerChart() {
		removeAllData();
		setHTitle("Zeitschritte");
		setVTitle("Anzahl Siedler pro Insel");
		setComment("Stellt die Anzahl an Siedlern für jede Insel dar.");
		
	}
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		HashMap<Integer, Integer> siedlerImDorf = new HashMap<Integer, Integer>();
		List childs = (List) karte.getChilds();
		for (Object child : childs) {
			if (!(child instanceof Insel)) continue;
			Insel insel = (Insel)child;
			if (insel.getDorf() == null) continue;
			int inselId = insel.getID();
			int siedler = insel.getDorf().getAnzahlSiedler();
			siedlerImDorf.put(inselId, siedler);
		}
		
		for (Integer inselId : siedlerImDorf.keySet()) {
			lineTo("Siedler auf der Insel "+inselId, TYPE_LINE, getTop().getTime(), siedlerImDorf.get(inselId));
		}
	}

}
