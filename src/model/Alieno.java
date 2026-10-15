package model;

public class Alieno extends OggettoDiGioco{
	
	private TipoAlieno tipo;
	
	public Alieno(Punto2D posizione, TipoAlieno tipo) {
		super(posizione, tipo.getLarghezza(), tipo.getAltezza());
		this.tipo = tipo;
	}
	
	public double getPunti() {
		return this.tipo.getPunti();
	}
	
	public TipoAlieno getTipo() {
		return this.tipo;
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
