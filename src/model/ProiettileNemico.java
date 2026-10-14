package model;

public class ProiettileNemico extends Proiettile{
	
	private static final double LARGHEZZA = 6;
    private static final double ALTEZZA = 16;
	
	public ProiettileNemico(Punto2D posizione, double velocita) {
		super(posizione, LARGHEZZA, ALTEZZA, velocita);
	}
	
	public void muovi() {	// Metodo per muovere il proiettile nemico verso il basso
		double spostamento = this.getPosizione().getY()+this.getVelocita();
		this.getPosizione().setY(spostamento);		
	}
	
}
