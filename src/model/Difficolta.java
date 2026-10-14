package model;

public enum Difficolta {
	
	FACILE(0.5, 3, 1),
	NORMALE(1, 2, 1),
	DIFFICILE(1.5, 1, 1);
	
	private final double frequenzaFuocoNemici;
	private final int maxProiettileGiocatore;
	private double velocitaProiettiliNemici;
	
	private Difficolta(double frequenzaFuocoNemici, int maxProiettileGiocatore, double velocitaProiettiliNemici) {
		this.frequenzaFuocoNemici = frequenzaFuocoNemici;
		this.maxProiettileGiocatore = maxProiettileGiocatore;
		this.velocitaProiettiliNemici = velocitaProiettiliNemici;
	}
}
