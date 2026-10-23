package model;
import java.util.ArrayList;
import java.util.List;
public class Gioco {
	
	public static final double LARGHEZZA_CAMPO = 800;
	public static final double ALTEZZA_CAMPO = 600;
    private static final double MARGINE_FONDO = 30;
    
    private static final double TICK_FRA_SPARI = 60;	//Num di tick fra lo uno sparo di un alieno all'altro
    private static final int NUM_SCUDI = 4;
    //private static final int NUM_VITE = 3;

    private int ondate;
    
    private int viteGiocatore;
	private int punteggio;								
	private int record;
	private StatoGioco stato;							
	private Difficolta difficolta;			 			
	
	private Giocatore mioGiocatore;						
    private List<Scudo> scudi;
    private GrigliaAlieni miaGriglia;
    private List<ProiettileGiocatore> spariGiocatore;
    private List<ProiettileNemico> spariNemico;
    
    private GestoreCollisioni gestoreCollisioni;
    private int tickDaUltimoSparo;	//Quanti tick di timer dall'ultimo sparo di un alieno
	
	public Gioco() {
		this.stato = StatoGioco.MENU;
		this.record=0;
	}
	
	public void avvia(Difficolta difficolta) {
		
		this.difficolta = difficolta;
		this.tickDaUltimoSparo = 0;
		this.punteggio=0;
		//this.record=0;		
		this.ondate=0;
		this.viteGiocatore = (this.difficolta == Difficolta.DIFFICILE)? 1 : 3;	//assegno il numero di vite in base alla difficoltà
		
		double xGiocatore = (LARGHEZZA_CAMPO-Giocatore.LARGHEZZA_GIOCATORRE)/2;
		double yGiocatore = ALTEZZA_CAMPO-Giocatore.ALTEZZA-MARGINE_FONDO;
		this.mioGiocatore = new Giocatore(new Punto2D(xGiocatore, yGiocatore), viteGiocatore);		
		this.miaGriglia = new GrigliaAlieni(difficolta, this.ondate);		
		this.scudi = new ArrayList<>();
		double passo = LARGHEZZA_CAMPO/NUM_SCUDI;	//distanza fra gli scudi
		for(int a=0; a<=NUM_SCUDI; a++) {
			double xScudo = (passo*a)+(passo-Scudo.LARGHEZZA)/2; 	//va a centrare ogni scudo nella sua parte di schermo
			this.scudi.add(new Scudo(new Punto2D(xScudo, Scudo.Y_SCUDO)));
		}	
		this.spariGiocatore = new ArrayList<>();
		this.spariNemico = new ArrayList<>();
		
		this.stato = StatoGioco.IN_GIOCO;		
	}
	
	public void aggiorna() {
		
		if(this.stato != StatoGioco.IN_GIOCO)
			return;
		
		//muove gli alieni ed i proiettili già esistenti:
		this.movimenti();
		
		//cerca l'alieno che sarà selezionato per creare un proiettile:
		this.fuocoAlieno();
		
		//gestisco le collisioni:
		this.gestoreCollisioni = new GestoreCollisioni();
		gestoreCollisioni.risolvi(this);
		
		//pulisco le liste:
		this.pulisciListeProiettili();
		
		//se la griglia è stata distrutta:
		this.nuovaOndata();
		
		//fine partita:
		this.finePartita();
		
	}
	
	private void movimenti() {
		this.miaGriglia.muovi(LARGHEZZA_CAMPO);
		for(Proiettile p : this.spariNemico)
			p.muovi();
		for(Proiettile p : this.spariGiocatore)
			p.muovi();
	}
	
	
	private void fuocoAlieno() {
		this.tickDaUltimoSparo++;
		if(this.tickDaUltimoSparo >= TICK_FRA_SPARI/difficolta.getFrequenzaFuocoNemici()) {
			Alieno tiratore = this.miaGriglia.scegliTiratore();
			this.spariNemico.add(tiratore.spara(this.difficolta.getVelocitaProiettiliNemici()));
			tickDaUltimoSparo=0;
		}
			
	}
	
	
	private void pulisciListeProiettili() {
		for(Proiettile p : this.spariNemico) {
			if( !p.getStatoVita() || p.getPosizione().getY()>ALTEZZA_CAMPO)
				this.spariNemico.remove(p);
		}
		for(Proiettile p : this.spariGiocatore) {
			if( !p.getStatoVita() || p.getPosizione().getY()<0 )
				this.spariNemico.remove(p);
		}
	}
	
	
	private void nuovaOndata() {		
		if(this.miaGriglia.isDistrutta()) {
			this.ondate++;
			this.miaGriglia = new GrigliaAlieni(this.difficolta, this.ondate);
		}		
	}
	
	
	private void finePartita() {
		if(!this.mioGiocatore.getStatoVita() || this.miaGriglia.raggiungeFondo(this.mioGiocatore.getPosizione().getY())) {
			if(this.record > this.punteggio) {
				this.record = this.punteggio;
				this.stato = StatoGioco.VITTORIA;
			}else {
				this.stato = StatoGioco.GAME_OVER;
			}
		}
	}
	
	//getter:
	public int getPunteggio() {
		return this.punteggio;
	}	
	public int getRecord() {
		return this.record;
	}
	public int getViteGiocatore() {
		return this.viteGiocatore;
	}
	public Giocatore getGiocatore() {
		return this.mioGiocatore;
	}	
	public GrigliaAlieni getGrigliaAlieni() {
		return this.miaGriglia;
	}	
	public List<Scudo> getScudi(){
		/*List<Scudo> lista = this.scudi;
		return lista;*/
		return this.scudi;
	}	
	public List<ProiettileNemico> getSpariNemici(){
		/*List<ProiettileNemico> lista = this.spariNemico;
		return lista;*/
		return this.spariNemico;
	}	
	public List<ProiettileGiocatore> getSpariGiocatore(){
		/*List<ProiettileGiocatore> lista = this.spariGiocatore;
		return lista;*/
		return this.spariGiocatore;
	}
	
	
	public void incrementaPunteggio(double incremento) {
		this.punteggio+=incremento;
	}
}
