package com.solvd.it.company;

import com.solvd.it.app.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class InputReader {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(InputReader.class);
    public void processFile() {
        String[] targetWords = {"architecture", "hexagonal", "java"};
        String[] words = {"empty"};

        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : targetWords) {
            wordCounts.put(word.toLowerCase(), 0);
        }

        try {
            File file = new File("src/main/resources/input.txt");
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

            for (String line : lines) {
                words = line.toLowerCase().split("\\W+");
                for (String word : words) {
                    if (wordCounts.containsKey(word)) {
                        wordCounts.put(word, wordCounts.get(word) + 1);
                    }
                }
            }

            wordCounts.forEach((word, count) ->
                    LOGGER.info("Word " + word + " appears " + count + " times"));

            File outFile = new File("src/main/resources/output.txt");
            FileUtils.writeStringToFile(outFile, wordCounts.toString(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            LOGGER.error("Error processing file", e);
        }
    }

}