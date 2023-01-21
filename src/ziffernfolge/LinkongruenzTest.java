/**
Projekt: Ziffernreihen Spiel 
File: LinKongruenzTest.java
Author: Lukas Hammer
Last changed: 21.01.2023
**/
package ziffernfolge;

import static org.junit.jupiter.api.Assertions.*;

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
			System.out.println(zufallszahl);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl <=bis);
		}
	}
	
	@Test
	@Order(6)
	void testGleichVerteilung() {
		int von=1;
		int bis=2;
		int zaehler1 = 0;
		int zaehler2 = 0;
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(von, bis);
		for(int i = 0; i<100;i++) 
		{
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			if(zufallszahl == 1) {
				zaehler1++;
			}
			if(zufallszahl == 2) {
				zaehler2++;
			}
		}
		double wkeitZahl1 = zaehler1/100;
		double wkeitZahl2 = zaehler2/100;
		assertTrue(wkeitZahl1 == wkeitZahl2);
	}
	
	
	

}
