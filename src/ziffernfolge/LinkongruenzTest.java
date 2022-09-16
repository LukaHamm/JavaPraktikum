/**
Projekt: Ziffernreihen Spiel 
File: LinKongruenzTest.java
Author: Lukas Hammer
Last changed: 16.06.2022
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
			assertTrue(zufallszahl < standardZahlenweite);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste();
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl < bis);
		}
		
	}
	
	@Test
	@Order(2)
	void testNaechsteMitParametern() {
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(von, bis);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl < bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorKonstruktorMitParameter.naechste(von,bis);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl < bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(-von, bis);
			assertTrue(zufallszahl >=0);
			assertTrue(zufallszahl < bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(bis, von);
			assertTrue(zufallszahl >=von);
			assertTrue(zufallszahl < bis);
		}
		
		for(int i = 0; i<100;i++) {
			int zufallszahl = zufallsGeneratorDefaultKonstruktor.naechste(0, 0);
			assertTrue(zufallszahl >=0);
			assertTrue(zufallszahl < 100);
		}
	}
	@Test
	@Order(3)
	void testKonstruktorMitParametern() {
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(bis,von);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardVon() == von);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardZahlenweite() == (bis-von));
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(0,von);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardVon() == 0);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardZahlenweite() == von);
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(bis,-von);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardVon() == 0);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardZahlenweite() == (bis));
		zufallsGeneratorKonstruktorMitParameter = new LinKongruenz(0,0);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardVon() == 0);
		assertTrue(zufallsGeneratorKonstruktorMitParameter.getStandardZahlenweite() == 100);
	}
	

}
