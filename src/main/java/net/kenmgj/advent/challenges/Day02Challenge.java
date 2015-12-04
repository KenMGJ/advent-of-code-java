package net.kenmgj.advent.challenges;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day02Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day02Challenge.class);

	@Override
	protected int executeChallenge1(String line) {
		int[] values = parseLine(line);
		int area1 = values[0] * values[1];
		int area2 = values[1] * values[2];
		int area3 = values[2] * values[0];

		int minArea = NumberUtils.min(area1, area2, area3);
		int total = (2 * area1) + (2 * area2) + (2 * area3) + minArea;

		logger.debug("Areas: " + area1 + ", " + area2 + ", " + area3);
		logger.debug("Minimum area: " + minArea);
		logger.debug("Total: " + total);

		return total;
	}

	@Override
	protected int executeChallenge2(String line) {
		int[] values = parseLine(line);
		Arrays.sort(values);
		logger.debug("Sorted values: " + values[0] + ", " + values[1] + ", " + values[2]);

		int total = (values[0] * values[1] * values[2]) + ((2 * values[0]) + (2 * values[1]));
		logger.debug("Total: " + total);

		return total;
	}

	private int[] parseLine(String line) {
		String[] parsedValues = line.split("x");

		int[] values = new int[3];
		values[0] = Integer.parseInt(parsedValues[0]);
		values[1] = Integer.parseInt(parsedValues[1]);
		values[2] = Integer.parseInt(parsedValues[2]);

		logger.debug("Values: " + values[0] + ", " + values[1] + ", " + values[2]);

		return values;
	}

}
