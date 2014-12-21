package com.ubs.opsit.interviews.wordfrequency;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Timothy Sinard
 * @since 20-Dec-2014
 */

public class WordFrequencyTest {

    @Test(expected = java.lang.AssertionError.class)
    public void testExample() {
        Assert.fail("Not yet implemented.");
    }

    @Test
    public void mapNotNull() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        Map<String, Integer> result = wfUtil
                .countOccurrencesOfWordsWithin("Test");
        Assert.assertNotNull(result);

    }

    @Test
    public void processValidStringAsOne() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        Map<String, Integer> result = wfUtil
                .countOccurrencesOfWordsWithin("Test");
        Assert.assertNotEquals(0, result.size());
    }

    // Maps natively do not allow duplicate key entries.
    @Test
    public void processValidStringByWords() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        Map<String, Integer> result = wfUtil
                .countOccurrencesOfWordsWithin("the man in the moon");
        Assert.assertEquals(4, result.size());
    }

    @Test
    public void processValidStringByWordsWithCount() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        Map<String, Integer> result = wfUtil
                .countOccurrencesOfWordsWithin("the man in the moon");
        Assert.assertEquals(2, result.get("the").intValue());
        Assert.assertEquals(1, result.get("man").intValue());
        Assert.assertEquals(1, result.get("in").intValue());
        Assert.assertEquals(1, result.get("moon").intValue());
    }

    @Test
    public void processValidStringByWordsWithCountSubsequently() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        Map<String, Integer> result = wfUtil
                .countOccurrencesOfWordsWithin("the man in the moon");
        Assert.assertEquals(2, result.get("the").intValue());
        Assert.assertEquals(1, result.get("man").intValue());
        Assert.assertEquals(1, result.get("in").intValue());
        Assert.assertEquals(1, result.get("moon").intValue());
        result = wfUtil.countOccurrencesOfWordsWithin("the man on the moon");
        Assert.assertEquals(4, result.get("the").intValue());
        Assert.assertEquals(2, result.get("man").intValue());
        Assert.assertEquals(1, result.get("in").intValue());
        Assert.assertEquals(1, result.get("on").intValue());
        Assert.assertEquals(2, result.get("moon").intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void processEmptyString() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        wfUtil.countOccurrencesOfWordsWithin("          ");
        Assert.fail("An empty string should not be processed.");

    }
    @Test(expected = IllegalArgumentException.class)
    public void processNullString() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        try {
            wfUtil.countOccurrencesOfWordsWithin(null);
            Assert.fail("A null string was handled improperly.");
        } catch (NullPointerException e) {
            Assert.fail("A null string should not be processed.");
        }

    }
    @Test
    public void runAsNewThread() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();
        WordFrequencyThreadTestCaller wfttestCaller1 = new WordFrequencyThreadTestCaller(wfUtil, "the man in the moon");
        Thread t1 = new Thread(wfttestCaller1, "thread1");
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        Map<String, Integer> result = wfttestCaller1.getResults();
        Assert.assertEquals(2, result.get("the").intValue());
        Assert.assertEquals(1, result.get("man").intValue());
        Assert.assertEquals(1, result.get("in").intValue());
        Assert.assertEquals(1, result.get("moon").intValue());
    }

    @Test
    public void runAsMultipleThreads() {
        WordFrequencyUtil wfUtil = new WordFrequencyUtil();

        //By giving each thread a lot to do, it gives them time to unsynchronize
        //This will fail if method is not thread safe
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append(" the man in the moon ");
        }
        WordFrequencyThreadTestCaller wfttestCaller1 = new WordFrequencyThreadTestCaller(wfUtil, sb.toString());
        WordFrequencyThreadTestCaller wfttestCaller2 = new WordFrequencyThreadTestCaller(wfUtil, sb.toString());
        Thread t1 = new Thread(wfttestCaller1, "thread1");
        Thread t2 = new Thread(wfttestCaller2, "thread2");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        Map<String, Integer> result = wfttestCaller2.getResults();
        Assert.assertEquals(200, result.get("the").intValue());
        Assert.assertEquals(100, result.get("man").intValue());
        Assert.assertEquals(100, result.get("in").intValue());
        Assert.assertEquals(100, result.get("moon").intValue());
    }

}
