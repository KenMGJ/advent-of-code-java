package net.kenmgj.advent.neo4j;

import java.util.HashSet;
import java.util.Set;

public class Program {
	private final String name;
	private int weight;
	private Set<Program> children;

	public Program(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public Program(String name) {
		this(name, 0);
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Set<Program> getChildren() {
		if (children == null) {
			children = new HashSet<Program>();
		}
		return children;
	}

	@Override
	public String toString() {
		return "Program [name=" + name + ", weight=" + weight + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Program other = (Program) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
}
