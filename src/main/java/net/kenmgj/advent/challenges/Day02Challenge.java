package net.kenmgj.advent.challenges;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;

public class Day02Challenge extends AbstractChallenge {

	@Override
	protected int executeChallenge1(String line) {
		int[] values = parseLine(line);
		int area1 = values[0] * values[1];
		int area2 = values[1] * values[2];
		int area3 = values[2] * values[0];

		int minArea = NumberUtils.min(area1, area2, area3);
		int total = (2 * area1) + (2 * area2) + (2 * area3) + minArea;

		return total;
	}

	@Override
	protected int executeChallenge2(String line) {
		int[] values = parseLine(line);
		Arrays.sort(values);
		int total = (values[0] * values[1] * values[2]) + ((2 * values[0]) + (2 * values[1]));
		return total;
	}

	private int[] parseLine(String line) {
		String[] parsedValues = line.split("x");

		int[] values = new int[3];
		values[0] = Integer.parseInt(parsedValues[0]);
		values[1] = Integer.parseInt(parsedValues[1]);
		values[2] = Integer.parseInt(parsedValues[2]);

		return values;
	}

}
