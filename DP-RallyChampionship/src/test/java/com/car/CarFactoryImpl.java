package com.car;

/**
 * The Car Factory Implementation.
 */
public class CarFactoryImpl implements CarFactory {

	/**
	 * Instantiates a new car factory impl.
	 */
	public CarFactoryImpl() {
	}

	@Override
	public Car citroenC5() {
		return new ResistantCar("Citröen C5", Speed.RAPIDA, Fuel.ELEFANTE);
	}

	@Override
	public Car citroenC4() {
		return new FastCar("Citröen C4", Speed.RAPIDA, Fuel.ESCASO);
	}

	@Override
	public Car citroenC3() {
		return new Car("Citröen C3", Speed.RAPIDA, Fuel.ESCASO);
	}

	@Override
	public Car seatTarraco() {
		return new ResistantCar("Seat Tarraco", Speed.TORTUGA, Fuel.GENEROSO);
	}

	@Override
	public Car seatAteca() {
		return new FastCar("Seat Ateca", Speed.GUEPARDO, Fuel.GENEROSO);
	}

	@Override
	public Car seatArona() {
		return new Car("Seat Arona", Speed.RAPIDA, Fuel.ESCASO);
	}

	@Override
	public Car peugeot5008() {
		return new ResistantCar("Peugeot 5008", Speed.LENTA, Fuel.GENEROSO);
	}

	@Override
	public Car peugeot3008() {
		return new FastCar("Peugeot 3008", Speed.GUEPARDO, Fuel.NORMAL);
	}

	@Override
	public Car peugeot2008() {
		return new Car("Peugeot 2008", Speed.NORMAL, Fuel.ESCASO);
	}
}
