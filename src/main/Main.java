package main;

import javax.swing.SwingUtilities;

import controller.GameController;
import model.Gioco;
import view.FinestraPrincipale;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Gioco gioco = new Gioco();
	        FinestraPrincipale finestra = new FinestraPrincipale(gioco);
	        new GameController(gioco, finestra).avvia();
	    });
	}
	
}
