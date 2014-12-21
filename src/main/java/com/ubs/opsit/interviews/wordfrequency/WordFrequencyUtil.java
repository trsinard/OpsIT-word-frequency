package com.ubs.opsit.interviews.wordfrequency;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Timothy Sinard on 12/20/2014.
 */
public class WordFrequencyUtil implements WordFrequency {
    private Map<String, Integer> mappedCount;
    public WordFrequencyUtil() {
        this.mappedCount = new HashMap<String, Integer>();
    }

    @Override
    public synchronized Map<String, Integer> countOccurrencesOfWordsWithin(
            String stringToEvaluate) throws IllegalArgumentException {

        if (stringToEvaluate == null) {
            throw new IllegalArgumentException("Cannot process a null string.");
        }
        if (stringToEvaluate.trim().equals("")) {
            throw new IllegalArgumentException(
                    "Cannot process an empty string.");
        }

        String[] wordsInString = stringToEvaluate.split("\\s+");
        for (String word : wordsInString) {
            Integer occurance = this.mappedCount.get(word);
            occurance = occurance == null ? 1 : ++occurance;
            this.mappedCount.put(word, occurance);
        }

        return this.mappedCount;
    }

}
