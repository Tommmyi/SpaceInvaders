package model;

public class Scudo extends OggettoDiGioco{
	

	/*private int resistenza;
	
	public Scudo(Punto2D posizione, double larghezza, double altezza, int resistenza) {		
		super(posizione, larghezza, altezza);
		this.resistenza = resistenza;
	}
	public void danneggia() {
		this.resistenza--;
	}
	public boolean isDistrutto() {
		if(this.resistenza==0)
		return true;
		return false;
	}*/
	private static final double LARGHEZZA = 0;
	private static final double ALTEZZA = 0;
	private static final double RESISTENZA = 10.0;
	
	
	private Punto2D punto;
	private double resistenza ;
	
	public Scudo(Punto2D punto ) {		
		super(null, ALTEZZA, LARGHEZZA);
		this.resistenza=RESISTENZA;
	}
	public void danneggia() {
		this.resistenza--;
	}
	public boolean isDistrutto() {
		if(this.resistenza==0)
			return true;
		return false;
	}
}
