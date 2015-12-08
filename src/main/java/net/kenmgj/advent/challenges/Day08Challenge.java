package net.kenmgj.advent.challenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day08Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day08Challenge.class);

	@Override
	protected int executeChallenge1(String line) {

		String unescaped = line;
		logger.debug(line);

		Pattern pattern = Pattern.compile("^\"(.*)\"$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			unescaped = matcher.group(1);
		}
		logger.debug(unescaped);

		unescaped = unescaped.replaceAll("\\\\\\\\", "S");
		logger.debug(unescaped);

		unescaped = unescaped.replaceAll("\\\\\\\"", "U");
		logger.debug(unescaped);

		unescaped = unescaped.replaceAll("\\\\x..", "D");
		logger.debug(unescaped);

		logger.debug("Size of line: " + line.length() + " Size of unescaped line: " + unescaped.length());
		return line.length() - unescaped.length();
	}

	@Override
	protected int executeChallenge2(String line) {

		String escaped;

		escaped = line.replace("\\", "\\\\");
		logger.debug(escaped);

		escaped = escaped.replace("\"", "\\\"");
		logger.debug(escaped);

		escaped = "\"" + escaped + "\"";
		logger.debug(escaped);

		logger.debug("Size of escaped: " + escaped.length() + " Size of line: " + line.length());
		return escaped.length() - line.length();
	}

}
