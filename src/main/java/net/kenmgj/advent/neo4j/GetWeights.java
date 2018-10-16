package net.kenmgj.advent.neo4j;

import java.io.File;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;

public class GetWeights {

	private static final int MAX_DEPTH = 5;

	private final static String[] TEST_INPUT = {
			"MATCH (n) DETACH DELETE n",
			"CREATE (n:Program { name: 'pbga', weight: 66 })",
			"CREATE (n:Program { name: 'xhth', weight: 57 })",
			"CREATE (n:Program { name: 'ebii', weight: 61 })",
			"CREATE (n:Program { name: 'havc', weight: 66 })",
			"CREATE (n:Program { name: 'ktlj', weight: 57 })",
			"CREATE (n:Program { name: 'fwft', weight: 72 })",
			"CREATE (n:Program { name: 'qoyq', weight: 66 })",
			"CREATE (n:Program { name: 'padx', weight: 45 })",
			"CREATE (n:Program { name: 'tknk', weight: 41 })",
			"CREATE (n:Program { name: 'jptl', weight: 61 })",
			"CREATE (n:Program { name: 'ugml', weight: 68 })",
			"CREATE (n:Program { name: 'gyxo', weight: 61 })",
			"CREATE (n:Program { name: 'cntj', weight: 57 })",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'fwft' AND b.name = 'ktlj' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'fwft' AND b.name = 'cntj' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'fwft' AND b.name = 'xhth' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'padx' AND b.name = 'pbga' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'padx' AND b.name = 'havc' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'padx' AND b.name = 'qoyq' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'tknk' AND b.name = 'ugml' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'tknk' AND b.name = 'padx' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'tknk' AND b.name = 'fwft' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'ugml' AND b.name = 'gyxo' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'ugml' AND b.name = 'ebii' CREATE (a)-[r:HOLDS]->(b)",
			"MATCH (a:Program), (b:Program) WHERE a.name = 'ugml' AND b.name = 'jptl' CREATE (a)-[r:HOLDS]->(b)",
	};

	public enum RelationshipTypes implements RelationshipType {
		HOLDS
	}

	public static void main( String... args ) throws Exception
	{
		GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(new File("/Users/kgiroux/embedded"));

		/*
		try (Transaction tx = db.beginTx()) {
			for (String s : Input.INPUT) {
				db.execute(s);
			}
			tx.success();
		}
		 */

		Node startingNode = null;

		try (Transaction tx = db.beginTx()) {
			// Result result = db.execute("MATCH (a) WHERE NOT ()-[:HOLDS]->(a) RETURN a");
			Result result = db.execute("MATCH (a) WHERE a.name = 'bvrxeo' RETURN a");
			if (result.hasNext()) {
				startingNode = (Node) result.next().get("a");
				tx.success();
			}
		}

		if (startingNode != null) {
			try (Transaction tx = db.beginTx()) {
				TraversalDescription traversal = db.traversalDescription()
						.depthFirst()
						.relationships(RelationshipTypes.HOLDS, Direction.OUTGOING)
						.evaluator(Evaluators.toDepth(1))
						.uniqueness(Uniqueness.RELATIONSHIP_PATH);
				;

				for (Path position : traversal.traverse(startingNode)) {
					for (Node n : position.nodes()) {
						System.out.print(n.getProperty("name") + " ");
					}
					System.out.println("");
				}
			}
		}

		db.shutdown();
	}
}