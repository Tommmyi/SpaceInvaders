package model;

public abstract class Proiettile extends OggettoDiGioco{
	
	private double velocita;
	
	public Proiettile(Punto2D posizione, double larghezza, double altezza, double velocita) {
		super(posizione, larghezza, altezza);
		this.velocita = velocita;
	}
	
	public abstract void muovi();
	
	public double getVelocita() {
		return this.velocita;
	}
	
}
