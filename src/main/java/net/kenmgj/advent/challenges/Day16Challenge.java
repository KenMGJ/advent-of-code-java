package net.kenmgj.advent.challenges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day16Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day16Challenge.class);

	private Map<String, Integer> mfcsamResults;
	private Map<Integer, Map<String, Integer>> auntSues;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Challenge number: " + challengeNumber);

		mfcsamResults = new HashMap<String, Integer>();
		mfcsamResults.put("children", 3);
		mfcsamResults.put("cats", 7);
		mfcsamResults.put("samoyeds", 2);
		mfcsamResults.put("pomeranians", 3);
		mfcsamResults.put("akitas", 0);
		mfcsamResults.put("vizslas", 0);
		mfcsamResults.put("goldfish", 5);
		mfcsamResults.put("trees", 3);
		mfcsamResults.put("cars", 2);
		mfcsamResults.put("perfumes", 1);

		auntSues = new HashMap<Integer, Map<String, Integer>>();
		for (String line : input) {
			addAuntToList(line);
		}

		for (String s : mfcsamResults.keySet()) {
			auntSues = filterAuntSues(s, challengeNumber);
		}

		int auntSue = 0;

		if (auntSues.size() == 1) {
			for (Entry<Integer, Map<String, Integer>> entry : auntSues.entrySet()) {
				auntSue = entry.getKey();
			}
		}

		return auntSue;
	}

	private void addAuntToList(String line) {
		Pattern pattern = Pattern.compile("^Sue (\\d+): (.+): (\\d+), (.+): (\\d+), (.+): (\\d+)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			int number = Integer.parseInt(matcher.group(1));
			String compound1 = matcher.group(2);
			int compound1Amt = Integer.parseInt(matcher.group(3));
			String compound2 = matcher.group(4);
			int compound2Amt = Integer.parseInt(matcher.group(5));
			String compound3 = matcher.group(6);
			int compound3Amt = Integer.parseInt(matcher.group(7));

			logger.debug(number + " " + compound1 + ":" + compound1Amt + " " +
					compound2 + ":" + compound2Amt + " " + compound3 + ":" + compound3Amt);

			Map<String, Integer> auntSueProp = new HashMap<String, Integer>();
			auntSueProp.put(compound1, compound1Amt);
			auntSueProp.put(compound2, compound2Amt);
			auntSueProp.put(compound3, compound3Amt);
			auntSues.put(number, auntSueProp);
		}
	}

	private Map<Integer, Map<String, Integer>> filterAuntSues(String compound, int challengeNumber) {
		Map<Integer, Map<String, Integer>> filteredMap = new HashMap<Integer, Map<String, Integer>>();

		for (Entry<Integer, Map<String, Integer>> s : auntSues.entrySet()) {
			if (!s.getValue().containsKey(compound)) {
				filteredMap.put(s.getKey(), s.getValue());
			} else {
				if (challengeNumber == 1) {
					if (s.getValue().get(compound) == mfcsamResults.get(compound)) {
						filteredMap.put(s.getKey(), s.getValue());
					}
				} else {
					if (compound == "cats" && s.getValue().get(compound) > mfcsamResults.get(compound)) {
						filteredMap.put(s.getKey(), s.getValue());
					} else if (compound == "trees" && s.getValue().get(compound) > mfcsamResults.get(compound)) {
						filteredMap.put(s.getKey(), s.getValue());
					} else if (compound == "goldfish" && s.getValue().get(compound) < mfcsamResults.get(compound)) {
						filteredMap.put(s.getKey(), s.getValue());
					} else if (compound == "pomeranians" && s.getValue().get(compound) < mfcsamResults.get(compound)) {
						filteredMap.put(s.getKey(), s.getValue());
					} else if (compound != "cats" && compound != "trees" && compound != "goldfish" && compound != "pomeranians" &&
							s.getValue().get(compound) == mfcsamResults.get(compound)) {
							filteredMap.put(s.getKey(), s.getValue());
					}
				}
			}
		}

		return filteredMap;
	}
}
