package net.kenmgj.advent.challenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day05Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day05Challenge.class);

	@Override
	protected int executeChallenge1(String line) {

		if (!lineMatchesPattern(line, "[aeiou].*[aeiou].*[aeiou]")) {
			logger.debug("Does not contain at least three vowels.");
			return 0;
		}

		if (!lineMatchesPattern(line, "(.)\\1")) {
			logger.debug("Does not contain a letter tha occurs at least twice in a row.");
			return 0;
		}

		if (lineMatchesPattern(line, "(ab|cd|pq|xy)")) {
			logger.debug("Contains on of the strings ab, cd, pq, or xy.");
			return 0;
		}

		return 1;
	}

	@Override
	protected int executeChallenge2(String line) {
		if (!lineMatchesPattern(line, "(..).*\\1")) {
			logger.debug("Does not contain a pair of two letter that appear twice without overlapping.");
			return 0;
		}

		if (!lineMatchesPattern(line, "(.).\\1")) {
			logger.debug("Does not contains at least one letter that repeats with exactly one letter between them.");
			return 0;
		}

		return 1;
	}

	private boolean lineMatchesPattern(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}
}
