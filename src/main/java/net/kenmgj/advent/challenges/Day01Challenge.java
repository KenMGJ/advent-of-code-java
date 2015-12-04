package net.kenmgj.advent.challenges;

public class Day01Challenge extends AbstractChallenge {

	@Override
	protected int executeChallenge1(String line) {
		int floor_number = 0;

		for (char c : line.toCharArray()) {
			if (c == '(') {
				floor_number++;
			} else {
				if (c == ')') {
					floor_number--;
				}
			}
		}
		return floor_number;
	}

	@Override
	protected int executeChallenge2(String line) {
		int floor_number = 0;
		int i = 0;

		for (char c : line.toCharArray()) {
			if (c == '(') {
				floor_number++;
			} else {
				if (c == ')') {
					floor_number--;
				}
			}

			if (floor_number < 0) {
				break;
			}

			i++;
		}

		return i + 1;
	}
}
