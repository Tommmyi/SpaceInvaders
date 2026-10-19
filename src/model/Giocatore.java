package model;

public class Giocatore extends OggettoDiGioco {
    private int vite;
    private static final double VELOCITA = 10;
    public Giocatore(Punto2D posizione, double larghezza, double altezza, int viteIniziali) {
        super(posizione, larghezza, altezza);
        this.vite = viteIniziali;
    }

    public void muoviSinistra() {
    	double nuovaX = this.getPosizione().getX() - VELOCITA;
        this.getPosizione().setX(nuovaX);
    }

    public void muoviDestra() {
    	double nuovaX = this.getPosizione().getX() + VELOCITA;
    	this.getPosizione().setX(nuovaX);
    }

    public void perdiVita() {
        this.vite--;
        if (this.vite <= 0) {
            this.distruggi();  
        }
    }

    public int getVite() {
        return vite;
    }
}
