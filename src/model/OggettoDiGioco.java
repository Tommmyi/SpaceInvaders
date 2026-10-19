package model;

public class OggettoDiGioco {
	
	private Punto2D posizione;
	private double larghezza;
	private double altezza;
	private boolean vivo;
	
	public OggettoDiGioco(Punto2D posizione, double larghezza, double altezza) {
		this.posizione=posizione;
		this.larghezza=larghezza;
		this.altezza=altezza;
		this.vivo=true;
	}
	
	public double getLarghezza() {
		return this.larghezza;
	}
	
	public double getAltezza() {
		return this.altezza;
	}
	
	public double getArea() {
		return this.altezza*this.larghezza;
	}
	
	public boolean getStatoVita() {
		return this.vivo;
	}
	
	public Punto2D getPosizione() { 
		return this.posizione;
	}
	
	public void setPosizione(Punto2D nuovoPunto) {
		this.posizione = nuovoPunto;
	}
	
	public boolean interseca(OggettoDiGioco ogg) {
		
		double questoX1 = this.posizione.getX();
		double questoX2 = this.posizione.getX()+this.larghezza;
		double questoY1 = this.posizione.getY();
		double questoY2 = this.posizione.getY()+this.altezza;
		
		double altroX1 = ogg.posizione.getX();
		double altroX2 = ogg.posizione.getX()+this.larghezza;
		double altroY1 = ogg.posizione.getY();
		double altroY2 = ogg.posizione.getY()+this.altezza;
		
		if (questoX2 < altroX1 || // Questo è completamente a sinistra di altro
		        questoX1 > altroX2 || // Questo è completamente a destra di altro
		        questoY2 < altroY1 || // Questo è completamente sopra di altro (assumendo Y che cresce verso il basso)
		        questoY1 > altroY2) {  // Questo è completamente sotto di altro
		        
			return false; // Nessuna collisione
		}
		// Se nessuna delle condizioni sopra è vera, allora si intersecano!
		return true;
		}
	
		public void distruggi() {	//Metodo per impostare lo stato della vita dell'oggetto di gioco da vivo a morto
			this.vivo = false;
		}
	
}
