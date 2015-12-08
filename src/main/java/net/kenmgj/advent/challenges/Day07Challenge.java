package net.kenmgj.advent.challenges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day07Challenge extends AbstractChallenge {

	private Logger logger = LoggerFactory.getLogger(Day07Challenge.class);

	private static int VALUE_OF_B = 0;

	public enum Ops { AND, OR, AND_V, OR_V, LSHIFT, RSHIFT, NOT };

	private class Wire {

		private String name;
		private Integer value;
		private Ops op;
		private String[] inputs;

		public Wire(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public Wire(String name, String[] inputs) {
			this.name = name;
			this.inputs = inputs;
		}

		public Wire(String name, Ops op, String[] inputs) {
			this.name = name;
			this.op = op;
			this.inputs = inputs;
		}
	}

	private Map<String, Wire> circuit;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Lines in file: " + input.size());

		circuit = new HashMap<String, Wire>();

		for (String line : input) {
			logger.debug("Challenge Number: " + challengeNumber);
			logger.debug("Line: " + line);

			processLine(line);
		}

		if (VALUE_OF_B != 0) {
			circuit.get("b").value = VALUE_OF_B;
		}

		int value = getValue("a");
		VALUE_OF_B = value;
		return value;
	}

	private void processLine(String line) {

		Wire wire;

		Pattern pattern = Pattern.compile("^(\\d+) -> ([a-z]+)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(2), Integer.parseInt(matcher.group(1)));
			circuit.put(wire.name, wire);
			return;
		}

		pattern = Pattern.compile("^([a-z]+) -> ([a-z]+)$");
		matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(2), new String[] { matcher.group(1) });
			circuit.put(wire.name, wire);
			return;
		}

		pattern = Pattern.compile("^([a-z]+) (AND|OR) ([a-z]+) -> ([a-z]+)$");
		matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(4), Ops.valueOf(matcher.group(2)),
					new String[] { matcher.group(1), matcher.group(3) });
			circuit.put(wire.name, wire);
			return;
		}

		pattern = Pattern.compile("^(\\d+) (AND|OR) ([a-z]+) -> ([a-z]+)$");
		matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(4), Ops.valueOf(matcher.group(2) + "_V"),
					new String[] { matcher.group(1), matcher.group(3) });
			circuit.put(wire.name, wire);
			return;
		}

		pattern = Pattern.compile("^([a-z]+) (LSHIFT|RSHIFT) (\\d+) -> ([a-z]+)$");
		matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(4), Ops.valueOf(matcher.group(2)),
					new String[] { matcher.group(1), matcher.group(3) });
			circuit.put(wire.name, wire);
			return;
		}

		pattern = Pattern.compile("^NOT ([a-z]+) -> ([a-z]+)$");
		matcher = pattern.matcher(line);

		if (matcher.find()) {
			wire = new Wire(matcher.group(2), Ops.NOT,
					new String[] { matcher.group(1) });
			circuit.put(wire.name, wire);
		}
	}

	public int getValue(String wireName) {
		Wire wire = circuit.get(wireName);

		if (wire.value == null) {
			if (wire.op == null) {
				wire.value = getValue(wire.inputs[0]);
			} else {
				switch (wire.op) {
				case AND:
					wire.value = getValue(wire.inputs[0]) & getValue(wire.inputs[1]);
					break;
				case OR:
					wire.value = getValue(wire.inputs[0]) | getValue(wire.inputs[1]);
					break;
				case AND_V:
					wire.value = Integer.parseInt(wire.inputs[0]) & getValue(wire.inputs[1]);
					break;
				case OR_V:
					wire.value = Integer.parseInt(wire.inputs[0]) | getValue(wire.inputs[1]);
					break;
				case LSHIFT:
					wire.value = getValue(wire.inputs[0]) << Integer.parseInt(wire.inputs[1]);
					break;
				case RSHIFT:
					wire.value = getValue(wire.inputs[0]) >> Integer.parseInt(wire.inputs[1]);
					break;
				case NOT:
					wire.value = ~getValue(wire.inputs[0]) & 65535;
					break;
				}
			}
		}

		return wire.value;
	}
}
