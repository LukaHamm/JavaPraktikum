/**
Projekt: Ziffernreihen Spiel 
File: LinKongruenzTest.java
Author: Lukas Hammer
Last changed: 21.01.2023
**/
package ziffernfolge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class LinkongruenzTest {

	private static LinKongruenz zufallsGeneratorDefaultKonstruktor;
	private static LinKongruenz zufallsGeneratorKonstruktorMitParameter;
	private static final int von = 20;
	private static final int bis = 80;
	private static final int standardZahlenweite = 100;
	
	
	@BeforeAll
	public static void initZufallsgeneratoren() {
		zufallsGeneratorDefaultKonstruktor = new LinKongruenz();
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(von, bis);
	}
	
	@Test
	@Order(1)
	void testNaechste() {
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste();
			assertTrue(zufallszahl >=0);
			assertTrue(zufallszahl <= standardZahlenweite);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <= bis);
		}
		
	}
	
	@Test
	@Order(2)
	void testNaechsteMitParametern() {
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(von, bis);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <= bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste(von,bis);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <= bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(-von, bis);
			assertTrue(zufallszahl >=0);
			assertTrue(zufallszahl <= bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(bis, von);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <= bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(0, 0);
			assertTrue(zufallszahl >=0);
			assertTrue(zufallszahl <= 100);
		}
	}
	@Test
	@Order(4)
	void testKonstruktorMitParametern() {
		int zufallszahl;
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(bis,von);
		for(int i =0;i<100;i++) {
			zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <=bis);
		}
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(0,von);
		for(int i =0;i<100;i++) {
			zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl <=von);
			assertTrue(zufallszahl >=0);
		}
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(bis,-von);
		for(int i =0;i<100;i++) {
			zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl <=bis);
			assertTrue(zufallszahl >=0);
		}
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(0,0);
		for(int i =0;i<100;i++) {
			zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl <=standardZahlenweite);
			assertTrue(zufallszahl >=0);
		}
	}
	
	@Test
	@Order(5)
	void test1() {
		int von=1;
		int bis=2;
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(von, bis);
		for(int i = 0; i<100;i++) 
		{
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <=bis);
		}
	}
	
	@Test
	@Order(6)
	void testGleichVerteilung() {
		int von=3;
		int bis=33;
		HashMap<Integer, Integer> zaehlerMap = new HashMap<>();
		int zaehler1 = 0;
		int zaehler2 = 0;
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(von, bis);
		for(int i = 0; i<100000;i++) 
		{
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			Integer zufallsZahlZaehler = zaehlerMap.get(zufallszahl);
			if(zufallsZahlZaehler == null) {
				zaehlerMap.put(zufallszahl, 1);
			}else {
				++zufallsZahlZaehler;
				zaehlerMap.put(zufallszahl, zufallsZahlZaehler);
			}
			
		}
		int durchschnittsanzahl = 100000/30;
		System.out.println("Anzahl der einzelnen Zahlen:");
		for(Entry<Integer,Integer> entry: zaehlerMap.entrySet()) {
			System.out.println("Zahl: " + entry.getKey() + " Wert: "+ entry.getValue()); 
			double quotient = (double) entry.getValue()/durchschnittsanzahl;
			double abweichung = Math.abs( 1.0d - quotient);
			System.out.println(abweichung);
			assertTrue(abweichung <= 0.1);
		}
		System.out.println();
		System.out.println("Durchschnitt: " + durchschnittsanzahl);
	
	}
	
	
	

}
