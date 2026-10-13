package main;
import model.OggettoDiGioco;
import model.Punto2D;

public class ProveConsole {
	
public static void main(String[] args) {
		
		// Test :	
		Punto2D p1 = new Punto2D(3,2);
		Punto2D p2 = new Punto2D(3,9);
		Punto2D p3 = new Punto2D(4,1);
		OggettoDiGioco ogg1 = new OggettoDiGioco(p1,2,2);
		OggettoDiGioco ogg2 = new OggettoDiGioco(p2,1,1);	//Non interseca ogg1
		OggettoDiGioco ogg3 = new OggettoDiGioco(p3,3,2);	//Interseca ogg2 
	
		System.out.println("deve stampare 3 e 2: "+p1.getX()+" "+p1.getY());
		System.out.println("deve stampare 3 e 9: "+p2.getX()+" "+p2.getY());
		
		// test del metodo interseca:
		if(!ogg1.interseca(ogg2)) // qua 
			System.out.println("funziona");
		else
			System.out.println("non funziona");
		
		if(ogg1.interseca(ogg3))
			System.out.println("funziona");
		else
			System.out.println("non funziona");	
		
	
	}
	
}
