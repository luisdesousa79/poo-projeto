package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Crab extends GameObject implements Sinkable {

	public Crab(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "krab"; 
	}

	@Override
	public int getLayer() {
		return 2; // investigar qual o layer correto
	}
	
	@Override
	public void sinks() {

		Point2D above = this.getPosition().plus(Direction.UP.asVector());
		Point2D below = this.getPosition().plus(Direction.DOWN.asVector());

		// se tiver água em baixo, desce de posição(afunda)
		if (getRoom().hasMovableAt(above)) {
			if (getRoom().isOnlyWaterAt(below)) {
				this.setPosition(below);
			}
		}
	}
	
	public void move() {
		
	}
	
}


