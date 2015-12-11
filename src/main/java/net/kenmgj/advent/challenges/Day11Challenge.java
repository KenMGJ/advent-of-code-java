package net.kenmgj.advent.challenges;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day11Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day11Challenge.class);
	private static Password newPassword;

	private class Password {
		private static final char A = 'a';
		private static final char Z = 'z';

		private char[] password;

		public Password(String password) {
			if (password.length() < 8) {
				password = password.substring(0, 7);
			}
			this.password = password.toCharArray();
		}

		public Password increment() {
			StringBuilder nextString = new StringBuilder();

			boolean carry = true;
			for (int i = password.length - 1; i >= 0; i--) {
				char c = password[i];
				if (carry) {
					c = getNextChar(c);
					if (c != A) {
						carry = false;
					}
				}
				nextString.append(c);
			}

			return new Password(nextString.reverse().toString());
		}

		private char getNextChar(char c) {
			return (c == Z) ? A : (char)(c + 1);
		}

		@Override
		public String toString() {
			StringBuilder toString = new StringBuilder();
			for (int i = 0; i < password.length; i++) {
				toString.append(password[i]);
			}
			return toString.toString();
		}

		public boolean isValid() {
			boolean hasStraight = false;
			for (int i = 0; i < password.length - 3; i++) {
				if (password[i] + 2 == password[i+2] && password[i+1] + 1 == password[i+2]) {
					hasStraight = true;
					break;
				}
			}
			logger.debug("Has Straight: " + this.toString() + " " + hasStraight);
			if (!hasStraight) {
				return false;
			}

			boolean hasInvalidChars = false;
			for (int i = 0; i < password.length; i++) {
				if (password[i] == 'i' || password[i] == 'o' || password[i] == 'l') {
					hasInvalidChars = true;
					break;
				}
			}
			logger.debug("Has Invalid Chars: " + this.toString() + " " + hasInvalidChars);
			if (hasInvalidChars) {
				return false;
			}

			int foundRepeatedChar = 0;
			char repeatedChar = 'A';

			int i = 0;
			while (i < password.length - 1) {
				if (password[i] == password[i+1] && password[i] != repeatedChar) {
					if (foundRepeatedChar == 0) {
						foundRepeatedChar = 1;
						repeatedChar = password[i];
						i += 2;
					} else {
						foundRepeatedChar = 2;
						break;
					}
				} else {
					i++;
				}
			}

			return (foundRepeatedChar == 2) ? true : false;
		}
	}

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {

		for (String line : input) {
			logger.debug("Line: " + line);

			Password p = (challengeNumber == 1 && newPassword == null) ? new Password(line) : newPassword;
			logger.debug("Password: " + p);

			do {
				p = p.increment();
				logger.debug("Password: " + p);
			} while (!p.isValid());

			newPassword = p;
			logger.info("Next password: " + p);
		}

		return 0;
	}

}
