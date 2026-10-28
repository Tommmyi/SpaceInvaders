package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import model.*;

class OggettoDiGiocoTest {

	private static final double DELTA = 1e-9;

	private OggettoDiGioco crea(double x, double y, double larghezza, double altezza) {
		return new OggettoDiGioco(new Punto2D(x, y), larghezza, altezza);
	}

	@Test
	void controlloNuovoOggetto() {
		OggettoDiGioco ogg = crea(1, 2, 3, 4);
		assertTrue(ogg.getStatoVita());
		assertEquals(3, ogg.getLarghezza(), DELTA);
		assertEquals(4, ogg.getAltezza(), DELTA);
		assertEquals(12, ogg.getArea(), DELTA);
	}

	@Test
	void distruggiRendeOggettoNonVivo() {
		OggettoDiGioco ogg = crea(0, 0, 1, 1);
		ogg.distruggi();
		assertFalse(ogg.getStatoVita());
	}

	@Test
	void setPosizioneCambiaPosizione() {
		OggettoDiGioco ogg = crea(0, 0, 1, 1);
		Punto2D nuovo = new Punto2D(10, 20);
		ogg.setPosizione(nuovo);
		assertSame(nuovo, ogg.getPosizione());
	}

	@Test
	void oggettiSovrappostiIntersecano() {
		OggettoDiGioco a = crea(3, 2, 2, 2);
		OggettoDiGioco b = crea(4, 1, 3, 2);
		assertTrue(a.interseca(b));
		assertTrue(b.interseca(a));
	}

	@Test
	void oggettiStaccatiNonIntersecano() {
		OggettoDiGioco a = crea(3, 2, 2, 2);
		OggettoDiGioco b = crea(3, 9, 1, 1);
		assertFalse(a.interseca(b));
		assertFalse(b.interseca(a));
	}

	@Test
	void oggettiCheSiToccanoSulBordoIntersecano() {
		OggettoDiGioco a = crea(0, 0, 10, 10);
		OggettoDiGioco b = crea(10, 0, 10, 10);
		assertTrue(a.interseca(b));
		assertTrue(b.interseca(a));
	}

}
