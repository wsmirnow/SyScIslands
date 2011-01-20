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
		
		removeAllData();
		
		HashMap<Integer, Map<Integer, Integer>> berufeProInsel = new HashMap<Integer, Map<Integer, Integer>>();
		for (Insel insel : karte.getInseln()) {
			if (insel == null) continue;
			int inselId = insel.getID();

			addHGuide((inselId*5)+1, "Insel "+inselId);
						
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
		
		for (Integer inselId : berufeProInsel.keySet()) {
			
			Map<Integer, Integer> berufe = berufeProInsel.get(inselId);
			if (berufe == null) continue;
			List<Integer> berufIds = new LinkedList<Integer>(berufe.keySet());
			Collections.sort(berufIds, new MapValueComperator(berufe));
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
				
				//System.out.println(getTop().getTime()+": insel"+inselId+" "+beruf+" "+ berufe.get(berufId));
				lineTo(beruf, Chart.TYPE_BALKEN, inselId*5+berufId, berufe.get(berufId));
			}
		}
	}
	
	class MapValueComperator implements Comparator {

		Map<Integer, Integer> map;
		public MapValueComperator(Map<Integer, Integer> map) {
			this.map = map;
		}
		
		@Override
		public int compare(Object key1, Object key2) {
			return map.get(key2) - map.get(key1);
		}
	}
}
