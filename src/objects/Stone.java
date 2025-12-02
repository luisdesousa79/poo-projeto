package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Stone extends HeavyObject {
	
	// controla se o caranguejo ja nasceu
	private boolean hasSpawnedCrab = false;
	
	public Stone(Room room) {
		super(room);
	}
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 1; //verificar se está correcto
	}
	
	@Override
    public boolean push(Vector2D dir) {
        // guarda a posição antiga antes de tentar mover
        Point2D oldPos = getPosition();

        // tenta mover a pedra
        boolean moved = super.push(dir);

        // se a pedra se moveu e o movimento foi horizontal e ele ainda nao spownou, spawna o caranguejo
        if (moved && dir.getY() == 0 && !hasSpawnedCrab) {
            spawnCrab(oldPos);
        }

        return moved;
    }
	
	private void spawnCrab(Point2D stoneOriginalPos) {
        
		// pega o local que o caranguejo vai nascer
        Point2D spawnPos = getPosition().plus(pt.iscte.poo.utils.Direction.UP.asVector());

        // verifica se nao tem nada la
        if (getRoom().isOnlyWaterAt(spawnPos)) {
            Crab babyCrab = new Crab(getRoom());
            babyCrab.setPosition(spawnPos);
            getRoom().addObject(babyCrab); 
            // spawnou o caranguejo
            hasSpawnedCrab = true;
        }
    }
	
}
