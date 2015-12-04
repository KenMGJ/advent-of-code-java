package net.kenmgj.advent.challenges;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(AbstractChallenge.class);

	public Pair<Integer, Integer> executeChallenge(List<String> input) {
		Pair<Integer, Integer> results = Pair.of(executeChallenge(input, 1), executeChallenge(input, 2));
		return results;
	}

	protected int executeChallenge(List<String> input, int challengeNumber) {
		int result = 0;
		logger.debug("Lines in file: " + input.size());

		for (String line : input) {
			logger.debug("Challenge Number: " + challengeNumber);
			logger.debug("Line: " + line);

			if (challengeNumber == 1) {
				result += executeChallenge1(line);
			} else {
				result += executeChallenge2(line);
			}
		}

		logger.debug("Result: " + result);
		return result;
	}

	protected int executeChallenge1(String line) {
		logger.warn("executeChallenge1 not implemented");
		return 0;
	}

	protected int executeChallenge2(String line) {
		logger.warn("executeChallenge2 not implemented");
		return 0;
	}

}
