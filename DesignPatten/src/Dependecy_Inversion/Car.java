package Dependecy_Inversion;

//Car class take Cast by any type (toshiba ,sony or any type you want )
public class Car {
	
	private final ICast cast;

	public Car(ICast cast) {
		this.cast = cast;
	}

}