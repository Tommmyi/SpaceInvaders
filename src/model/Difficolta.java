package model;

public enum Difficolta {
	
	FACILE(0.5, 1),
	NORMALE(1, 1),
	DIFFICILE(1.5, 1);
	
	private final double frequenzaFuocoNemici;
	private double velocitaProiettiliNemici;
	
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
