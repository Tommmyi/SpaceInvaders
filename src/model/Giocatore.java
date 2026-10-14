package model;

public class Giocatore extends OggettoDiGioco {
    private int vite;
    private double velocità;
    public Giocatore(Punto2D posizione, double larghezza, double altezza, int viteIniziali) {
        super(posizione, larghezza, altezza);
        this.vite = viteIniziali;
        this.velocità=5.0;
    }

    public void muoviSinistra() {
    	double nuovaX = this.getPosizione().getX() - this.velocità;
        this.getPosizione().setX(nuovaX);
    }

    public void muoviDestra() {
    	double nuovaX = this.getPosizione().getX() + this.velocità;
    	this.getPosizione().setX(nuovaX);
    }

    /*public ProiettileGiocatore spara() {
        return null; // Sostituire con il proiettile effettivo
    }

    public boolean puoSparare() {
        return true; 
    }*/

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
