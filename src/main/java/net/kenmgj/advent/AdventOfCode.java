package net.kenmgj.advent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.kenmgj.advent.challenges.AbstractChallenge;

public class AdventOfCode {

	private static final Logger logger = LoggerFactory.getLogger(AdventOfCode.class);

	private static final String CLASS_NAME_PLACEHOLDER = "net.kenmgj.advent.challenges.DayXXChallenge";

	public static void main(String[] args) throws Exception {
		logger.debug("Args: " + StringUtils.join(args, ", "));

		int challengeNumber = Integer.parseInt(args[0]);
		logger.debug("Challenge Number: " + challengeNumber);

		if (challengeNumber > 0 && challengeNumber <= 25) {
			List<String> fileContents = new ArrayList<String>();
			fileContents = FileUtils.readLines(new File(args[1]));

			String challengeNum = "" + challengeNumber;
			if (challengeNumber < 10) {
				challengeNum = "0" + challengeNum;
			}
			logger.debug("Challenge Number String: " + challengeNum);

			String className = CLASS_NAME_PLACEHOLDER.replace("XX", challengeNum);
			logger.debug(className);

			Object challenge = Class.forName(className).getConstructor().newInstance();
			if (challenge instanceof AbstractChallenge) {
				Pair<Integer, Integer> results = ((AbstractChallenge)challenge).executeChallenge(fileContents);
				logger.info("Result Challenge 1: " + results.getLeft());
				logger.info("Result Challenge 2: " + results.getRight());
			}
		}
	}
}
