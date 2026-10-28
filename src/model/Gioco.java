package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
public class Gioco {
	
	public static final int VITE_FACILE_NORMALE = 3;
	public static final int VITE_DIFFICILE = 1;
	public static final double LARGHEZZA_CAMPO = 800;
	public static final double ALTEZZA_CAMPO = 600;
    private static final double MARGINE_FONDO = 30;
    private static final int TICK_FRA_SPARI_GIOCATORE = 20;
    
    private static final double TICK_FRA_SPARI = 60;	//Num di tick fra lo uno sparo di un alieno all'altro
    public static final int NUM_SCUDI = 4;
 

    private int ondate;
    
	private int punteggio;								
	private int record;
	private StatoGioco stato;							
	private Difficolta difficolta;			 			
	
	private Giocatore mioGiocatore;						
    private List<Scudo> scudi;
    private GrigliaAlieni miaGriglia;
    private List<ProiettileGiocatore> spariGiocatore;
    private List<ProiettileNemico> spariNemico;
    private int viteGiocatore;
    private int tickDaUltimoSparoGiocatore;
    
    private GestoreCollisioni gestoreCollisioni;
    private int tickDaUltimoSparo;	//Quanti tick di timer dall'ultimo sparo di un alieno
	
	public Gioco() {
		this.stato = StatoGioco.MENU;
		this.record=0;
		this.gestoreCollisioni = new GestoreCollisioni();
		this.scudi=new ArrayList<>();
		this.spariGiocatore=new ArrayList<>();
		this.spariNemico=new ArrayList<>();
	}
	
	public void avvia(Difficolta difficolta) {
		
		this.difficolta = Objects.requireNonNull(difficolta); //la difficoltà non può essere null
		this.tickDaUltimoSparo = 0;
		this.punteggio=0;		
		this.ondate=0;
		this.viteGiocatore = (this.difficolta == Difficolta.DIFFICILE)? VITE_DIFFICILE : VITE_FACILE_NORMALE;	//assegno il numero di vite in base alla difficoltà
		this.tickDaUltimoSparoGiocatore = TICK_FRA_SPARI_GIOCATORE;
		
		double xGiocatore = (LARGHEZZA_CAMPO-Giocatore.LARGHEZZA_GIOCATORE)/2;
		double yGiocatore = ALTEZZA_CAMPO-Giocatore.ALTEZZA-MARGINE_FONDO;
		this.mioGiocatore = new Giocatore(new Punto2D(xGiocatore, yGiocatore), viteGiocatore);		
		this.miaGriglia = new GrigliaAlieni(difficolta, this.ondate);		
		this.scudi = new ArrayList<>();
		double passo = LARGHEZZA_CAMPO/NUM_SCUDI;	//distanza fra gli scudi
		for(int a=0; a<NUM_SCUDI; a++) {
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
		this.tickDaUltimoSparoGiocatore++;
		
		//muove gli alieni ed i proiettili già esistenti:
		this.movimenti();
		
		//cerca l'alieno che sarà selezionato per creare un proiettile:
		this.fuocoAlieno();
		
		//gestisco le collisioni:
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
		if(this.tickDaUltimoSparo >= TICK_FRA_SPARI/this.difficolta.getFrequenzaFuocoNemici()) {
			Optional<Alieno> tiratore = this.miaGriglia.scegliTiratore();
			tiratore.ifPresent(a -> this.spariNemico.add(a.spara(this.difficolta.getVelocitaProiettiliNemici())));
			this.tickDaUltimoSparo = 0;
		}

	}
	
	
	private void pulisciListeProiettili() {
		this.spariNemico.removeIf(p -> !p.getStatoVita() || p.getPosizione().getY() > ALTEZZA_CAMPO);
		this.spariGiocatore.removeIf(p -> !p.getStatoVita() || p.getPosizione().getY() < 0);
	}
	
	
	private void nuovaOndata() {		
		if(this.miaGriglia.isDistrutta()) {
			this.ondate++;
			this.miaGriglia = new GrigliaAlieni(this.difficolta, this.ondate);
		}		
	}
	
	
	private void finePartita() {
		if(!this.mioGiocatore.getStatoVita() || this.miaGriglia.raggiungeFondo(this.mioGiocatore.getPosizione().getY())) {
			if(this.punteggio > this.record) {
				this.record = this.punteggio;
				this.stato = StatoGioco.VITTORIA;
			}else {
				this.stato = StatoGioco.GAME_OVER;
			}
		}
	}
	
	//Metodi per le azioni del giocatore(chiamate dal controller)
	public void muoviGiocatoreSinistra() {
	    this.muoviGiocatore(Direzione.SINISTRA);
	}

	public void muoviGiocatoreDestra() {
	    this.muoviGiocatore(Direzione.DESTRA);
	}

	private void muoviGiocatore(Direzione direzione) {
	    if(this.stato != StatoGioco.IN_GIOCO)
	        return;

	    if(direzione == Direzione.SINISTRA)
	        this.mioGiocatore.muoviSinistra();
	    else
	        this.mioGiocatore.muoviDestra();

	    // il giocatore non può uscire dal campo
	    Punto2D posizione = this.mioGiocatore.getPosizione();
	    double xMassima = LARGHEZZA_CAMPO - this.mioGiocatore.getLarghezza();
	    posizione.setX(Math.max(0, Math.min(posizione.getX(), xMassima)));
	}
	
	public boolean sparaGiocatore() {	
		if(this.stato != StatoGioco.IN_GIOCO || this.tickDaUltimoSparoGiocatore < TICK_FRA_SPARI_GIOCATORE)
			return false;
		this.spariGiocatore.add(this.mioGiocatore.spara());
		this.tickDaUltimoSparoGiocatore = 0;
		return true;
	}
	
	
	//getter:
	public int getPunteggio() {
		return this.punteggio;
	}	
	public int getRecord() {
		return this.record;
	}
	public Difficolta getDifficolta() {
		return this.difficolta;
	}

	public int getOndate() {	
		return this.ondate;
	}
	public int getViteGiocatore() {
		return (this.mioGiocatore == null)? 0 : this.mioGiocatore.getVite();
	}
	public Giocatore getGiocatore() {
		return this.mioGiocatore;
	}	
	public GrigliaAlieni getGrigliaAlieni() {
		return this.miaGriglia;
	}	
	public List<Scudo> getScudi(){
		return this.scudi;
	}
	public StatoGioco getStato() {
		return this.stato;
	}
	public List<ProiettileNemico> getSpariNemici(){
		return this.spariNemico;
	}	
	public List<ProiettileGiocatore> getSpariGiocatore(){
		return this.spariGiocatore;
	}
	
	
	public void incrementaPunteggio(double incremento) {
		this.punteggio+= (int)incremento;
	}
}