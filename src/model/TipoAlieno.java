package model;

public enum TipoAlieno {
	TIPO_1(10, 1), //alieni normali
	TIPO_2(40, 1),	//sparano
	TIPO_3(20, 2);	//con scudo
	
	private double punti;
	private int vita;
	
	private TipoAlieno(double punti, int vita) {
		this.punti=punti;
		this.vita=vita;
	}
	
	public double getPunti() {
		return this.punti;
	}	
	
	public int getVita() {
		return this.vita;
	}
}
