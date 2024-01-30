/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xrea.jeisi.englishwords;

import com.xrea.jeisi.englishwords.residentwork.Score;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.xrea.jeisi.englishwords.residentwork.ConfigInfo;
import com.xrea.jeisi.englishwords.residentwork.Words;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author jeisi
 */
public class TestScore {

    private HashMap<String, Score> scores = new HashMap<>();

    public void addWords(Words words) {
        for(Word word : words.getList()) {
            if(!scores.containsKey(word.getEnglish())) {
                scores.put(word.getEnglish(), new Score(word.getEnglish()));
            }
        }
    }
    
    public boolean isEmpty() {
        return scores.isEmpty();
    }
    
//    public Stream<Map.Entry<String, Score>> stream() {
//        return scores.entrySet().stream();
//    }
    
    public void correct(String english) {
        Score score = searchScore(english);
        score.incCorrect();
    }

    public void wrong(String english) {
        Score score = searchScore(english);
        score.incWrong();
    }
    
    public List<Score> extract(Predicate<Score> p) {
        List<Score> ret = new ArrayList<>();
        for(Score score : scores.values()) {
            if(p.test(score)) {
                ret.add(score);
            }
        }
        return ret;
    }

    private Score searchScore(String english) {
        Score score = scores.get(english);
        if (score == null) {
            score = new Score(english);
            scores.put(english, score);
        }
        return score;
    }

    public void load(String identifier) {
        Path inputPath = ConfigInfo.getPath("testscore_" + identifier + ".csv");
        if(!Files.exists(inputPath)) {
            return;
        }
        
        try (CSVReader reader = new CSVReader(Files.newBufferedReader(inputPath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Score score = new Score(line);
                scores.put(score.getEnglish(), score);
            }
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void save(String identifier) {
        List<String[]> lines = new ArrayList<>();
        for (var score : scores.values()) {
            lines.add(score.toCsv());
        }

        Path outputPath = ConfigInfo.getPath("testscore_" + identifier + ".csv");
        Path parentDirectory = outputPath.getParent();
        if (!Files.exists(parentDirectory)) {
            try {
                Files.createDirectories(parentDirectory);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException();
            }
        }

        try (CSVWriter csvWriter = new CSVWriter(Files.newBufferedWriter(outputPath))) {
            csvWriter.writeAll(lines);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void print() {
        scores.entrySet().stream().forEach(s -> System.out.println(s.getValue().toString()));
    }
}
