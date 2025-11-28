package objects;

import pt.iscte.poo.game.Room;

public class SteelHorizontal extends ImmovableObject {

	public SteelHorizontal(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "steelHorizontal";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
