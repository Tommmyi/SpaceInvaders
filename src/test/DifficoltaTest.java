package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import model.Difficolta;

class DifficoltaTest {

	private static final double DELTA = 1e-9;

	@Test
	void valoriDelleDifficolta() {
		assertEquals(0.5, Difficolta.FACILE.getFrequenzaFuocoNemici(), DELTA);
		assertEquals(2, Difficolta.FACILE.getVelocitaProiettiliNemici(), DELTA);
		assertEquals(1, Difficolta.NORMALE.getFrequenzaFuocoNemici(), DELTA);
		assertEquals(3, Difficolta.NORMALE.getVelocitaProiettiliNemici(), DELTA);
		assertEquals(1.5, Difficolta.DIFFICILE.getFrequenzaFuocoNemici(), DELTA);
		assertEquals(4, Difficolta.DIFFICILE.getVelocitaProiettiliNemici(), DELTA);
	}
}
