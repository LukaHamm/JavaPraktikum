/**
Projekt: Ziffernreihen Spiel 
File: Spielekonsole.java
Author: Lukas Hammer
Last changed: 21.01.2023
**/
package ziffernfolge;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/** Spielkonsole fuer das Spiel "Ziffernfolge". 
Es besteht aus einem Feld von neun Ziffernfeldern, die in verschiedenen Farben
aufleuchten k�nnen. Der Spieler kan die Zifferfelder mit der Maus
anklicken, um die memorierte Ziffernreihe einzugeben.*/
@SuppressWarnings("serial")
public class Spielkonsole extends JPanel
{ private static final int abstand=5;
  
  /** Hoehe der Spielkonsole, wird automatisch so eingestellt, dass
      drei Ziffernfelder nebeneinander Platz haben.*/
  public static final int hoehe=abstand*4+Ziffer.groesse*3;;
  
  /** Breite der Spielkonsole, wird automatisch so eingestellt, dass
      drei Ziffernfelder �bereinander Platz haben.*/
  static final int breite=abstand*4+Ziffer.groesse*3;;
  
  private Ziffer[][] ziffern=new Ziffer[3][3];
  LinKongruenz zufallszahlen = new LinKongruenz();
//  private Random zufallszahlen=new Random(); // fuer die zuf�llige Auswahl neuer Ziffern
  private Liste ziffernfolge=new VerketteteListe(); // f�r die Verwaltung der Ziffernfolge
  private Liste.Iterator iterator_ziffernfolge=ziffernfolge.erzeuge_Iterator(); // f�r das Navigieren in der Ziffernfolge
  private Steuerung steuerung;  // f�r die Spielsteuerung
  
  /** Erzeugt eine neue Spielkonsole.
      Die Abmessungen werden automatisch so eingestellt, dass
      die neun Ziffernfelder genug Platz haben. Die Spielkonsole erzeugt
      eine Steuerung, die erlaubt, das Spiel zu kontrollieren. 
      Ist die Spielkonsole erzeugt, wird der Steuerung der Eintritt des 
      Ereignisses "Spiel gestartet" �bermittelt.*/
  public Spielkonsole()
  { this.setBorder(new LineBorder(new Color(0, 0, 0)));
    this.setBackground(SystemColor.controlHighlight);
    this.setSize(breite,hoehe); 
    this.setLayout(null);
    for(int i=0;i<3;i++)
    { for(int j=0;j<3;j++)
      { ziffern[i][j]=new Ziffer(this);
        int xpos=abstand+j*(abstand+Ziffer.groesse);
        int ypos=abstand+i*(abstand+Ziffer.groesse);
        ziffern[i][j].setLocation(xpos,ypos);
        this.add(ziffern[i][j]);
      } 
    }
  }
  
  /**
   * 
   * @return Die Spieldauer des jetzigen Spiels
   */
  public int spielzeit() {
	  return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - steuerung.startzeitInMs);
  }
  
  /**
   * Methode, um die Benutzeroberfläche der Spielekonsole zu verstecken (false) oder zu zeigen(true)
   * @param isSichtbar
   */
  public void sichtbar (boolean isSichtbar) {
		this.setVisible(isSichtbar);
	  
  }
  
  /**
   * 
   * @return Laenge der derzeitigen Ziffernfolge
   */
  public int laenge_Ziffernfolge () {
	  int laengeZiffernfolge = 0;
	  for(Liste.Iterator k = ziffernfolge.erzeuge_Iterator();!k.nach_ende();k.weiter()) {
		  ++laengeZiffernfolge;
	  }
	  --laengeZiffernfolge;
	  return laengeZiffernfolge;
  }
  
  /** Die aktuelle Ziffernreihe wird um eine Ziffer verlaengert und 
      ihre Praesentation gestartet.
      Dies bedeutet, dass die zugehoerigen Ziffernfelder 
      nacheinander  blau aufleuchten. Ist die Praesentation beendet, wird der
      Steuerung mitgeteilt, dass dieses Ereignis eingetreten ist.*/
  public void starte_Praesentation_Ziffernfolge()
  { int ziffer=zufallszahlen.naechste(1, 9);
    ziffernfolge.setze_an_Ende(ziffer);
    iterator_ziffernfolge.anfang();
    Thread t=new PraesentationsThread(this);
    t.start();
  }
    
  /** Die aktuelle Ziffernfolge wird geloescht und durch eine neue, noch 
      leere ersetzt. */
  public void beginne_neue_Ziffernfolge()
  { ziffernfolge=new VerketteteListe();
    iterator_ziffernfolge=ziffernfolge.erzeuge_Iterator();
  }
  
  /** Die n�chste Sollziffer wird f�r die n�chste �berpr�fung bereitgestellt. */
  public void naechste_Sollziffer()
  { if(!iterator_ziffernfolge.nach_ende()) iterator_ziffernfolge.weiter();
  }
  
  /** Teilt der Spielkonsole mit, dass der Spieler ein Ziffernfeld 
      mit der Maus angeklickt hat. Die Spielkonsole leitet dieses Ereignis an
      seine Steuerung weiter.
      @param ziffer Verweis auf das ausgew�hlte Ziffer-Objekt.*/
  public void Ziffer_ausgewaehlt(Ziffer ziffer)
  { steuerung.ziffer_ausgewaehlt(ziffer);
  }
  
  /** Es wird gepr�ft, ob die ausgewaehlte Ziffer korrekt ist.
      Die ausgewaehlte Ziffer wird mit der aktuellen Ziffer der Ziffernreihe
      verglichen. 
      @param ziffer Verweis auf das ausgewaehlte Ziffer-Objekt 
      @return true, wenn die Ziffer mit der aktuellen uebereinstimmt. */
  public boolean ausgewaehlte_Ziffer_korrekt(Ziffer ziffer)
  { int ziffer_ist=ziffer.wert();
    int ziffer_soll=(Integer)iterator_ziffernfolge.element();
    return ziffer_ist==ziffer_soll;
  }
  
  /** Prueft, ob die gesamte Ziffernreihe richtig memoriert worden ist.
      @return true, wenn die gesamte Ziffernreihe 
      richtig memoriert worden ist. */
  public boolean alle_Ziffern_memoriert()
  { iterator_ziffernfolge.weiter();
    boolean alle_memoriert=iterator_ziffernfolge.nach_ende();
    iterator_ziffernfolge.zurueck();
    return alle_memoriert;
  }
  
  /** Der Spielkonsole wird die Steuerung bekannt gemacht, mit der sie 
   * zusammenarbeiten soll.
   * @param steuerung  Steuerung  */
  public void melde_an(Steuerung steuerung) { this.steuerung=steuerung;}  
  
  private class PraesentationsThread extends Thread
  { private Spielkonsole spielkonsole;
    
    private PraesentationsThread(Spielkonsole spielkonsole)
    { this.spielkonsole=spielkonsole;
    }
    
    @Override
	public void run()
    { for(Liste.Iterator k=spielkonsole.ziffernfolge.erzeuge_Iterator();
          !k.nach_ende();k.weiter())
      { int ziffer=(Integer)k.element();
        int i=(ziffer-1)/3;
        int j=((ziffer)-1)%3;  
        try{Thread.sleep(2000);} catch(InterruptedException e){} 
        this.spielkonsole.ziffern[i][j].leuchte_blau_auf();
      }
      spielkonsole.iterator_ziffernfolge.anfang();
      steuerung.praesentation_Ziffernfolge_beendet();
    }
  }
}