package model;

public class Scudo extends OggettoDiGioco{
	
	public static final double Y_SCUDO = Gioco.ALTEZZA_CAMPO-130;
	
	public static final double LARGHEZZA = 75;
	public static final double ALTEZZA = 40;
	private static final double RESISTENZA = 10.0;
	
	private double vitaScudo ;
	
	public Scudo(Punto2D punto ) {		
		super(punto, LARGHEZZA, ALTEZZA);
		this.vitaScudo=RESISTENZA;
	}
	public void danneggia() {
		this.vitaScudo--;
		if(this.vitaScudo<=0)
			this.distruggi();
	}
	
	public void azzeraVitaScudo() {
		this.vitaScudo = 0;
		this.distruggi();
	}
	public double getVitaScudo() {
		return vitaScudo;
	}
}
