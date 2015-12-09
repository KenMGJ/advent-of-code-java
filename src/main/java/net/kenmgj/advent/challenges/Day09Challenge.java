package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day09Challenge extends AbstractChallenge {

	Set<String> cities;
	Map<Pair<String, String>, Integer> distanceBetweenCities;

	private Logger logger = LoggerFactory.getLogger(Day09Challenge.class);

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Challenge number: " + challengeNumber);

		cities = new HashSet<String>();
		distanceBetweenCities = new HashMap<Pair<String, String>, Integer>();

		for (String line : input) {
			parseLine(line);
		}

		List<Integer> distances = new ArrayList<Integer>();

		PermutationIterator<String> pi = new PermutationIterator<String>(cities);
		while (pi.hasNext()) {
			List<String> permCities = pi.next();
			int distance = calculateDistance(permCities);
			distances.add(distance);
			logger.debug("distance: " + distance);
		}

		int[] distancesAsInt = new int[distances.size()];
		for (int i = 0; i < distances.size(); i++) {
			distancesAsInt[i] = distances.get(i);
		}

		if (challengeNumber == 1) {
			return NumberUtils.min(distancesAsInt);
		} else {
			return NumberUtils.max(distancesAsInt);
		}
	}

	private int calculateDistance(List<String> cities) {
		int distance = 0;

		String previousCity = "";
		for (String city : cities) {
			if (!previousCity.isEmpty()) {
				logger.debug("Previous city: " + previousCity + " City: " + city);
				logger.debug("City: " + city);
				Pair<String, String> cityPair = Pair.of(previousCity, city);
				distance += distanceBetweenCities.get(cityPair);
			}
			previousCity = city;
		}
		return distance;
	}

	private void parseLine(String line) {
		Pattern pattern = Pattern.compile("(.+) to (.+) = (\\d+)");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String city1 = matcher.group(1);
			String city2 = matcher.group(2);
			Integer distance = Integer.parseInt(matcher.group(3));

			Pair<String, String> sourceToDestination = Pair.of(city1, city2);
			distanceBetweenCities.put(sourceToDestination, distance);
			logger.debug(city1 + " to " + city2 + " = " + distance);

			sourceToDestination = Pair.of(city2, city1);
			distanceBetweenCities.put(sourceToDestination, distance);
			logger.debug(city2 + " to " + city1 + " = " + distance);

			cities.add(city1);
			cities.add(city2);
		}
	}

}
