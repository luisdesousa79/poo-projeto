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
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	private Map<String, Room> rooms;

	private Room currentRoom;

	// esta variável vai indicar o número da room atual, começando na 0
	private int currentRoomNumber = 0;

	// indica o último tick processado
	private int lastTickProcessed = 0;

	// contador do número de movimentos do peixe grande
	private int numberBigFishMoves = 0;

	// contador do número de movimentos do peixe pequeno
	private int numberSmallFishMoves = 0;

	// eu acho que isto deviam ser variáveis da Room e não do GameEngine
	private boolean smallFishExited = false;
	private boolean bigFishExited = false;

	public GameEngine() {
		// mapa que associa cada nome de ficheiro "room?.txt" a um objeto Room
		rooms = new HashMap<String, Room>();
		loadGame();
		// define o nível inicial e atribui a currentRoom o Room lido a partir de
		// "room0.txt"
		currentRoom = rooms.get("room0.txt");
		updateGUI();
		
		//inicializa os peixes na Room
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
		// cria uma lista com todos os ficheiros que estão na pasta "./rooms"
		File[] files = new File("./rooms").listFiles();
		// itera sobre os ficheiros
		// e preenche o dicionário rooms com a associação entre o nome dos ficheiros
		// e o objeto Room lido a partir deles
		for (File f : files) {
			rooms.put(f.getName(), Room.readRoom(f, this));
		}
	}

	// este é o método que trata das teclas e dos ticks
	@Override
	public void update(Observed source) {
		// pergunta à ImageGUI se alguma tecla foi pressionada
		if (ImageGUI.getInstance().wasKeyPressed()) {
			// lê a tecla carregada
			int k = ImageGUI.getInstance().keyPressed();

			// se carregarmos na tecla "r" (código 82), voltamos ao iníco do nível
			if (k == 82) {
				// põe as variáveis que controlam a saída dos peixes a false de novo
				smallFishExited = false;
				bigFishExited = false;
				restartRoom(); // substituir por função de reinício de nível
			}

			// se carregarmos na tecla "espaço" (código 32), muda-se de peixe
			if (k == 32) {
				currentRoom.nextFish();
			}

			// se se carregar numa tecla de direcção, movimenta-se o peixe actual
			if (Direction.isDirection(k)) {
				GameCharacter fish = currentRoom.getActiveFish();

				boolean fishSaiu = false;
				// verifica se o peixe saiu ou não
				if ((fish instanceof SmallFish && smallFishExited) || (fish instanceof BigFish && bigFishExited)) {
					fishSaiu = true;
				}

				// move o peixe se ele nao saiu do nível
				if (!fishSaiu) {
					// guarda a posiçao do peixe que se quer mover
					Point2D oldPos = fish.getPosition();

					// o peixe tenta mover-se
					fish.move(Direction.directionFor(k).asVector());

					// se o peixe se moveu
					if (!fish.getPosition().equals(oldPos)) {

						// se for o peixe pequeno, incrementa o contador de jogadas do pequeno
						if (currentRoom.getActiveFish() instanceof SmallFish) {
							// se o peixe ativo é o pequeno, incrementa o contador do pequeno e atualiza
							// mensagem
							numberSmallFishMoves++;
							updateStatusMessage();
						}

						// se é o grande, incrementa o grande e atualiza mensagem
						if (currentRoom.getActiveFish() instanceof BigFish) {
							numberBigFishMoves++;
							updateStatusMessage();
						}

						// verifica se o peixe pode suportar os objetos da nova posição
						if (!fish.canSupport())
							fish.dies();

						// verifica se está em posição de saída de nível
						processExit(fish);
					}
				}
			}
		}

		// salva quantos tiques já passaram até agora
		int t = ImageGUI.getInstance().getTicks();

		// processa os ticks passados desde o último até ao t
		while (lastTickProcessed < t) {
			processTick();
		}

		
		// verifica se algum dos peixes morreu na sequência do movimento e dos ticks que passaram
		// caso tenha morrido, reinícia o nível
		if (SmallFish.getInstance().isDead() || BigFish.getInstance().isDead()) {
			restartRoom();
			return;
		}

		// verifica se os dois peixes saíram, caso em que passa ao próximo nível
		if (smallFishExited && bigFishExited) {
			loadNextLevel();
			// reset p a prox room
			smallFishExited = false;
			bigFishExited = false;
		}

		// chama o update da interface gráfica
		ImageGUI.getInstance().update();

	}

	// incrementa o contador de ticks e aplica a gravidade aos objetos móveis
	private void processTick() {

		lastTickProcessed++;

		// põe os objetos da room a movimentarem-se
		currentRoom.updateMovement();

		// verifica se o peixe pequeno pode suportar os objetos móveis acima dele
		if (!SmallFish.getInstance().canSupport())
			SmallFish.getInstance().dies();

		// verifica se o peixe grande pode suportar os objetos móveis acima dele
		if (!BigFish.getInstance().canSupport())
			BigFish.getInstance().dies();
	}

	// atualiza os objetos da interface gráfica
	public void updateGUI() {
		if (currentRoom != null) {
			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(currentRoom.getObjects());
		}
	}

	// procedimento para atualizar a mensagem do topo da janela
	private void updateStatusMessage() {
		String message = "Room: " + currentRoomNumber + " | Small: " + numberSmallFishMoves + " | Big: "
				+ numberBigFishMoves;
		ImageGUI.getInstance().setStatusMessage(message);
	}

	// carrega o nível seguinte
	private void loadNextLevel() {

		// define a proxima room
		int numRoomSeguinte = currentRoomNumber + 1;
		String roomSeguinte = "room" + numRoomSeguinte + ".txt";

		// se houver uma próxima room
		if (rooms.containsKey(roomSeguinte)) {

			// atualiza a room
			currentRoom = rooms.get(roomSeguinte);

			// inicializa os peixes na nova Room
			SmallFish.getInstance().setRoom(currentRoom);
			BigFish.getInstance().setRoom(currentRoom);

			// redireciona os peixes para o posicão inicial daquela room
			SmallFish.getInstance().setPosition(currentRoom.getSmallFishStartingPosition());
			BigFish.getInstance().setPosition(currentRoom.getBigFishStartingPosition());

			// volta a adicionar os peixes à room que está a ser carregada
			currentRoom.addObject(SmallFish.getInstance());
			currentRoom.addObject(BigFish.getInstance());

			updateGUI();

			currentRoomNumber++;

			updateStatusMessage();

		} else {
			// se n tiver mais rooms, venceu tudo
			// soma os tiks dos dois peixes
            int finalMoves = numberBigFishMoves + numberSmallFishMoves; 
            
            // pede o nome ao jogador da run
            String name = javax.swing.JOptionPane.showInputDialog(null, 
                "Parabéns! Venceste com " + finalMoves + " moves!\nIntroduz o teu nome:");
            
            // se ele escreveu um nome guarda o score na lista
            if (name != null && !name.trim().isEmpty()) {
                ScoreManager sm = new ScoreManager();
                sm.addScore(name, finalMoves);
                
                // mostra a lista das Highscores
                javax.swing.JOptionPane.showMessageDialog(null, sm.getHighScoresText());
            }
            
            // fecha o jogo
             System.exit(0);
		}

	}

	// verifica se o peixe está na posição de Exit
	private void processExit(GameCharacter fish) {
		// verifica se uma Door está na posição de peixe
		for (GameObject obj : currentRoom.getObjectsAt(fish.getPosition())) {

			// está na posição de Door
			if (obj instanceof Door) {

				// remove o peixe da room e da tela
				currentRoom.removeObject(fish);
				ImageGUI.getInstance().removeImage(fish);

				if (fish instanceof SmallFish)
					smallFishExited = true;
				if (fish instanceof BigFish)
					bigFishExited = true;

				// troca para o outro peixe
				currentRoom.nextFish();
				break;
			}
		}
	}

	// vai reiniciar o nível
	public void restartRoom() {
		
		String fileName = "room" + currentRoomNumber + ".txt";
		
		// guarda o ficheiro de onde vai recarregar a room
		File f = new File("./rooms/" + fileName);

		// volta a carregar a Room no seu estado inicial no dicionário
		rooms.put(f.getName(), Room.readRoom(f, this));
		
		// guarda a room atual na variável currentRoom
		currentRoom = rooms.get(fileName);
		
		SmallFish.getInstance().setRoom(currentRoom);
	    BigFish.getInstance().setRoom(currentRoom);

		// volta a adicionar os peixes à room atual
		currentRoom.addObject(SmallFish.getInstance());
		currentRoom.addObject(BigFish.getInstance());

		// redireciona os peixes para o posicão inicial da room atual
		SmallFish.getInstance().setPosition(currentRoom.getSmallFishStartingPosition());
		BigFish.getInstance().setPosition(currentRoom.getBigFishStartingPosition());
		
		// reiniciar flags de saída
	    smallFishExited = false;
	    bigFishExited = false;
		
	 // reiniciar estado isDead como falso
	    SmallFish.getInstance().setAlive();
	    BigFish.getInstance().setAlive();

		// atualiza interface gráfico
		updateGUI();

		updateStatusMessage();
	}
	
	

}
