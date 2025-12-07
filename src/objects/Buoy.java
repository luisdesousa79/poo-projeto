package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buoy extends LightObject {

	public Buoy(Room room) {
		super(room);

	}

	@Override
	public String getName() {
		return "buoy";
	}

	@Override
	public int getLayer() {
		return 1; 
	}

	@Override
	public void applyMovement() {
		buoys();
		sinks();
	}

	public void buoys() {
		// deve boiar se não tiver nenhum objeto móvel por cima
		Point2D above = getPosition().plus(Direction.UP.asVector());
		if (getRoom().isOnlyWaterAt(above)) {
			setPosition(above);
		}
	}

	public void sinks() {

		Point2D above = this.getPosition().plus(Direction.UP.asVector());

		// se tiver um objeto móvel em cima e água em baixo, desce de posição(afunda)
		if (getRoom().hasMovableAt(above)) {
			super.applyGravity();
		}
	}

}
