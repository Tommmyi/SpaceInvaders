package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.*;

class GiocoTest {

	private static final double DELTA = 1e-9;
	private static final int NUM_SCUDI = Gioco.NUM_SCUDI;
	private Gioco gioco;

	@BeforeEach
	void setUp() {
		gioco = new Gioco();
	}

	@Test
	void partenzaGioco() {
		assertEquals(StatoGioco.MENU, gioco.getStato());
		assertEquals(0, gioco.getRecord());
		assertEquals(0, gioco.getPunteggio());
		assertEquals(0, gioco.getViteGiocatore());
		assertNull(gioco.getGiocatore());
	}

	@Test
	void nelMenuComandiNonFannoNulla() {
		assertDoesNotThrow(() -> {
			gioco.aggiorna();
			gioco.muoviGiocatoreDestra();
		});
		assertFalse(gioco.sparaGiocatore());
		assertEquals(StatoGioco.MENU, gioco.getStato());
	}
	
	@Test
	void ricominciaPartita() {
	    gioco.avvia(Difficolta.NORMALE);	   
	    gioco.getGiocatore().azzeraVite();
	    gioco.aggiorna();
	    assertEquals(StatoGioco.GAME_OVER, gioco.getStato());
	    gioco.avvia(Difficolta.FACILE);
	    assertEquals(StatoGioco.IN_GIOCO, gioco.getStato());
	    assertTrue(gioco.getGiocatore().getStatoVita());
	    assertTrue(gioco.getGiocatore().getVite() > 0);
	    gioco.incrementaPunteggio(50);
	    gioco.getGiocatore().azzeraVite();
	    gioco.aggiorna();
	    assertEquals(StatoGioco.VITTORIA, gioco.getStato());
	    gioco.avvia(Difficolta.DIFFICILE);
	    assertEquals(StatoGioco.IN_GIOCO, gioco.getStato());
	    assertTrue(gioco.getGiocatore().getStatoVita());
	    assertTrue(gioco.getGiocatore().getVite() > 0);
	}
	
	@Test
	void avviaSenzaDifficoltaLanciaEccezione() {
		assertThrows(NullPointerException.class, () -> gioco.avvia(null));
	}

	@Test
	void avviaPreparaPartita() {
		gioco.avvia(Difficolta.NORMALE);
		assertEquals(StatoGioco.IN_GIOCO, gioco.getStato());
		assertEquals(Difficolta.NORMALE, gioco.getDifficolta());
		assertEquals(0, gioco.getPunteggio());
		assertEquals(0, gioco.getOndate());
		assertEquals(Gioco.VITE_FACILE_NORMALE, gioco.getViteGiocatore());
		assertTrue(gioco.getSpariGiocatore().isEmpty());
		assertTrue(gioco.getSpariNemici().isEmpty());
		assertFalse(gioco.getGrigliaAlieni().isDistrutta());
	}

	@Test
	void creazioniScudi() {//deve creare 4 scudi non sovrapposti
		gioco.avvia(Difficolta.NORMALE);
		List<Scudo> scudi = gioco.getScudi();
		assertEquals(NUM_SCUDI, scudi.size());
		for (Scudo s : scudi) {
			assertTrue(s.getPosizione().getX() >= 0);
			assertTrue(s.getPosizione().getX() + s.getLarghezza() <= Gioco.LARGHEZZA_CAMPO);
			assertEquals(Scudo.Y_SCUDO, s.getPosizione().getY(), DELTA);
			assertFalse(s.interseca(gioco.getGiocatore()));
		}
		for (int i = 0; i < scudi.size() - 1; i++) {
			assertFalse(scudi.get(i).interseca(scudi.get(i + 1)));
		}
	}

	@Test
	void giocatoreNonEsceDalCampo() {
		gioco.avvia(Difficolta.NORMALE);
		for (int i = 0; i < 500; i++) {
			gioco.muoviGiocatoreSinistra();
		}
		assertEquals(0, gioco.getGiocatore().getPosizione().getX(), DELTA);
		for (int i = 0; i < 500; i++) {
			gioco.muoviGiocatoreDestra();
		}
		assertEquals(Gioco.LARGHEZZA_CAMPO - Giocatore.LARGHEZZA_GIOCATORE, gioco.getGiocatore().getPosizione().getX(), DELTA);
	}

	@Test
	void rimozioneProiettili() {
		gioco.avvia(Difficolta.NORMALE);
		for (int i = 0; i < 3; i++) {
			gioco.getSpariNemici().add(new ProiettileNemico(new Punto2D(0, Gioco.ALTEZZA_CAMPO + 1), 3));
			gioco.getSpariGiocatore().add(new ProiettileGiocatore(new Punto2D(0, -1)));
		}
		ProiettileNemico distrutto = new ProiettileNemico(new Punto2D(700, 300), 3);
		distrutto.distruggi();
		gioco.getSpariNemici().add(distrutto);
		ProiettileNemico inCampo = new ProiettileNemico(new Punto2D(700, 300), 3);
		gioco.getSpariNemici().add(inCampo);

		assertDoesNotThrow(() -> gioco.aggiorna());

		assertEquals(List.of(inCampo), gioco.getSpariNemici());
		assertTrue(gioco.getSpariGiocatore().isEmpty());
	}
	
	@Test
	void incrementaPunteggio() {
		gioco.avvia(Difficolta.NORMALE);
		gioco.incrementaPunteggio(40);
		gioco.incrementaPunteggio(10);
		assertEquals(50, gioco.getPunteggio());
	}

	@Test
	void velocitaNuovaOndata() {
		gioco.avvia(Difficolta.NORMALE);
		double velocitaIniziale = gioco.getGrigliaAlieni().getVelocita();
		gioco.getGrigliaAlieni().getGriglia().forEach(riga -> riga.forEach(Alieno::distruggi));
		gioco.aggiorna();
		assertEquals(1, gioco.getOndate());
		assertFalse(gioco.getGrigliaAlieni().isDistrutta());
		assertTrue(gioco.getGrigliaAlieni().getVelocita() > velocitaIniziale);
		assertEquals(StatoGioco.IN_GIOCO, gioco.getStato());
	}

	@Test
	void aggiornaFinePartita() {//il metodo aggiorna non deve cambiare nulla alla fine della partita
		gioco.avvia(Difficolta.NORMALE);
		gioco.getGiocatore().azzeraVite();
		gioco.aggiorna();
		double xAlieno = gioco.getGrigliaAlieni().getGriglia().get(0).get(0).getPosizione().getX();
		gioco.aggiorna();
		gioco.muoviGiocatoreDestra();
		assertEquals(StatoGioco.GAME_OVER, gioco.getStato());
		assertEquals(xAlieno, gioco.getGrigliaAlieni().getGriglia().get(0).get(0).getPosizione().getX(), DELTA);
		assertFalse(gioco.sparaGiocatore());
	}

}
