package model;

public class Giocatore extends OggettoDiGioco {
	
	private static final double LARGHEZZA_GIOCATORRE = 40;
	private static final double ALTEZZA = 30;
	private static final double VELOCITA = 10;	
    private int vite;
    
    public Giocatore(Punto2D posizione, int numVite) {
        super(posizione, LARGHEZZA_GIOCATORRE, ALTEZZA);
        this.vite = numVite;
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
