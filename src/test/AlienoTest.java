package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Alieno;
import model.ProiettileNemico;
import model.Punto2D;
import model.TipoAlieno;

class AlienoTest {

	private static final double DELTA = 1e-9;

	private Alieno crea(TipoAlieno tipo) {
		return new Alieno(new Punto2D(0, 0), tipo);
	}	
	
	@Test
	void morteAlieno() {
	    Alieno a1 = crea(TipoAlieno.TIPO_1);
	    a1.danneggia();
	    assertFalse(a1.getStatoVita());
	    Alieno a3 = crea(TipoAlieno.TIPO_3);
	    a3.danneggia();
	    assertTrue(a3.getStatoVita());
	    assertEquals(1, a3.getVitaAlieno());
	    a3.danneggia();
	    assertFalse(a3.getStatoVita());
	}
	

	@Test
	void puntiDipendonoDalTipo() {
		assertEquals(10, crea(TipoAlieno.TIPO_1).getPunti(), DELTA);
		assertEquals(40, crea(TipoAlieno.TIPO_2).getPunti(), DELTA);
		assertEquals(20, crea(TipoAlieno.TIPO_3).getPunti(), DELTA);
	}

	@Test
	void spostaCambiaLaPosizione() {
		Alieno a = new Alieno(new Punto2D(10, 20), TipoAlieno.TIPO_1);
		a.sposta(5, -3);
		assertEquals(15, a.getPosizione().getX(), DELTA);
		assertEquals(17, a.getPosizione().getY(), DELTA);
	}

	@Test
	void soloTipo2VivoPuoSparare() {
		Alieno tiratore = crea(TipoAlieno.TIPO_2);
		assertTrue(tiratore.puoSparare());
		assertFalse(crea(TipoAlieno.TIPO_1).puoSparare());
		assertFalse(crea(TipoAlieno.TIPO_3).puoSparare());
		tiratore.distruggi();
		assertFalse(tiratore.puoSparare());
	}

	@Test
	void partenzaProiettileAlieno() {//il proiettile deve partire dal centro dell'alieno
		Alieno a = new Alieno(new Punto2D(100, 200), TipoAlieno.TIPO_2);
		ProiettileNemico p = a.spara(3);
		double xAttesa = 100 + (Alieno.LARGHEZZA_ALIENO - ProiettileNemico.LARGHEZZA) / 2;
		assertEquals(xAttesa, p.getPosizione().getX(), DELTA);
		assertEquals(200 + Alieno.ALTEZZA_ALIENO, p.getPosizione().getY(), DELTA);
		assertEquals(3, p.getVelocita(), DELTA);
		assertTrue(p.getStatoVita());
	}
}
