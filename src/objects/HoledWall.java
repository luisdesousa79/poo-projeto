package objects;

import pt.iscte.poo.game.Room;

public class HoledWall extends ImmovableObject {
	
	public HoledWall(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "holedWall";
	}

	@Override
	public int getLayer() {
		return 1; // verificar se está correcto
	}
	
	
	
}
