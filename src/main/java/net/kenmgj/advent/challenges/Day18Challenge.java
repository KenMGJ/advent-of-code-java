package net.kenmgj.advent.challenges;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day18Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day18Challenge.class);

	private static int ROWS = 100;
	private static int COLS = 100;
	private static int ITERATIONS = 100;

	private boolean[][] lightsOn;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Challenge number: " + challengeNumber);
		lightsOn = new boolean[ROWS][COLS];

		// Load light information
		int i = 0;
		for (String line : input) {
			char[] lights = line.toCharArray();
			for (int j = 0; j < lights.length; j++) {
				if (lights[j] == '#') {
					lightsOn[i][j] = true;
				} else {
					lightsOn[i][j] = false;
				}
			}
			i++;
		}

		logGrid(lightsOn);
		for (int m = 0; m < ITERATIONS; m++) {
			lightsOn = getNextGeneration(lightsOn, challengeNumber);

			logGrid(lightsOn);
		}

		// Calculate total lights on
		int numberOfLightsOn = 0;
		for (int k = 0; k < ROWS; k++) {
			for (int l = 0; l < COLS; l++) {
				if (lightsOn[k][l]) {
					numberOfLightsOn++;
				}
			}
		}
		return numberOfLightsOn;
	}

	private void logGrid(boolean[][] lightsOn) {
		for (int i = 0; i < lightsOn.length; i++) {
			for (int j = 0; j < lightsOn[i].length; j++) {
				logger.debug("[" + i + "," + j + "] " + lightsOn[i][j]);
			}
		}
	}

	private boolean[][] getNextGeneration(boolean[][] lightsOn, int challengeNumber) {
		boolean[][] newLightsOn = new boolean[ROWS][COLS];

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				int numberOn = 0;
				if (i > 0) {
					if (j > 0) {
						if (lightsOn[i-1][j-1]) {
							numberOn++;
						}
					}
					if (lightsOn[i-1][j]) {
						numberOn++;
					}
					if (j < COLS-1) {
						if (lightsOn[i-1][j+1]) {
							numberOn++;
						}
					}
				}
				if (j > 0) {
					if (lightsOn[i][j-1]) {
						numberOn++;
					}
				}
				if (j < COLS-1) {
					if (lightsOn[i][j+1]) {
						numberOn++;
					}
				}
				if (i < ROWS-1) {
					if (j > 0) {
						if (lightsOn[i+1][j-1]) {
							numberOn++;
						}
					}
					if (lightsOn[i+1][j]) {
						numberOn++;
					}
					if (j < COLS-1) {
						if (lightsOn[i+1][j+1]) {
							numberOn++;
						}
					}
				}

				if (challengeNumber == 2 && (i == 0 || i == ROWS-1) &&
						(j == 0 || j == (COLS-1))) {
					newLightsOn[i][j] = true;
				} else {
					if (lightsOn[i][j]) {
						if (numberOn == 2 || numberOn == 3) {
							newLightsOn[i][j] = true;
						} else {
							newLightsOn[i][j] = false;
						}
					} else {
						if (numberOn == 3) {
							newLightsOn[i][j] = true;
						} else {
							newLightsOn[i][j] = false;
						}
					}
				}
			}
		}

		return newLightsOn;
	}
}
