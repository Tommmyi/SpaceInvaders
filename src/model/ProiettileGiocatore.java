package model;

public class ProiettileGiocatore extends Proiettile{
	
	private static final double VELOCITA_PROIETTILE_GIOCATORE = 5;
	private static final double LARGHEZZA = 6;
    private static final double ALTEZZA = 16;
	
	public ProiettileGiocatore(Punto2D posizione) {
		super(posizione, LARGHEZZA, ALTEZZA, VELOCITA_PROIETTILE_GIOCATORE );
	}	
	
	public void muovi() { //metodo per muovere il proiettile del giocatore verso l'alto
		double spostamento = this.getPosizione().getY()-this.getVelocita();
		this.getPosizione().setY(spostamento);
	}
	
}
