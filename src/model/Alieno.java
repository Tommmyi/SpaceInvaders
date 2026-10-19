package model;

public class Alieno extends OggettoDiGioco{
	
	public static final double LARGHEZZA_ALIENO = 40;
	public static final double ALTEZZA_ALIENO = 30;
	
	private TipoAlieno tipo;

	public Alieno(Punto2D posizione, TipoAlieno tipo) {
		super(posizione, LARGHEZZA_ALIENO, ALTEZZA_ALIENO);
		this.tipo = tipo;
	}
	
	public double getPunti() {
		return this.tipo.getPunti();
	}
	
	public TipoAlieno getTipo() {
		return this.tipo;
	}
	
	public void sposta(double spostamentoX, double spostamentoY) {
		this.setPosizione(new Punto2D(this.getPosizione().getX()+spostamentoX, this.getPosizione().getY()+spostamentoY));
	}
	
	public boolean puoSparare() {				
		if(this.tipo == TipoAlieno.TIPO_2 && this.getStatoVita())
			return true;
		return false;		
	}
	
	public ProiettileNemico spara(double velocita) {		
		Punto2D puntoProiettile = new Punto2D(this.getPosizione().getX()/2, this.getPosizione().getY());
		return new ProiettileNemico(puntoProiettile, velocita);		
	}	
}
