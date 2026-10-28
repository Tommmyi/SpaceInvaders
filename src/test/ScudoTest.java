package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import model.*;
import org.junit.jupiter.api.Test;

class ScudoTest {

	private static final double DELTA = 1e-9;
	private static final double RESISTENZA = Scudo.RESISTENZA;

	@Test
	void resistenzaScudo() {
		Scudo s = new Scudo(new Punto2D(0, Scudo.Y_SCUDO));
		for (int i = 1; i < RESISTENZA; i++) {
			s.danneggia();
			assertTrue(s.getStatoVita(), "lo scudo deve resistere al colpo " + i);
		}
		s.danneggia();
		assertFalse(s.getStatoVita());
		assertEquals(0, s.getVitaScudo(), DELTA);
	}

	@Test
	void azzeraVitaDistruggeScudo() {
		Scudo s = new Scudo(new Punto2D(0, Scudo.Y_SCUDO));
		s.azzeraVitaScudo();
		assertFalse(s.getStatoVita());
		assertEquals(0, s.getVitaScudo(), DELTA);
	}
}
