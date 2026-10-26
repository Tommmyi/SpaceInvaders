package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import model.Difficolta;
import model.Gioco;
import model.StatoGioco;
import view.FinestraPrincipale;

public class GameController {

    private static final int INTERVALLO_TICK = 16;   // ms, circa 60 tick al secondo

    private final Gioco gioco;
    private final FinestraPrincipale finestra;
    private final Timer timer;

    // stato dei tasti, letto a ogni tick
    private boolean sinistra, destra, spazio;

    public GameController(Gioco gioco, FinestraPrincipale finestra) {
        this.gioco = gioco;
        this.finestra = finestra;
        this.timer = new Timer(INTERVALLO_TICK, e -> tick());

        // navigazione tra le schermate
        finestra.getPannelloMenu().setListenerGioca(
                e -> finestra.mostra(FinestraPrincipale.DIFFICOLTA));
        finestra.getPannelloDifficolta().setListenerDifficolta(this::avviaPartita);
        finestra.getPannelloDifficolta().setListenerIndietro(
                e -> finestra.mostra(FinestraPrincipale.MENU));
        finestra.getPannelloFine().setListenerRiprova(
                e -> finestra.mostra(FinestraPrincipale.MENU));

        // tastiera
        finestra.getPannelloGioco().addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e)  { impostaTasto(e.getKeyCode(), true); }
            @Override 
            public void keyReleased(KeyEvent e) { impostaTasto(e.getKeyCode(), false); }
        });
    }

    public void avvia() {
        finestra.mostra(FinestraPrincipale.MENU);
        finestra.setVisible(true);
    }

    private void avviaPartita(Difficolta difficolta) {
        gioco.avvia(difficolta);
        sinistra = destra = spazio = false;
        finestra.mostra(FinestraPrincipale.GIOCO);
        timer.start();
    }

    private void impostaTasto(int codice, boolean premuto) {
        if (codice == KeyEvent.VK_LEFT)  sinistra = premuto;
        if (codice == KeyEvent.VK_RIGHT) destra = premuto;
        if (codice == KeyEvent.VK_SPACE) spazio = premuto;
    }

    private void tick() {
        // 1. input -> comandi al model
        if (sinistra) gioco.muoviGiocatoreSinistra();
        if (destra)   gioco.muoviGiocatoreDestra();
        if (spazio)   gioco.sparaGiocatore();

        // 2. il model avanza di un tick
        gioco.aggiorna();

        // 3. la view ridisegna
        finestra.getPannelloGioco().repaint();

        // 4. se la partita Ã¨ finita, mostra l'esito
        StatoGioco stato = gioco.getStato();
        if (stato == StatoGioco.VITTORIA || stato == StatoGioco.GAME_OVER) {
            timer.stop();
            finestra.getPannelloFine().mostraEsito(
                    stato == StatoGioco.VITTORIA, gioco.getPunteggio(), gioco.getRecord());
            finestra.mostra(FinestraPrincipale.FINE);
        }
    }
}