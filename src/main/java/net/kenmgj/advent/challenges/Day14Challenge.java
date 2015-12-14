package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day14Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day14Challenge.class);

	private class Deer {
		private String name;
		private int flySpeed;
		private int flyDuration;
		private int restDuration;

		public Deer(String name, int flySpeed, int flyDuration, int restDuration) {
			this.name = name;
			this.flySpeed = flySpeed;
			this.flyDuration = flyDuration;
			this.restDuration = restDuration;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Deer other = (Deer) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Deer [name=" + name + ", flySpeed=" + flySpeed + ", flyDuration=" + flyDuration + ", restDuration="
					+ restDuration + "]";
		}

		private Day14Challenge getOuterType() {
			return Day14Challenge.this;
		}
	}

	private Set<Deer> deer;
	private static final int DURATION = 2503;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {

		logger.debug("Challenge Number: " + challengeNumber);

		deer = new HashSet<Deer>();
		for (String line : input) {
			readDeer(line);
		}

		if (challengeNumber == 1) {
			Map<Deer, Pair<Integer, Boolean>> flightResults = calculateFlight(DURATION);
			List<Pair<Deer, Integer>> leaders = calculateMaxDistance(flightResults);

			if (leaders.isEmpty()) {
				return 0;
			} else {
				return leaders.get(0).getRight();
			}
		} else {

			Map<Deer, Integer> leaderboard = new HashMap<Deer, Integer>();
			for (Deer d : deer) {
				leaderboard.put(d, 0);
			}

			for (int i = 1; i < DURATION; i++) {
				Map<Deer, Pair<Integer, Boolean>> flightResults = calculateFlight(i);
				List<Pair<Deer, Integer>> leaders = calculateMaxDistance(flightResults);

				for (Pair<Deer, Integer> entry : leaders) {
					leaderboard.put(entry.getLeft(), leaderboard.get(entry.getLeft()) + 1);
				}
			}

			for (Entry<Deer, Integer> s : leaderboard.entrySet()) {
				logger.debug(s.getKey() + " Points: " + s.getValue());
			}

			if (leaderboard.isEmpty()) {
				return 0;
			} else {
				return getLeaderScore(leaderboard);
			}
		}
	}

	private void readDeer(String line) {
		Pattern pattern = Pattern.compile("^(.+) can fly (\\d+) .* for (\\d+) seconds, but then must rest for (\\d+) seconds.$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String name = matcher.group(1);
			Integer flySpeed = Integer.parseInt(matcher.group(2));
			Integer flyDuration = Integer.parseInt(matcher.group(3));
			Integer restDuration = Integer.parseInt(matcher.group(4));

			Deer deer = new Deer(name, flySpeed, flyDuration, restDuration);
			this.deer.add(deer);

			logger.debug(name + " Fly Speed: " + flySpeed + " Fly Duration: " + flyDuration + " Rest Duration: " + restDuration);
		}
	}

	private Map<Deer, Pair<Integer, Boolean>> calculateFlight(int duration) {
		Map<Deer, Pair<Integer, Boolean>> results = new HashMap<Deer, Pair<Integer, Boolean>>();

		for (Deer d : deer) {
			int i = 0;

			int distance = 0;
			boolean resting = false;

			while (i < duration) {
				for (int j = 0; j < d.flyDuration; j++) {
					resting = false;
					distance += d.flySpeed;
					i++;
					if (i == duration) {
						break;
					}
				}
				if (i == duration) {
					break;
				}
				for (int j = 0; j < d.restDuration; j++) {
					resting = true;
					i++;
					if (i == duration) {
						break;
					}
				}
			}

			Pair<Integer, Boolean> result = Pair.of(distance, resting);
			results.put(d, result);
			logger.debug(d + " Distance: " + distance + " Resting: " + resting);
		}

		return results;
	}

	private List<Pair<Deer, Integer>> calculateMaxDistance(Map<Deer, Pair<Integer, Boolean>> distances) {
		List<Pair<Deer, Integer>> results = new ArrayList<Pair<Deer, Integer>>();

		int maxDistance = 0;

		for (Entry<Deer, Pair<Integer, Boolean>> entry : distances.entrySet()) {
			if (entry.getValue().getLeft() > maxDistance) {
				maxDistance = entry.getValue().getLeft();
			}
		}
		logger.debug("Max Distance: " + maxDistance);

		for (Entry<Deer, Pair<Integer, Boolean>> entry : distances.entrySet()) {
			if (entry.getValue().getLeft() == maxDistance) {
				results.add(Pair.of(entry.getKey(), entry.getValue().getLeft()));
			}
		}

		for (Pair<Deer, Integer> entry : results) {
			logger.debug(entry.getLeft() + " Distance: " + entry.getRight());
		}

		return results;
	}

	private int getLeaderScore(Map<Deer, Integer> leaderboard) {
		int maxScore = 0;

		for (Entry<Deer, Integer> entry : leaderboard.entrySet()) {
			if (entry.getValue() > maxScore) {
				maxScore = entry.getValue();
			}
		}

		return maxScore;
	}
}
