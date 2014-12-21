package com.ubs.opsit.interviews.wordfrequency;

import java.util.Map;
/**
 * @author Timothy Sinard
 * @since 20-Dec-2014
 */

public class WordFrequencyThreadTestCaller implements Runnable {

	private String parameter;
	private WordFrequencyUtil wfUtil;
	private Map<String, Integer> results;

	public WordFrequencyThreadTestCaller(WordFrequencyUtil wfUtil,
			String parameter) {
		this.wfUtil = wfUtil;
		this.parameter = parameter;
		this.results = null;
	}
	@Override
	public void run() {
		this.results = this.wfUtil
				.countOccurrencesOfWordsWithin(this.parameter);
	}
	public Map<String, Integer> getResults() {
		return this.results;
	}
}
