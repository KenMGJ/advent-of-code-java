package net.kenmgj.advent.challenges;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public abstract class AbstractChallenge {

	public Pair<Integer, Integer> executeChallenge(List<String> input) {
		Pair<Integer, Integer> results = Pair.of(executeChallenge(input, 1), executeChallenge(input, 2));
		return results;
	}

	protected int executeChallenge(List<String> input, int challengeNumber) {
		int result = 0;

		for (String line : input) {
			if (challengeNumber == 1) {
				result += executeChallenge1(line);
			} else {
				result += executeChallenge2(line);
			}
		}

		return result;
	}

	protected int executeChallenge1(String line) {
		return 0;
	}

	protected int executeChallenge2(String line) {
		return 0;
	}

}
