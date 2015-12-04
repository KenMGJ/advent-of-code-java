package net.kenmgj.advent.challenges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day01Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day01Challenge.class);

	@Override
	protected int executeChallenge1(String line) {
		int floorNumber = 0;

		for (char c : line.toCharArray()) {
			logger.debug("Character: " + c);
			if (c == '(') {
				floorNumber++;
			} else {
				if (c == ')') {
					floorNumber--;
				}
			}
			logger.debug("Floor number: " + floorNumber);
		}
		return floorNumber;
	}

	@Override
	protected int executeChallenge2(String line) {
		int floorNumber = 0;
		int i = 0;

		for (char c : line.toCharArray()) {

			logger.debug("Character: " + c);
			if (c == '(') {
				floorNumber++;
			} else {
				if (c == ')') {
					floorNumber--;
				}
			}

			logger.debug("Floor number: " + floorNumber);
			if (floorNumber < 0) {
				break;
			}

			i++;
		}

		return i + 1;
	}
}
