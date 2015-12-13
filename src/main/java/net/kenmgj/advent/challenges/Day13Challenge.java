package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day13Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day13Challenge.class);

	private static final String MY_NAME = "KenMGJ";

	private List<String> guests;
	private Map<Pair<String, String>, Integer> happiness;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {

		guests = new ArrayList<String>();
		happiness = new HashMap<Pair<String, String>, Integer>();

		for (String line : input) {
			populateStructures(line);
		}

		if (challengeNumber == 2) {
			addMeToGuestList();
		}

		return findBestArrangement();
	}

	private void populateStructures(String line) {
		Pattern pattern = Pattern.compile("^(.+) would (gain|lose) (\\d+) happiness units by sitting next to (.+)\\.$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String guest1 = matcher.group(1);
			String gainOrLoss = matcher.group(2);
			int happy = Integer.parseInt(matcher.group(3));
			if ("lose".equals(gainOrLoss)) {
				happy *= -1;
			}
			String guest2 = matcher.group(4);

			if (!guests.contains(guest1)) {
				guests.add(guest1);
			}
			Pair<String, String> guestPair = Pair.of(guest1, guest2);
			happiness.put(guestPair, happy);
			logger.debug(guest1 + " " + guest2 + " " + happy);
		}
	}

	private void addMeToGuestList() {
		for (String guest : guests) {
			Pair<String, String> pair = Pair.of(MY_NAME, guest);
			happiness.put(pair, 0);
			pair = Pair.of(guest, MY_NAME);
			happiness.put(pair, 0);
		}
		guests.add(MY_NAME);
	}

	private int findBestArrangement() {
		PermutationIterator<String> pi = new PermutationIterator<String>(guests);

		int bestScore = 0;

		while (pi.hasNext()) {
			List<String> permGuests = pi.next();
			logger.debug("Permutation: " + StringUtils.join(permGuests, ", "));

			int score = 0;
			for (int i = 0; i < permGuests.size(); i++) {
				int h = (i == 0) ? permGuests.size() - 1 : i - 1;
				int j = (i == permGuests.size() - 1) ? 0 : i + 1;
				score += happiness.get(Pair.of(permGuests.get(i), permGuests.get(h))) +
						happiness.get(Pair.of(permGuests.get(i), permGuests.get(j)));
			}
			logger.debug("Score: " + score);

			if (score > bestScore) {
				logger.debug("New best score: " + score);
				bestScore = score;
			}
		}

		return bestScore;
	}
}
