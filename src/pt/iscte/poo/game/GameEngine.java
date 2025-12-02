package pt.iscte.poo.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import objects.BigFish;
import objects.Door;
import objects.GameCharacter;
import objects.GameObject;
import objects.SmallFish;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;


public class GameEngine implements Observer {

	private Map<String, Room> rooms;
	private Room currentRoom;
	private int lastTickProcessed = 0;

	private boolean smallFishExited = false;
    private boolean bigFishExited = false;

	public GameEngine() {
		// mapa que associa cada nome de ficheiro "room?.txt" a um objeto Room
		rooms = new HashMap<String, Room>();
		loadGame();
		// define o nível inicial atriuir a currentRoom o Room lido a partir de "room0.txt"
		currentRoom = rooms.get("room0.txt");
		updateGUI();
		//SmallFish.getInstance().setRoom(currentRoom);
		//BigFish.getInstance().setRoom(currentRoom);
		SmallFish sf = SmallFish.getInstance();
		sf.setRoom(currentRoom);
		sf.setPosition(currentRoom.getSmallFishStartingPosition());
		currentRoom.addObject(sf);
		
		BigFish bf = BigFish.getInstance();
		bf.setRoom(currentRoom);
		bf.setPosition(currentRoom.getBigFishStartingPosition());
		currentRoom.addObject(bf);
		
	}
	
	private void loadGame() {
		//cria uma lista com todos os ficheiros que estão na pasta "./rooms"
		File[] files = new File("./rooms").listFiles();
		// itera sobre os ficheiros 
		// e preenche o dicionário rooms com a associação entre o nome dos ficheiros
		// e o objeto Room lido a partir deles
		for (File f : files) {
			rooms.put(f.getName(), Room.readRoom(f, this));
		}
	}

	// carrega o nível seguinte
	private void loadNextLevel() {
		
		// pega a room atual
		String roomAtual = currentRoom.getName();
			
		// corta os 4 caracteres de cada ponta deixando só o número da room atual
		String numRoomAtualStr = roomAtual.substring(4, roomAtual.length() - 4);
				
		// converte para int
		int numRoomAtual = Integer.parseInt(numRoomAtualStr);
				
		// define a proxima room
		int numRoomSeguinte = numRoomAtual + 1;
		String roomSeguinte = "room" + numRoomSeguinte + ".txt";

		// vê se há a próxima room
		if (rooms.containsKey(roomSeguinte)) {
					
			// limpa a tela
			ImageGUI.getInstance().clearImages();
					
			// passa de room
			currentRoom = rooms.get(roomSeguinte);
					
			// põe de novo os peixes na room
			SmallFish.getInstance().setRoom(currentRoom);
			BigFish.getInstance().setRoom(currentRoom);
			
			// redireciona os peixes para o posicão inicial daquela room
			SmallFish.getInstance().setPosition(currentRoom.getSmallFishStartingPosition());
			BigFish.getInstance().setPosition(currentRoom.getBigFishStartingPosition());
			
			// volta a adicionar os peixes à room que está a ser carregada
			currentRoom.addObject(SmallFish.getInstance());
			currentRoom.addObject(BigFish.getInstance());
			
			
					
			updateGUI();
					
			ImageGUI.getInstance().setStatusMessage("Room " + numRoomSeguinte);
					
		} else {
			// se n tiver mais rooms, venceu tudo
			ImageGUI.getInstance().setStatusMessage("Acabaram todas as rooms. Parabens voce venceu!!!! :)");
		}
		
	}

	// este é o método que trata das teclas e dos ticks
	@Override
	public void update(Observed source) {
		// pergunta à ImageGUI se alguma tecla foi pressionada
		if (ImageGUI.getInstance().wasKeyPressed()) {
			// lê a tecla carregada
			int k = ImageGUI.getInstance().keyPressed();

			// se carregarmos na tecla "espaço" (código 32), muda-se de peixe
			if (k == 32) {
				currentRoom.nextFish();
			}

			// se se carregar numa tecla de direcção, movimenta-se o peixe actual
			if (Direction.isDirection(k)) {
				GameCharacter fish = currentRoom.getActiveFish();
				
				boolean fishSaiu = false;
				// ve se o peixe saiu ou nao
				if ((fish instanceof SmallFish && smallFishExited) || (fish instanceof BigFish && bigFishExited)) {
					fishSaiu = true;
				}
					
				// mexe o peixe se ele nao saiu
				if (!fishSaiu) {
					fish.move(Direction.directionFor(k).asVector());
					if (!fish.canSupport())
						fish.dies();
					processExit(fish);
				}
			}

			// salva quantos tiques já passaram até agora
			int t = ImageGUI.getInstance().getTicks();

			// processa os ticks passados desde o último até ao t
			while (lastTickProcessed < t) {
				processTick();

			}

			// vitoria
			if (smallFishExited && bigFishExited) {
				loadNextLevel();
				// receta p a prox room
				smallFishExited = false;
				bigFishExited = false;
			}
			
			// chama o update da interface gráfica
			ImageGUI.getInstance().update();
		}
	}
	
	private void processExit(GameCharacter fish) {
		// ve se tem uma door embaixo do peixe
		for (GameObject obj : currentRoom.getObjectsAt(fish.getPosition())) {
			
			// esta encima dela
			if (obj instanceof Door) {
				
				// remove o peixe da room e da tela
				currentRoom.removeObject(fish); 
				ImageGUI.getInstance().removeImage(fish); 
					
				if (fish instanceof SmallFish) smallFishExited = true;
				if (fish instanceof BigFish) bigFishExited = true;
					
				// troca para o outro peixe
				currentRoom.nextFish();
				break;
				}
			}
		}

	// incrementa o contador de ticks e aplica a gravidade aos objetos móveis
	private void processTick() {
		lastTickProcessed++;

		// implementa a gravidade nos objectos da Room
		currentRoom.applyGravity();
		
		// verifica se o peixe pequeno pode suportar os objetos móveis acima dele
		if (!SmallFish.getInstance().canSupport())
			SmallFish.getInstance().dies();
		
		// verifica se o peixe grande pode suportar os objetos móveis acima dele
		if (!BigFish.getInstance().canSupport())
			BigFish.getInstance().dies();
	}

	// reinicializa os objectos da interface gráfica
	public void updateGUI() {
		if (currentRoom != null) {
			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(currentRoom.getObjects());
		}
	}

}
