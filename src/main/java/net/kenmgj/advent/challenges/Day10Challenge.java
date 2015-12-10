package net.kenmgj.advent.challenges;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day10Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day10Challenge.class);

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		int iterations = (challengeNumber == 1) ? 40 : 50;

		String inputOutput = "";
		int outputLength = 0;

		for (String s : input) {
			inputOutput = s;

			for (int i = 0; i < iterations; i++) {
				inputOutput = lookAndSay(inputOutput);
			}

			logger.debug("Output: " + inputOutput + " Length: " + inputOutput.length());
			outputLength += inputOutput.length();
		}

		return outputLength;
	}

	private String lookAndSay(String input) {
		logger.debug("Input: " + input);
		StringBuilder output = new StringBuilder();

		char previousChar = 'Z';
		int previousCount = 0;

		for (char c : input.toCharArray()) {
			if (c != previousChar) {
				logger.debug("Previous: " + previousChar + " Count: " + previousCount);
				if (previousCount > 0) {
					output.append(previousCount);
					output.append(previousChar);
				}
				previousChar = c;
				previousCount = 1;
			} else {
				previousCount++;
			}
		}
		logger.debug("Previous: " + previousChar + " Count: " + previousCount);
		output.append(previousCount);
		output.append(previousChar);

		logger.debug("Output: " + output);
		return output.toString();
	}
}
