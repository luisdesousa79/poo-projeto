package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {

	private boolean isDead;

	public GameCharacter(Room room) {
		super(room);
	}

	@Override
	public int getLayer() {
		return 2;
	}

	// inicialmente esta função fazia apenas movimentos aleatórios
	// agora implementa o movimento dos peixes controlado pelo jogador

	public void move(Vector2D dir) {

		Point2D destination = getPosition().plus(dir);

		// inspecionamos a posição de destino para ver se está vazia (é só água)
		// ouocupada
		// no caso de estar ocupada, se está ocupada com um objecto móvel ou imóvel

		// se é só água, o peixe pode mover-se para lá
		if (getRoom().isOnlyWaterAt(destination)) {
			setPosition(destination);
			return;
		}

		// se é um objecto imóvel (fixo)
		if (getRoom().hasImmovableAt(destination)) {

			
			// se o objeto no destino for uma HoledWall e o peixe for o pequeno
			if (getRoom().hasHoledWallAt(destination) && this instanceof SmallFish) {
				//se há uma taça na parede perfurada, tenta empurrar a taça
				if (getRoom().hasCupAt(destination)) {
					List<GameObject> objectsAtDestination = getRoom().getObjectsAt(destination);
					for (GameObject object : objectsAtDestination) {
						if(object instanceof Cup) {
							boolean pushed = ((Cup) object).push(dir);
							// caso o objeto tenha sido empurrado, move-se para a posição anterior dele
							if (pushed) {
								setPosition(destination);
							}
							break;
						}
					}
				}
				// se não tiver taça, o Peixe pequeno atravessa o buraco
				setPosition(destination);
				return;
			}
			
			

			// se for outro objeto imóvel, bloqueia movimento
			return;

		}

		// se for um objeto móvel
		if (getRoom().hasMovableAt(destination)) {

			// obtém a lista de objetos na posição de destino
			List<GameObject> objectsAtDestination = getRoom().getObjectsAt(destination);

			// percorre os objetos no destino
			for (GameObject object : objectsAtDestination) {

				// se o objeto no destino for a armadilha.
				if (object instanceof Trap) {

					// peixe pequeno atravessa a armadilha
					if (this instanceof SmallFish) {
						setPosition(destination);
						return;
					}

					// peixe grande morre ao colidir
					if (this instanceof BigFish) {
						dies();
						return;
					}

				}
			}

			// Caso não seja uma armadilha, vamos verificar se é um objecto empurrável
			Pushable pushableObject = null;

			// percorre a lista de objetos na posção de destino
			for (GameObject object : objectsAtDestination) {
				// se encontrar um objeto que possa ser empurrado (Pushable), guarda-o numa
				// variável
				if (object instanceof Pushable) {
					pushableObject = (Pushable) object;
					break;
				}
			}

			// se o objeto guardado for nulo, sai
			if (pushableObject == null) {
				return;
			}

			// pergunta ao peixe se o consegue empurrar
			if (!canPush(pushableObject, dir)) {
				return;
			}

			// chama a função push do objecto empurrado
			boolean pushed = pushableObject.push(dir);

			// caso o objeto tenha sido empurrado, move-se para a posição anterior dele
			if (pushed) {
				setPosition(destination);
			}
		}
	}

	// método abstrato do canPush, implementado nas classes filhas
	public abstract boolean canPush(Pushable object, Vector2D dir);

	// função que implementa a morte dos peixes
	public void dies() {
		getRoom().removeObject(this);
		this.isDead = true;
		// volta ao início do nível
	}

	// retorna verdadeiro se o peixe em causa morreu
	public boolean isDead() {
		return this.isDead;
	}

	public boolean canSupport() {
		List<MovableObject> supportedObjects = getRoom().getMovableObjectsAbove(this.getPosition());

		int numberHeavyObjects = 0;

		// percorremos a lista de objetos que o peixe está a suportar
		for (MovableObject m : supportedObjects) {

			// contamos quantos são pesados
			if (m instanceof HeavyObject)
				numberHeavyObjects++;
		}

		if (this instanceof SmallFish) {

			// peixe pequeno só pode suportar um objecto leve
			if (supportedObjects.size() > 1) {
				return false;
			}

			// não consegue suportar nenhum pesado
			if (numberHeavyObjects > 0) {
				return false;
			}
		}

		// se for o peixe grande

		if (this instanceof BigFish) {

			// só pode suportar 1 objeto pesado
			if (numberHeavyObjects > 1) {
				return false;
			}

			// o peixe grande pode suportar vários objetos leves 
			return true;
		}

		return true;
	}

}