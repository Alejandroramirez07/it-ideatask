package com.solvd.it.company;

import com.solvd.it.app.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReader {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(InputReader.class);
    public void processFile() {
        String[] targetWords = {"architecture", "hexagonal", "java"};

        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : targetWords) {
            wordCounts.put(word.toLowerCase(), 0);
        }

        try (InputStream inputStream = getClass().getResourceAsStream("/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+");
                for (String word : words) {
                    if (wordCounts.containsKey(word)) {
                        wordCounts.put(word, wordCounts.get(word) + 1);
                    }
                }
            }

            wordCounts.forEach((word, count) ->
                    LOGGER.info("Word '{}' appears {} times", word, count));

        } catch (Exception e) {
            LOGGER.error("Error reading input.txt", e);
        }
    }
}