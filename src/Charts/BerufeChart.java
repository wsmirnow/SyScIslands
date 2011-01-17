package Charts;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import SyScIslands.Dorf;
import SyScIslands.Insel;
import SyScIslands.Karte;
import SyScIslands.Siedler;
import eawag.chart.Chart;

public class BerufeChart extends Chart {
	
	public Karte karte;
	
	public void condition() {
		super.condition();
		if (getTop().getTime() < 1) return;
		
		HashMap<Integer, Map<Integer, Integer>> berufeProInsel = new HashMap<Integer, Map<Integer, Integer>>();
		List childs = (List) karte.getChilds();
		for (Object child : childs) {
			if (!(child instanceof Insel)) continue;
			Insel insel = (Insel)child;
			int inselId = insel.getID();
			if (insel.getDorf() == null) {
				berufeProInsel.put(inselId, null);
			} else {
				Dorf dorf = insel.getDorf();
				List dorfChilds = (List) dorf.getChilds();
				Map<Integer, Integer> berufe = new HashMap<Integer, Integer>();
				int anzSiedler = 0;
				for (Object dorfChild : dorfChilds) {
					if (dorfChild instanceof Siedler) {
						Siedler siedler = (Siedler) dorfChild;
						anzSiedler++;
						
						int beruf = siedler.beruf;
						Integer berufAnz = berufe.get(beruf);
						if (berufAnz == null) berufAnz = 0;
						berufe.put(beruf, ++berufAnz);
					}
				}
				berufeProInsel.put(inselId, berufe);
			}
		}
		
		removeAllData();
		for (Integer inselId : berufeProInsel.keySet()) {
			addHGuide(inselId, "Insel "+inselId);
			Map<Integer, Integer> berufe = berufeProInsel.get(inselId);
			if (berufe == null) continue;
			List<Integer> berufIds = new LinkedList<Integer>(berufe.keySet());
			Collections.sort(berufIds, new MyComperator(berufe));
			for (Integer berufId : berufIds) {
				String beruf;
				switch (berufId) {
				case Siedler.BERUF_BAUER:
					beruf = "Bauer";
					break;
				case Siedler.BERUF_JAEGER:
					beruf = "Jaeger";
					break;
				case Siedler.BERUF_HOLZFAELLER:
					beruf = "Holzfaeller";
					break;
				case Siedler.BERUF_HAFENBAUER:
					beruf = "Hafenbauer";
					break;
				case Siedler.BERUF_SCHIFFSBAUER:
					beruf = "Schiffsbauer";
					break;
				default:
					beruf = "unbekannt";
				}
				System.out.println(getTop().getTime()+": insel"+inselId+" "+beruf+" "+ berufe.get(berufId));
				lineTo(beruf, Chart.TYPE_BALKEN, inselId, berufe.get(berufId));
			}
		}
	}
	
	class MyComperator implements Comparator {

		Map<Integer, Integer> berufe;
		public MyComperator(Map<Integer, Integer> map) {
			this.berufe = map;
		}
		
		@Override
		public int compare(Object key1, Object key2) {
			return berufe.get(key2) - berufe.get(key1);
		}
	}

}
