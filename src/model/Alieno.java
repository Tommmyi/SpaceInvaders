package model;

public class Alieno extends OggettoDiGioco{
	
	public static final double LARGHEZZA_ALIENO = 40;
	public static final double ALTEZZA_ALIENO = 30;
	
	private TipoAlieno tipo;	
	private int vitaAlieno;	

	public Alieno(Punto2D posizione, TipoAlieno tipo) {
		super(posizione, LARGHEZZA_ALIENO, ALTEZZA_ALIENO);
		this.tipo = tipo;
		this.vitaAlieno = this.tipo.getVita();
	}
	
	public void danneggia() {
		this.vitaAlieno--;
		if(this.vitaAlieno<=0) {
			this.distruggi();
		}
	}
	
	public double getPunti() {
		return this.tipo.getPunti();
	}
	
	public TipoAlieno getTipo() {
		return this.tipo;
	}
	public int getVitaAlieno() {
		return vitaAlieno;
	}	
	
	public void sposta(double spostamentoX, double spostamentoY) {
		this.setPosizione(new Punto2D(this.getPosizione().getX()+spostamentoX, this.getPosizione().getY()+spostamentoY));
	}
	
	public boolean puoSparare() {				
		return this.tipo == TipoAlieno.TIPO_2 && this.getStatoVita();		
	}
	
	public ProiettileNemico spara(double velocita) {		
		double xProiettile = this.getPosizione().getX() + (this.getLarghezza() - ProiettileNemico.LARGHEZZA)/2;
		double yProiettile = this.getPosizione().getY() + this.getAltezza();
		return new ProiettileNemico(new Punto2D(xProiettile, yProiettile), velocita);	
	}
}
