package objects;

import pt.iscte.poo.game.Room;

public class Bomb extends MovableObject {
	
	public Bomb(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "bomb";
	}

	@Override
	public int getLayer() {
		return 1; //verificar
	}

}
