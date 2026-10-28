package model;

public class Giocatore extends OggettoDiGioco {
	
	public static final double LARGHEZZA_GIOCATORE = 40;
	public static final double ALTEZZA = 30;
	
	public static final double VELOCITA = 5;	
    private int vite;
    
    public Giocatore(Punto2D posizione, int numVite) {
        super(posizione, LARGHEZZA_GIOCATORE, ALTEZZA);
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
    	if (this.vite > 0) {	
    		this.vite--;
    	}
        if (this.vite <= 0) {
            this.distruggi();
        }
    }

    public int getVite() {
        return vite;
    }
    
    public void azzeraVite() {
    	this.vite = 0;
    	this.distruggi();
    }
    
    public ProiettileGiocatore spara() {
    	//il proiettile parte dal centro del giocatore, appena sopra di esso (prima si sovrapponeva al giocatore)
    	double xProiettile = this.getPosizione().getX() + (this.getLarghezza() - ProiettileGiocatore.LARGHEZZA)/2;
    	double yProiettile = this.getPosizione().getY() - ProiettileGiocatore.ALTEZZA;
    	return new ProiettileGiocatore(new Punto2D(xProiettile, yProiettile));
    }
    
}
