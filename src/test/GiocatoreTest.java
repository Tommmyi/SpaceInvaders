package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.*;

class GiocatoreTest {

	private static final double DELTA = 1e-9;
	private static final double VELOCITA = Giocatore.VELOCITA;
	private Giocatore giocatore;

	@BeforeEach
	void setUp() {
		giocatore = new Giocatore(new Punto2D(100, 500), 3);
	}

	@Test
	void giocatoreVitaDimensione() {
		assertEquals(3, giocatore.getVite());
		assertTrue(giocatore.getStatoVita());
		assertEquals(Giocatore.LARGHEZZA_GIOCATORE, giocatore.getLarghezza(), DELTA);
		assertEquals(Giocatore.ALTEZZA, giocatore.getAltezza(), DELTA);
	}

	@Test
	void muoviSinistraDestra() {
		giocatore.muoviSinistra();
		assertEquals(100 - VELOCITA, giocatore.getPosizione().getX(), DELTA);
		giocatore.muoviDestra();
		giocatore.muoviDestra();
		assertEquals(100 + VELOCITA, giocatore.getPosizione().getX(), DELTA);
		assertEquals(500, giocatore.getPosizione().getY(), DELTA);
	}

	@Test
	void perdiVitaToglieUnaVita() {
		giocatore.perdiVita();
		assertEquals(2, giocatore.getVite());
		assertTrue(giocatore.getStatoVita());
	}

	@Test
	void perdiVitaPuoUccidere() {
		giocatore.perdiVita();
		giocatore.perdiVita();
		giocatore.perdiVita();
		assertEquals(0, giocatore.getVite());
		assertFalse(giocatore.getStatoVita());
		giocatore.perdiVita();
		assertEquals(0, giocatore.getVite());	//le vite non diventano negative
	}

	@Test
	void azzeraViteUccide() {
		giocatore.azzeraVite();
		assertEquals(0, giocatore.getVite());
		assertFalse(giocatore.getStatoVita());
	}

	@Test
	void sparaCreaProiettile() {
		ProiettileGiocatore p = giocatore.spara();
		double xAttesa = 100 + (Giocatore.LARGHEZZA_GIOCATORE - ProiettileGiocatore.LARGHEZZA) / 2;
		assertEquals(xAttesa, p.getPosizione().getX(), DELTA);
		assertEquals(500 - ProiettileGiocatore.ALTEZZA, p.getPosizione().getY(), DELTA);
		assertTrue(p.getStatoVita());
	}
}
