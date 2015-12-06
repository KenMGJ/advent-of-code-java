package net.kenmgj.advent.challenges;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day06Challenge extends AbstractChallenge {

	private static final int GRID_ROWS = 1000;
	private static final int GRID_COLUMNS = 1000;

	private static final String REGEX = "(.*) (\\d{1,3}),(\\d{1,3}) through (\\d{1,3}),(\\d{1,3})";

	private int[][] lightGrid;

	private Logger logger = LoggerFactory.getLogger(Day06Challenge.class);

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Lines in file: " + input.size());

		lightGrid = new int[GRID_ROWS][GRID_COLUMNS];
		for (int i = 0; i < GRID_ROWS; i++) {
			for (int j = 0; j < GRID_COLUMNS; j++) {
				lightGrid[i][j] = 0;
			}
		}

		for (String line : input) {
			logger.debug("Challenge Number: " + challengeNumber);
			logger.debug("Line: " + line);

			processLine(line, challengeNumber);
		}

		// Calculate results
		int total = 0;
		for (int i = 0; i < GRID_ROWS; i++) {
			for (int j = 0; j < GRID_COLUMNS; j++) {
				total += lightGrid[i][j];
			}
		}
		return total;
	}

	private void processLine(String line, int challengeNumber) {
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String command = matcher.group(1);
			String startRow = matcher.group(2);
			String startColumn = matcher.group(3);
			String endRow = matcher.group(4);
			String endColumn = matcher.group(5);

			logger.debug("Command: " + command + " (" + startRow + "," + startColumn
					+ ") -> (" + endRow + "," + endColumn + ")");

			int startX = Integer.parseInt(startRow);
			int startY = Integer.parseInt(startColumn);
			int endX = Integer.parseInt(endRow);
			int endY = Integer.parseInt(endColumn);

			for (int i = startX; i <= endX; i++) {
				for (int j = startY; j <= endY; j++) {
					if ("turn on".equals(command)) {
						if (challengeNumber == 1) {
							lightGrid[i][j] = 1;
						} else {
							lightGrid[i][j] += 1;
						}
					} else {
						if ("turn off".equals(command)) {
							if (challengeNumber == 1) {
								lightGrid[i][j] = 0;
							} else {
								if (lightGrid[i][j] != 0) {
									lightGrid[i][j] -= 1;
								}
							}
						} else {
							if ("toggle".equals(command)) {
								if (challengeNumber == 1) {
									if (lightGrid[i][j] == 0) {
										lightGrid[i][j] = 1;
									} else {
										lightGrid[i][j] = 0;
									}
								} else {
									lightGrid[i][j] += 2;
								}
							} else {
								logger.warn("Unknown command: " + command);
							}
						}
					}
				}
			}
		}
 	}
}
