package model;

public enum Difficolta {
	
	FACILE(0.5, 2),
	NORMALE(1, 3),
	DIFFICILE(1.5, 4);
	
	private final double frequenzaFuocoNemici;
	private final double velocitaProiettiliNemici;
	
	private Difficolta(double frequenzaFuocoNemici, double velocitaProiettiliNemici) {
		this.frequenzaFuocoNemici = frequenzaFuocoNemici;
		this.velocitaProiettiliNemici = velocitaProiettiliNemici;
	}
	
	public double getFrequenzaFuocoNemici() {
		return this.frequenzaFuocoNemici;
	}
	
	public double getVelocitaProiettiliNemici() {
		return this.velocitaProiettiliNemici;
	}
}
