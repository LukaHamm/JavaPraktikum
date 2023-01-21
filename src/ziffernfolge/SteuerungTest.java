/**
Projekt: Ziffernreihen Spiel 
File: Steuerung.java
Author: Lukas Hammer
Last changed: 21.01.2023
**/
package ziffernfolge;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class SteuerungTest {

	private static Steuerung steuerung;
	private static Spielkonsole spielkonsole;
	private static Bestenliste bestenliste;
	private static long startzeit;
	
	@BeforeAll
	public static void init () {
		spielkonsole = new Spielkonsole();
		steuerung = new Steuerung(spielkonsole);
		bestenliste = new Bestenliste(steuerung);
		steuerung.melde_An(bestenliste);
		spielkonsole.melde_an(steuerung);
	}

	@Test
	@Order(1)
	public void testSpielGestartet() {
		int laengeZiffernFolge = spielkonsole.laenge_Ziffernfolge();
		spielkonsole.melde_an(steuerung);
		steuerung.melde_An(bestenliste);
		steuerung.spiel_gestartet();
		int laengeZiffernfolgeNachPraesentation = spielkonsole.laenge_Ziffernfolge();
		assertTrue("Spielekonsole nicht sichtbar",spielkonsole.isVisible());
		assertTrue("Ziffernfolge nicht erweitert",(laengeZiffernfolgeNachPraesentation - laengeZiffernFolge) == 1);
		steuerung.praesentation_Ziffernfolge_beendet();
	}

	@Test
	@Order(2)
	public void testFalschGeraten() {
		Ziffer ziffer = new Ziffer(spielkonsole);
		spielkonsole.Ziffer_ausgewaehlt(ziffer);
		assertTrue("Spielekonsole ist sichtbar",!spielkonsole.isVisible());
	}
	
	@Test
	@Order(3)
	public void testEingabeName() {
		bestenliste.aktiviere_Namenseingabe();
		startzeit = steuerung.startzeitInMs;
		steuerung.name_eingegeben();
		assertTrue("Anzahl richtig geraten falsch",spielkonsole.laenge_Ziffernfolge() ==0);
	}
	
	@Test
	@Order(4)
	public void testNeuesSpiel() {
		steuerung.neues_Spiel();
		assertTrue("Startzeit nicht zurückgesetzt",startzeit < steuerung.startzeitInMs);
		assertTrue("Anzahl richtig geraten falsch", spielkonsole.laenge_Ziffernfolge() == 0);
	}
	
	

}
