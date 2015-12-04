package net.kenmgj.advent.challenges;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Day03Challenge extends AbstractChallenge {

	@Override
	protected int executeChallenge1(String line) {

		Map<Point, Integer> houses = new HashMap<Point, Integer>();
		Point currentPos = new Point(0,0);

		houses.put(currentPos, 1);

		for (char c : line.toCharArray()) {
			currentPos = nextHouse(currentPos, c);
			incrementHouseVisits(houses, currentPos);
		}

		return houses.keySet().size();
	}

	@Override
	protected int executeChallenge2(String line) {

		Map<Point, Integer> houses = new HashMap<Point, Integer>();

		Point currentSantaPos = new Point(0,0);
		houses.put(currentSantaPos, 1);

		Point currentRoboPos = new Point(0,0);
		houses.put(currentRoboPos, 2);

		for (int i = 0; i < line.length(); i += 2) {
			currentSantaPos = nextHouse(currentSantaPos, line.charAt(i));
			incrementHouseVisits(houses, currentSantaPos);
			currentRoboPos = nextHouse(currentRoboPos, line.charAt(i+1));
			incrementHouseVisits(houses, currentRoboPos);
		}

		return houses.keySet().size();
	}

	private Point nextHouse(Point pos, char c) {
		int x = pos.x;
		int y = pos.y;

		switch (c) {
		case '^':
			y++;
			break;
		case '>':
			x++;
			break;
		case 'v':
			y--;
			break;
		case '<':
			x--;
			break;
		default:
			break;
		}

		return new Point(x, y);
	}

	private void incrementHouseVisits(Map<Point, Integer> houses, Point pos) {
		if (houses.containsKey(pos)) {
			houses.put(pos, houses.get(pos) + 1);
		} else {
			houses.put(pos, 1);
		}
	}
}
