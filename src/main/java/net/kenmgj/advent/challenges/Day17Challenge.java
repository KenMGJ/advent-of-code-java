package net.kenmgj.advent.challenges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.Combinations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day17Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day17Challenge.class);
	private static final int CAPACITY = 150;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {

		int[] containers = new int[input.size()];
		int l = 0;
		for (String s : input) {
			containers[l] = Integer.parseInt(s);
			l++;
		}

		int fit = 0;
		Map<Integer, Integer> minFit = new HashMap<Integer, Integer>();

		for (int i = 1; i <= input.size(); i++) {
			Combinations combinations = new Combinations(input.size(), i);
			for (int[] j : combinations) {
				int sum = 0;
				for (int k : j) {
					sum += containers[k];
				}
				if (sum == CAPACITY) {
					fit++;
					if (!minFit.containsKey(j.length)) {
						minFit.put(j.length, 1);
					} else {
						minFit.put(j.length, minFit.get(j.length) + 1);
					}
				}
			}
		}

		if (challengeNumber == 1) {
			return fit;
		} else {
			int[] minFitCombinations = new int[minFit.keySet().size()];
			int i = 0;
			for (int m : minFit.keySet()) {
				minFitCombinations[i] = m;
				i++;
				logger.debug("Containers: " + m);
			}
			int minCombinations = NumberUtils.min(minFitCombinations);
			return minFit.get(minCombinations);
		}
	}

}
