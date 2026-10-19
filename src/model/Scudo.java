package model;

public class Scudo extends OggettoDiGioco{
	
	private static final double LARGHEZZA = 75;
	private static final double ALTEZZA = 40;
	private static final double RESISTENZA = 10.0;
	
	private Punto2D punto;
	private double vitaScudo ;
	
	public Scudo(Punto2D punto ) {		
		super(null, ALTEZZA, LARGHEZZA);
		this.vitaScudo=RESISTENZA;
	}
	public void danneggia() {
		this.vitaScudo--;
	}
	
	public boolean isDistrutto() {
		return this.vitaScudo == 0;
	}
}
