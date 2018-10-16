package net.kenmgj.advent.neo4j;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;

public class BalanceWeight {

	// Driver objects are thread-safe and are typically made available application-wide.
	Driver driver;

	private HashMap<String, Program> knownPrograms;
	private Program rootProgram;

	public BalanceWeight(String uri, String user, String password)
	{
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		knownPrograms = new HashMap<String, Program>();
	}

	private void getProgramData()
	{
		try (Session session = driver.session())
		{
			StatementResult result = session.run(
					"MATCH r = (a)-[:HOLDING*1..10]->(b) WHERE a.name = {name} RETURN r",
					parameters("name", "ahnofa"));

			while (result.hasNext())
			{
				Record record = result.next();
				Path path = record.get("r").asPath();

				Program prevProgram = null;

				for (Node node : path.nodes()) {
					Program currProgram;

					String name = node.get("name").asString();
					int weight = node.get("weight").asInt();

					if (prevProgram == null) {
						if (rootProgram == null) {
							rootProgram = new Program(name, weight);

							knownPrograms.put(name, rootProgram);
						}
						else {
							if (!rootProgram.getName().equals(name)) {
								throw new RuntimeException("ugh!");
							}
						}
						currProgram = rootProgram;
					}
					else {
						if (knownPrograms.containsKey(name)) {
							currProgram = knownPrograms.get(name);
						}
						else {
							currProgram = new Program(name, weight);

							knownPrograms.put(name, currProgram);
							prevProgram.getChildren().add(currProgram);
						}
					}
					prevProgram = currProgram;
				}
			}
		}
	}

	private void balanceWeights() {
		for (Program p : knownPrograms.get("luralcy").getChildren() /* rootProgram.getChildren() */) {
			if (p.getName().equals("bvrxeo")) {
				System.out.println(p + " total: " + getTotalWeight(1, p));
			}
		}
	}

	private int getTotalWeight(int level, Program p) {
		Set<Program> c = p.getChildren();
		if (c == null || c.isEmpty()) {
			return p.getWeight();
		}
		else {
			int total = p.getWeight();
			Map<Integer, Integer> ints = new HashMap<Integer, Integer>();
			for (Program child : c) {
				int ct = getTotalWeight(level + 1, child);
				if (ints.containsKey(ct)) {
					ints.put(ct, ints.get(ct) + 1);
				}
				else {
					ints.put(ct, 1);
				}
				total += ct;
			}
			int out = 0;
			for (Entry<Integer, Integer> i : ints.entrySet()) {
				out += i.getKey() * i.getValue();
			}
			out += p.getWeight();
			System.out.println(p.getName() + " " + level + " " + ints + " " + out + " " + p.getWeight());
			return total;
		}
	}

	public void close()
	{
		// Closing a driver immediately shuts down all open connections.
		driver.close();
	}

	public static void main(String... args)
	{
		BalanceWeight example = new BalanceWeight("bolt://localhost:7687", "neo4j", "Th3@-B0x");
		example.getProgramData();
		example.balanceWeights();
		example.close();
	}
}
