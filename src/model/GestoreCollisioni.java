package model;

import java.util.List;

public class GestoreCollisioni {

	public void risolvi(Gioco gioco) {

		//Controllo se il giocatore collide con un proiettile alieno e se il giocatore collide con un alieno della griglia:
		for(ProiettileNemico p : gioco.getSpariNemici()) {
			if(p.getStatoVita() && gioco.getGiocatore().interseca(p)) {
				gioco.getGiocatore().perdiVita();
				p.distruggi();
			}
		}
		for(List<Alieno> lista : gioco.getGrigliaAlieni().getGriglia()) {
			for(Alieno a : lista) {
				if(a.getStatoVita() && gioco.getGiocatore().interseca(a)) {
					gioco.getGiocatore().azzeraVite();
				}
			}
		}

		//Controllo se lo scudo interseca con un proiettile o con un alieno della griglia:
		for(Scudo s : gioco.getScudi()) {
			if(s.getStatoVita()){
				//altrimenti un proiettile già "morto" danneggiava lo scudo più volte
				for(Proiettile p1 : gioco.getSpariNemici()) {
					if(s.getStatoVita() && p1.getStatoVita() && s.interseca(p1)) {
						s.danneggia();
						p1.distruggi();
					}
				}

				for(Proiettile p2 : gioco.getSpariGiocatore()) {
					if(s.getStatoVita() && p2.getStatoVita() && s.interseca(p2)) {
						s.danneggia();
						p2.distruggi();
					}
				}

				for(List<Alieno> lista : gioco.getGrigliaAlieni().getGriglia()) {
					for(Alieno a : lista) {
						if(a.getStatoVita() && s.interseca(a)) {
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
					if(p.getStatoVita() && a.getStatoVita() && p.interseca(a)) {
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
