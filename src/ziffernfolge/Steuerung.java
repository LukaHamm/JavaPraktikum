/**
Projekt: Ziffernreihen Spiel 
File: Steuerung.java
Author: Lukas Hammer
Last changed: 16.09.2022
**/
package ziffernfolge;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Steuerung {
	private Spielkonsole spielkonsole;
	// Zustaende
	private Start start = new Start();
	private Praesentation_Ziffernfolge praesentation_ziffernfolge = new Praesentation_Ziffernfolge();
	private Memorieren_Ziffernfolge memorieren_ziffernfolge = new Memorieren_Ziffernfolge();
	private Spiel_beendet spiel_beendet = new Spiel_beendet();
	private Anzeige_Bestenliste anzeige_Bestenliste = new Anzeige_Bestenliste();
	public Eingabe_Name eingabe_Name = new Eingabe_Name();
	public long startzeitInMs;
	private Bestenliste bestenliste;

	// aktueller Zustand der Spielkonsole.
	private Zustand zustand = start;

	/**
	 * Erzeugt eine Steuerung fuer eine Spielkonsole.
	 * 
	 * @param spielkonsole Spielkonsole, welche gesteuert werden soll.
	 */
	public Steuerung(Spielkonsole spielkonsole) {
		this.spielkonsole = spielkonsole;
	}

	/** Ereignis. Teilt der Steuerung mit, dass das Spiel gestartet worden ist. */
	public void spiel_gestartet() {
		zustand.spiel_gestartet();
	}

	/**
	 * Ereignis. Teilt der Steuerung mit, dass die Praesentation der Ziffernfolge
	 * beendet worden ist.
	 */
	public void praesentation_Ziffernfolge_beendet() {
		zustand.praesentation_Ziffernfolge_beendet();
	}

	/**
	 * Ereignis. Teilt der Steuerung mit, dass der Spieler eine Ziffer ausgewaehlt
	 * hat
	 */
	public void ziffer_ausgewaehlt(Ziffer ziffer) {
		zustand.ziffer_ausgewaehlt(ziffer);
	}
	
	/**
	 * Ereignis. Teilt der Steuerung mit, dass ein neues Spiel gestartet werden
	 * soll.
	 */
	public void neues_Spiel() {
		zustand.neues_Spiel();
	}

	/**
	 * Ereignis. Teilt der Steuerung mit, dass der Name eingegeben wurde. Es muss
	 * als nächstes die Bestenliste angezeigt werden.
	 */
	public void name_eingegeben() {
		zustand.name_eingegeben();
	}

	// abstrakte Zustandsklasse mit Standardverhalten
	private abstract class Zustand {
		public void spiel_gestartet() {
		};

		public void neues_Spiel() {
		};

		public void name_eingegeben() {
		};

		public void praesentation_Ziffernfolge_beendet() {
		};

		public void ziffer_ausgewaehlt(Ziffer ziffer) {
		};

		public void entry() {
		};

		public void exit() {
		};

		// Umschalten auf neuen Zustand
		public void naechster_Zustand(Zustand neuer_zustand) {
			exit();
			zustand = neuer_zustand;
			neuer_zustand.entry();
		}
	}

	// Zustand Start
	private class Start extends Zustand {
		@Override
		public void spiel_gestartet() {
			startzeitInMs = System.currentTimeMillis();
			naechster_Zustand(praesentation_ziffernfolge);
		}
	}

	// Zustand Praesentation_Ziffernfolge
	private class Praesentation_Ziffernfolge extends Zustand {
		@Override
		public void entry() {
			spielkonsole.starte_Praesentation_Ziffernfolge();
		}

		@Override
		public void praesentation_Ziffernfolge_beendet() {
			naechster_Zustand(memorieren_ziffernfolge);
		}
	}

	// Zustand Memorieren_Ziffernfolge
	private class Memorieren_Ziffernfolge extends Zustand {
		@Override
		public void ziffer_ausgewaehlt(Ziffer ziffer) {
			if (spielkonsole.ausgewaehlte_Ziffer_korrekt(ziffer) && !spielkonsole.alle_Ziffern_memoriert()) {
				ziffer.leuchte_gruen_auf();
				spielkonsole.naechste_Sollziffer();
				naechster_Zustand(memorieren_ziffernfolge);
				return;
			}
			if (spielkonsole.ausgewaehlte_Ziffer_korrekt(ziffer) && spielkonsole.alle_Ziffern_memoriert()) {
				ziffer.leuchte_gruen_auf();
				naechster_Zustand(praesentation_ziffernfolge);
				return;
			}
			if (!spielkonsole.ausgewaehlte_Ziffer_korrekt(ziffer)) { // Fehler
				ziffer.leuchte_rot_auf();
				naechster_Zustand(eingabe_Name);
				return;
			}
		}
	}

	// Zustand Spiel_beendet
	private class Spiel_beendet extends Zustand {
		@Override
		public void ziffer_ausgewaehlt(Ziffer ziffer) {
			naechster_Zustand(eingabe_Name);
		}

		@Override
		public void exit() {
			spielkonsole.beginne_neue_Ziffernfolge();
		}
	}

	private class Anzeige_Bestenliste extends Zustand {
		@Override
		public void entry() {
			bestenliste.zeige_Liste_an();
		}
		
		@Override
		public void neues_Spiel() {
			startzeitInMs = System.currentTimeMillis();
			naechster_Zustand(praesentation_ziffernfolge);
		}

		@Override
		public void exit() {
			spielkonsole.beginne_neue_Ziffernfolge();
			spielkonsole.sichtbar(true);
			bestenliste.sichtbar(false);
		}

	}

	private class Eingabe_Name extends Zustand {
		
		@Override
		public void entry() {
			spielkonsole.sichtbar(false);
			bestenliste.neues_Ergebnis(spielkonsole.laenge_Ziffernfolge(), spielkonsole.spielzeit());
			bestenliste.sichtbar(true);
			bestenliste.aktiviere_Namenseingabe();
			
		}

		@Override
		public void name_eingegeben() {
			naechster_Zustand(anzeige_Bestenliste);
		}

	}
	
	public void melde_An(Bestenliste bestenliste) {
		this.bestenliste = bestenliste;
	}

}