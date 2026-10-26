package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Gioco;



public class FinestraPrincipale extends JFrame{
	
	public static final String MENU = "MENU";
    public static final String DIFFICOLTA = "DIFFICOLTA";
    public static final String GIOCO = "GIOCO";
    public static final String FINE = "FINE";

    private final CardLayout carte = new CardLayout();
    private final JPanel contenitore = new JPanel(carte);

    private final PannelloMenu pannelloMenu = new PannelloMenu();
    private final PannelloDifficolta pannelloDifficolta = new PannelloDifficolta();
    private final PannelloGioco pannelloGioco;
    private final PannelloFine pannelloFine = new PannelloFine();

    public FinestraPrincipale(Gioco gioco) {
        super("Space Invaders");
        this.pannelloGioco = new PannelloGioco(gioco);

        this.contenitore.add(pannelloMenu, MENU);
        this.contenitore.add(pannelloDifficolta, DIFFICOLTA);
        this.contenitore.add(pannelloGioco, GIOCO);
        this.contenitore.add(pannelloFine, FINE);

        this.add(contenitore);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();                         // dimensione presa dai pannelli (800x600)
        this.setLocationRelativeTo(null);    // finestra centrata sullo schermo
    }

    public void mostra(String nome) {
    	this.carte.show(contenitore, nome);
        if (nome.equals(GIOCO)) {
        	this.pannelloGioco.requestFocusInWindow();   // senza i tasti non arrivano al pannello
        }
    }

    public PannelloMenu getPannelloMenu() { 
    	return this.pannelloMenu; 
    }
    public PannelloDifficolta getPannelloDifficolta() { 
    	return this.pannelloDifficolta; 
    }
    public PannelloGioco getPannelloGioco() { 
    	return this.pannelloGioco; 
    }
    public PannelloFine getPannelloFine() { 
    	return this.pannelloFine; 
    }
	 
}
	
