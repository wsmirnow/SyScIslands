package SyScIslands;

import eawag.grid.Bug;

public class Siedler extends Bug {
	
	public final static int BERUF_BAUER = 1;
	public final static int BERUF_JAEGER = 2;
	public final static int BERUF_HOLZFAELLER = 3;
	public final static int BERUF_HAFENBAUER = 4;
	public final static int BERUF_SCHIFFSBAUER = 5;
	
	public int beruf = 1;
	public int amArbeiten = 0;
	public int hungerhungerSeit = 0;
	
	public Siedler() {
		this(1+(4*(int)Math.random()));
	}
	
	public Siedler(int berufsId) {
		this.beruf = berufsId;
	}
	
	public void sterben() {
		
	}
	
	public void reproduktion() {
		
	}
	
	public void berufAusueben() {
		
	}
}
