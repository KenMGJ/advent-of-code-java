package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Day12Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day12Challenge.class);

	@Override
	protected int executeChallenge1(String line) {
		return findValueInJson(line, false);
	}

	@Override
	protected int executeChallenge2(String line) {
		return findValueInJson(line, true);
	}

	private int findValueInJson(String line, boolean ignoreRed) {
		int total = 0;

		ObjectMapper mapper = new ObjectMapper();
		Object root;
		try {
			root = mapper.readValue(line, Object.class);
			total = findValue(root, ignoreRed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return total;
	}

	@SuppressWarnings("unchecked")
	private int findValue(Object root, boolean ignoreRed) {
		int total = 0;

		if (root instanceof ArrayList) {
			logger.debug("Object is an ArrayList");
			List<Object> items = (ArrayList<Object>)root;
			for (Object o : items) {
				total += findValue(o, ignoreRed);
			}
		} else {
			if (root instanceof LinkedHashMap) {
				logger.debug("Object is a Linked Hash Map");
				Map<String, Object> map = (LinkedHashMap<String, Object>)root;
				if (ignoreRed && map.containsValue("red")) {
					logger.debug("Ignoring object: found value red");
					total += 0;
				} else {
					for (Object o : map.values()) {
						total += findValue(o, ignoreRed);
					}
				}
			} else {
				if (root instanceof Integer) {
					logger.debug("Found Integer object");
					total += (Integer)root;
				}
			}
		}

		return total;
	}

}
