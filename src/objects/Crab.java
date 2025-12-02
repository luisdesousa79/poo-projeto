package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Crab extends MovableObject implements Sinkable {

	private boolean justSpawned = true;
	
	public Crab(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "krab"; 
	}

	@Override
	public int getLayer() {
		return 1; // investigar qual o layer correto
	}
	
	// nao deixa o caranguejo ser empurado
	@Override
	public boolean push(Vector2D dir) {
		return false; // O enunciado diz que não pode ser empurrado
	}
	
	
	@Override
	public void sinks() {
		Point2D below = this.getPosition().plus(Direction.DOWN.asVector());

		// afunda se n tiver nd em baixo
		if (getRoom().isOnlyWaterAt(below)) {
			this.setPosition(below);
		}
	}
	
	public void moveEnemy() {
		
		// faz com que ele so se mova no turno depois de nascer
		if (justSpawned) {
            justSpawned = false; 
            return; 
        }
		
		// escolher uma direção horizontal aleatória 
		double random = Math.random();
		Direction dir = (random < 0.5) ? Direction.LEFT : Direction.RIGHT;
		Point2D newPos = getPosition().plus(dir.asVector());

		// ve se o movimento é válido
		if (isValidMovement(newPos)) {
				
			// se se moveu para um peixe grande, o caranguejo morre
			if (isBigFishAt(newPos)) {
				getRoom().removeObject(this);
				return; // Morreu, não se mexe mais
			}
				
			// se se moveu para o peixe pequeno, mata ele
			if (isSmallFishAt(newPos)) {
				SmallFish.getInstance().dies();
			}
			
			// se movimenta
			setPosition(newPos);
		}
	}

	private boolean isValidMovement(Point2D p) {
		// não sai do mapa
		if (p.getX() < 0 || p.getX() >= 10) return false;

		// se tiver um objeto imovel que nao seja a parede com buracos nao move
		if (getRoom().hasImmovableAt(p)) {
			if (getRoom().hasHoledWallAt(p)) {
				return true;
			}
			return false; 
		}

		// nao se move para objetos moveis exeto os peixes
		if (getRoom().hasMovableAt(p)) {
			if (isSmallFishAt(p) || isBigFishAt(p)) return true;
			return false;
		}
		// caminho livre
		return true; 
	}
		
	private boolean isSmallFishAt(Point2D p) {
		return SmallFish.getInstance().getPosition().equals(p);
	}
		
	private boolean isBigFishAt(Point2D p) {
		return BigFish.getInstance().getPosition().equals(p);
	}
	
}


