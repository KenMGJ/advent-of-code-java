package net.kenmgj.advent.challenges;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day04Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day04Challenge.class);

	@Override
	protected int executeChallenge1(String line) {
		return hashStartsWith(line, "00000");
	}

	@Override
	protected int executeChallenge2(String line) {
		return hashStartsWith(line, "000000");
	}

	private int hashStartsWith(String line, String startsWith) {
		int i = 0;
		boolean matchFound = false;

		while (!matchFound) {

			String adventCoin = line + i;
			logger.debug("Possible AdventCoin: " + adventCoin);

			MessageDigest md5;
			String md5HashString = "";

			try {
				 md5 = MessageDigest.getInstance("MD5");
				 byte[] md5Bytes = md5.digest(adventCoin.getBytes("UTF-8"));
				 char[] md5Hash = Hex.encodeHex(md5Bytes);
				 md5HashString = new String(md5Hash);
				 logger.debug("Hash String: " + md5HashString);
			} catch (Exception e) {
			}

			if (md5HashString.startsWith(startsWith)) {
				logger.debug("Found Match: " + md5HashString);
				matchFound = true;
				break;
			}

			i++;
		}

		return i;
	}
}
