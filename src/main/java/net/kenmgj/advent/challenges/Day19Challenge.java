package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day19Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day19Challenge.class);

	private String molecules;
	private List<Pair<String,String>> replacementMap;
	private Set<String> replacements;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Challenge number: " + challengeNumber);

		if (challengeNumber == 1) {
			replacementMap = new ArrayList<Pair<String, String>>();

			molecules = input.remove(input.size()-1);
			input.remove(input.size()-1);

			populateReplacements(input);
		}
		logger.debug("Molecules: " + molecules);

		printReplacements();

		int returnValue = 0;

		if (challengeNumber == 1) {
			replacements = new HashSet<String>();
			for (Pair<String, String> p : replacementMap) {
				findReplacements(p.getLeft(), p.getRight());
			}
			returnValue = replacements.size();
		} else {

			Set<String> results = new HashSet<String>();
			results.add("e");

			int i = 0;
			//while (true) {
			//	results = generateMolecules(results);
			//	printResults(results);
			//	i++;
			//	if (results.contains(molecules)) {
			//		break;
			//	}
			//}

			returnValue = i;
		}

		return returnValue;
	}

	private void populateReplacements(List<String> input) {
		Pattern pattern = Pattern.compile("(.+) => (.+)");

		for (String s : input) {
			Matcher matcher = pattern.matcher(s);

			if (matcher.find()) {
				String left = matcher.group(1);
				String right = matcher.group(2);

				Pair<String, String> replacement = Pair.of(left, right);
				replacementMap.add(replacement);
			}
		}

	}

	private void printReplacements() {
		for (Pair<String, String> p : replacementMap) {
			logger.debug(p.getLeft() + " => " + p.getRight());
		}
	}

	private void findReplacements(String a, String b) {
		logger.debug("A: " + a);
		logger.debug("B: " + b);
		int i = 0;
		while (i < molecules.length()) {
			logger.debug("Index: " + i + "  Length: " + molecules.length());

			int upperLimit = i + a.length();
			if (upperLimit >= molecules.length()) {
				upperLimit = molecules.length();
			}

			String sub = molecules.substring(i, upperLimit);
			logger.debug("Index: " + i + " " + sub);

			if (sub.equals(a)) {
				logger.debug("Replace " + sub + " with " + b);

				StringBuilder sb = new StringBuilder();
				if (i > 0) {
					sb.append(molecules.substring(0, i));
				}
				sb.append(b);
				sb.append(molecules.substring(i + 1 + (a.length() - 1)));
				logger.debug(molecules + " " + sb.toString());
				replacements.add(sb.toString());
			}

			i += 1;
		}
	}

	private Set<String> generateMolecules(Set<String> molecules) {
		Set<String> newMolecules = new HashSet<String>();

		for (String molecule : molecules) {
			for (Pair<String, String> p : replacementMap) {
				for (String s : allReplaceOne(molecule, p.getLeft(), p.getRight())) {
					newMolecules.add(s);
				}
			}
		}

		return newMolecules;
	}

	private Set<String> allReplaceOne(String string, String find, String replace) {
		Set<String> results = new HashSet<String>();

		int i = 0;
		while (i < string.length()) {
			logger.debug("Find length: " + find.length());
			int offset = i + find.length();
			if (offset > string.length()) {
				offset = string.length();
			}
			String cmp = string.substring(i, offset);
			if (cmp.equals(find)) {
				StringBuilder result = new StringBuilder();
				if (i > 0) {
					result.append(string.substring(0, i));
				}
				result.append(replace);
				result.append(string.substring(i + find.length()));
				logger.debug(string);
				logger.debug(result.toString());
				results.add(result.toString());
			}
			i++;
		}

		return results;
	}

	private void printResults(Set<String> results) {
		for (String s : results) {
			logger.debug(s);
		}
	}
}
