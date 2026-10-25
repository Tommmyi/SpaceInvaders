package model;

import java.util.List;

public class GestoreCollisioni {

	public void risolvi(Gioco gioco) {
		
		//Controllo se il giocatore collide con un proiettile alieno e se il giocatore collide con un alieno della griglia:
		for(ProiettileNemico p : gioco.getSpariNemici()) {
			if(gioco.getGiocatore().interseca(p) && p.getStatoVita()) {
				gioco.getGiocatore().perdiVita();
				p.distruggi();				
			}
		}
		for(List<Alieno> lista : gioco.getGrigliaAlieni().getGriglia()) {
			for(Alieno a : lista) {
				if(gioco.getGiocatore().interseca(a) && a.getStatoVita()) {
					gioco.getGiocatore().azzeraVite();
				}
			} 
		}
		
		//Controllo se lo scudo interseca con un proiettile o con un alieno della griglia:
		for(Scudo s : gioco.getScudi()) {
			if(s.getStatoVita()){
				for(Proiettile p1 : gioco.getSpariNemici()) {
					if(s.interseca(p1)) {
						s.danneggia();
						p1.distruggi();
					}				
				}
			
				for(Proiettile p2 : gioco.getSpariGiocatore()) {
					if(s.interseca(p2)) {
						s.danneggia();
						p2.distruggi();
					}
				}
			
				for(List<Alieno> lista : gioco.getGrigliaAlieni().getGriglia()) {
					for(Alieno a : lista) {
						if(s.interseca(a) && a.getStatoVita()) {
							s.azzeraVitaScudo();
						}
					}
				}
			}
		}
		//Controllo se i proiettili del giocatore colpiscano un alieno della griglia:
		for(Proiettile p : gioco.getSpariGiocatore()) {
			for(List<Alieno> lista : gioco.getGrigliaAlieni().getGriglia()) {
				for(Alieno a : lista) {
					if(p.interseca(a) && a.getStatoVita()/**/&& p.getStatoVita()) {
						a.danneggia();
						p.distruggi();   
						if(!a.getStatoVita()) {
							gioco.incrementaPunteggio(a.getTipo().getPunti());	//punti ottenuti alla distruzione dell'alieno
						}
					}						
				}
			}
		}
	}
	
}
