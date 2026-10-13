package model;

public enum Difficolta {
	
	FACILE(0.5, 3),
	NORMALE(1, 2),
	DIFFICILE(1.5, 1);
	
	private final double frequenzaFuocoNemici;
	private final int maxProiettileGiocatore;
	
	private Difficolta(double frequenzaFuocoNemici, int maxProiettileGiocatore) {
		this.frequenzaFuocoNemici = frequenzaFuocoNemici;
		this.maxProiettileGiocatore = maxProiettileGiocatore;
	}
}
