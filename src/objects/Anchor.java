package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Anchor extends HeavyObject {
	
	public Anchor(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "anchor";
	}

	@Override
	public int getLayer() {
		return 1; //verificar se está correcto
	}
	
	@Override
	public boolean push(Vector2D dir) {

	    Point2D dest = getPosition().plus(dir);

	    // Só se move se a posição seguinte na direção do empurrão for água
	    if (getRoom().isOnlyWaterAt(dest)) {
	        setPosition(dest);
	        return true;
	    }

	    // 3) Se houver qualquer objeto na frente a âncora não empurra
	    return false;
	}

}
