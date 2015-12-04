package net.kenmgj.advent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import net.kenmgj.advent.challenges.AbstractChallenge;

public class AdventOfCode {

	private static final String CLASS_NAME_PLACEHOLDER = "net.kenmgj.advent.challenges.DayXXChallenge";

	public static void main(String[] args) throws Exception {

		int challengeNumber = Integer.parseInt(args[0]);

		if (challengeNumber > 0 && challengeNumber <= 25) {
			List<String> fileContents = new ArrayList<String>();
			fileContents = FileUtils.readLines(new File(args[1]));

			String challengeNum = "" + challengeNumber;
			if (challengeNumber < 10) {
				challengeNum = "0" + challengeNum;
			}

			String className = CLASS_NAME_PLACEHOLDER.replace("XX", challengeNum);
			Object challenge = Class.forName(className).getConstructor().newInstance();
			if (challenge instanceof AbstractChallenge) {
				Pair<Integer, Integer> results = ((AbstractChallenge)challenge).executeChallenge(fileContents);
				System.out.println("Challenge 1:\t" + results.getLeft() + "\n" + "Challenge 2:\t" + results.getRight());
			}
		}
	}
}
