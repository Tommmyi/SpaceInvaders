package model;

public enum TipoAlieno {
	TIPO_1(10), //alieni normali
	TIPO_2(40),	//sparano
	TIPO_3(20);	//con scudo
	
	private double punti;
	private double larghezza;
	private double altezza;
	
	private TipoAlieno(double punti) {
		this.punti=punti;
	}
	
	public double getPunti() {
		return this.punti;
	}	
}
