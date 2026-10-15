package model;

public enum TipoAlieno {
	TIPO_1(10,15, 15), //alieni normali
	TIPO_2(40, 15, 15),	//sparano
	TIPO_3(20, 15, 15);	//con scudo
	
	private double punti;
	private double larghezza;
	private double altezza;
	
	private TipoAlieno(double punti, double larghezza, double altezza) {
		this.punti=punti;
		this.larghezza=larghezza;
		this.altezza=altezza;
	}
	
	public double getPunti() {
		return this.punti;
	}
	
	public double getLarghezza() {
		return this.larghezza;
	}
	
	public double getAltezza() {
		return this.altezza;
	}
	
}
