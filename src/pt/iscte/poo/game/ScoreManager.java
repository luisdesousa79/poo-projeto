package pt.iscte.poo.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private static final String FILE_NAME = "highscores.txt";
    private List<Score> scores;

    public ScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    // carrega os scores do ficheiro se ele existir
    private void loadScores() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Score s = Score.fromFileFormat(line);
                if (s != null) scores.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // salva a lista no ficheiro
    private void saveScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Score s : scores) {
                bw.write(s.toFileFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adiciona score, ordena e nao conta o resto (pega so o top 10)
    public void addScore(String name, int moves) {
        scores.add(new Score(name, moves));
        
        Collections.sort(scores); // ordena para os menores moves primeiro
        
        // deixa so os 10 melhores
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
        
        saveScores();
    }
    
    // gera o texto bonito para mostrar na tela
    public String getHighScoresText() {
        StringBuilder sb = new StringBuilder();
        sb.append(" TOP 10 MELHORES RUNS \n");
        sb.append("----------------------------\n");
        int rank = 1;
        for (Score s : scores) {
            sb.append(rank).append(". ").append(s.toString()).append("\n");
            rank++;
        }
        return sb.toString();
    }
}