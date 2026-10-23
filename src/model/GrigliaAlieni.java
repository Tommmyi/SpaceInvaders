package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GrigliaAlieni extends OggettoDiGioco{
	
	private static final int RIGHE = 5;
	private static final int COLONNE = 11;
	
	private static final double VELOCITA_PARTENZA=5;	//velocità di partenza degli alieni nella griglia
	private static final double VELOCITA_Y=20;			//velocità costante con cui gli alieni scendono ogni volta che raggiungono il bordo
	private static final double SPAZIO_X=20;			//spazio che separa gli alieni fra di loro sull'asse delle x
	private static final double SPAZIO_Y=15;			//spazio che separa gli alieni fra di loro sull'asse delle y
	
	private static final Punto2D POSIZIONE_PARTENZA = new Punto2D(50, 50);	//posizione di partenza della griglia
	private static final double	LARGHEZZA = COLONNE * Alieno.LARGHEZZA_ALIENO + (COLONNE - 1) * SPAZIO_X; //calcolata la larghezza della griglia
	private static final double ALTEZZA = RIGHE * Alieno.ALTEZZA_ALIENO + (RIGHE - 1) * SPAZIO_Y;	//calcolata l'altezza della griglia
		
	private Direzione direzione;		//direzione di spostamento della griglia di alieni
	private double velocita;			//velocità effettiva della griglia che aumenterà ad ogni nuova "orda"
	private List<List<Alieno>> griglia;	
	
	public GrigliaAlieni(Difficolta diff, int ondata) {
		super(POSIZIONE_PARTENZA, LARGHEZZA, ALTEZZA);
		this.direzione = Direzione.DESTRA;
		this.velocita = VELOCITA_PARTENZA + ondata;
		this.griglia = new ArrayList<>();		
		for(int a=1; a<=RIGHE; a++) {					//5
			List<Alieno> rigaAlieni = new ArrayList<Alieno>();
			for(int b=1; b<=COLONNE; b++) {				//11
				double xAlieno = POSIZIONE_PARTENZA.getX() + (b - 1) * (Alieno.LARGHEZZA_ALIENO + SPAZIO_X);	//coordinata x che sarà passata per la creazione degli alieni
				double yAlieno = POSIZIONE_PARTENZA.getY() + (a - 1) * (Alieno.ALTEZZA_ALIENO + SPAZIO_Y);
				rigaAlieni.add(new Alieno(new Punto2D(xAlieno, yAlieno), this.scegliTipo(diff,a)));
			}
			this.griglia.add(rigaAlieni);
		}		
	}
	
	private TipoAlieno scegliTipo(Difficolta diff, int riga) {	//Metodo che restituisce il tipo di alieno giusto per la creazione dell'Alieno
		
		if(diff == Difficolta.FACILE) {
			return TipoAlieno.TIPO_1;
		}else if(diff == Difficolta.NORMALE) {
			return (riga == 1)? TipoAlieno.TIPO_2 : TipoAlieno.TIPO_1;
		}else{
			if (riga <= 2) 
				return TipoAlieno.TIPO_2;
	        if (riga == 5) 
	        	return TipoAlieno.TIPO_3;
	        return TipoAlieno.TIPO_1;
		}		
	}
	
	private void scendi() {
		
		for(List<Alieno> lista : this.griglia) {
			for(Alieno a : lista) {
				if(a.getStatoVita()) {
					a.sposta(0, VELOCITA_Y);
				}
			}
		}		
	}
	
	private boolean toccaBordo(double larghezzaCampo) {	
		
		boolean toccato = false;
		
		for (List<Alieno> lista : this.griglia) {
	        for (Alieno a : lista) {
	        	if(a.getStatoVita()) {
	        		double sxAlieno = a.getPosizione().getX();
	        		double dxAlieno = a.getPosizione().getX()+a.getLarghezza();
	        		if(this.direzione==Direzione.SINISTRA && sxAlieno-this.velocita<=0)
	        			toccato = true;
	        		if(this.direzione==Direzione.DESTRA && dxAlieno+this.velocita>=larghezzaCampo)
	        			toccato = true;
	        	}
	        }
	    }		
		return toccato;		
	}
	
	public void muovi(double larghezzaCampo) {		
		
		if(this.toccaBordo(larghezzaCampo)) {   																				
			this.direzione = (this.direzione==Direzione.DESTRA) ? Direzione.SINISTRA : Direzione.DESTRA;
			this.scendi();
		}else {
			for(List<Alieno> lista : this.griglia) {					
				for(Alieno a : lista) {
					if(a.getStatoVita()){
						double spostamento = (this.direzione == Direzione.DESTRA) ? this.velocita : -this.velocita;
						a.sposta(spostamento, 0);
					}
				}
			}
		}		
	}
	
	
	
	public boolean raggiungeFondo(double fondoY) {
		
		boolean raggiunto=false;
		double baseAlieno=0;		
		for(List<Alieno> lista : this.griglia) {
			for(Alieno a : lista) {
				if(a.getStatoVita()) {
					baseAlieno=a.getPosizione().getY()+a.getAltezza();
					if(baseAlieno >= fondoY)
						raggiunto = true;
				}
			}
		}		
		return raggiunto;
	}
	
	public Alieno scegliTiratore() {
		
		int capacita=0;
		Random rnd = new Random();
		List<Alieno> listaTiratori = this.griglia.getFirst().stream()
				.filter(a->a.puoSparare())
				.toList();
		capacita=listaTiratori.size();		
		int tiratore = rnd.nextInt(capacita);		
		return listaTiratori.get(tiratore);
	}
	
	public boolean isDistrutta() {
		
		for(List<Alieno> lista : this.griglia) {
			for(Alieno a : lista) {
				if(a.getStatoVita())
					return false;		//se anche solo un alieno è vivo allora la griglia non è ancora totalmente distrutta
			}
		}	
		return true;
	}		
	
	public List<List<Alieno>> getGriglia(){
		List<List<Alieno>> grigliaPassata = this.griglia;
		return grigliaPassata;
	}
}




