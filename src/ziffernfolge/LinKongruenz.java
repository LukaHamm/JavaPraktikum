package ziffernfolge;

import java.util.concurrent.TimeUnit;

public class LinKongruenz implements Zufallszahl {

	private final static int m = 100000000;
	private final static int m1 = 10000;
	private final static int b = 31415821;
	private int vorigeZahl;
	private int standardZahlenweite = 100;
	private int standardVon = 0;

	/**
	 * Methode zum multiplizieren von der Konstanten b mit der vorigen Zahl.
	 * Wird benoetigt um einen Ueberlauf zu vermeiden(siehe Sedgewick)
	 * 
	 * 
	 * @param p die vorherige Zahl
	 * @param q die Konstante b
	 * @return Multiplikation von q und p
	 */
	private int mult(int p, int q) {
		int p1, p0, q1, q0;
		p1 = p / m1;
		p0 = p % m1;
		q1 = q / m1;
		q0 = q % m1;

		return (((p0 * q1 + p1 * q0) % m1) * m1 + p0 * q0) % m;
	}

	public LinKongruenz() {
		vorigeZahl = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());;
	}

	public LinKongruenz(int von, int bis) {
		bis = bis< 0?0:bis;
		von = von< 0?0:von;
		int groessereZahl = Math.max(von, bis);
		standardVon = Math.min(von, bis);
		standardZahlenweite = (groessereZahl-standardVon) == 0?standardZahlenweite:groessereZahl-standardVon;
		if((groessereZahl - standardVon)== 0) {
			standardVon = 0;
		}
		vorigeZahl = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());;

	}

	public int naechste() {
		vorigeZahl = (mult(vorigeZahl, b) + 1) % m ;
		int zufallszahl = ((vorigeZahl / m1) * standardZahlenweite) / m1 +standardVon;
		return zufallszahl;
	}
	
	

	@Override
	public int naechste(int von, int bis) {
		bis = bis< 0?0:bis;
		von = von< 0?0:von;
		int groessereZahl = Math.max(von, bis);
		int kleinereZahl = Math.min(von, bis);
		int zahlenWeite = (groessereZahl - kleinereZahl) == 0 ?standardZahlenweite:groessereZahl - kleinereZahl;
		if((groessereZahl - kleinereZahl)== 0) {
			kleinereZahl = 0;
		}
		vorigeZahl = (mult(vorigeZahl, b) + 1) % m;
		int zufallszahl = ((vorigeZahl / m1) * zahlenWeite) / m1 + kleinereZahl;
		return zufallszahl;
	}

	public int getStandardZahlenweite() {
		return standardZahlenweite;
	}

	public void setStandardZahlenweite(int standardZahlenweite) {
		this.standardZahlenweite = standardZahlenweite;
	}

	public int getStandardVon() {
		return standardVon;
	}

	public void setStandardVon(int standardVon) {
		this.standardVon = standardVon;
	}
	
	
	
	
}
