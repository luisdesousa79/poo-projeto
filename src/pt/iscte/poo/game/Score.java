package pt.iscte.poo.game;

public class Score implements Comparable<Score> {
    
    private String playerName;
    private int moves;

    public Score(String playerName, int moves) {
        this.playerName = playerName;
        this.moves = moves;
    }

    public int getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return String.format("%-15s | %d moves", playerName, moves);
    }
    
    // formato para guardar no ficheiro: Nome;moves
    public String toFileFormat() {
        return playerName + ";" + moves;
    }
    
    // converte uma linha do ficheiro de volta para objeto score
    public static Score fromFileFormat(String line) {
        String[] parts = line.split(";");
        if (parts.length >= 2) {
            try {
                return new Score(parts[0], Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Score other) {
        // ordem crescente, quem tiver menos moves fica em primeiro
        return Integer.compare(this.moves, other.moves);
    }
}