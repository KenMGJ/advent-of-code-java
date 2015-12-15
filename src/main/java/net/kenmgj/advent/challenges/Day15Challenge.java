package net.kenmgj.advent.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day15Challenge extends AbstractChallenge {

	private class IngredientProperties {
		int capacity;
		int durability;
		int flavor;
		int texture;
		int calories;

		public IngredientProperties(int capacity, int durability, int flavor,
				int texture, int calories) {
			this.capacity = capacity;
			this.durability = durability;
			this.flavor = flavor;
			this.texture = texture;
			this.calories = calories;
		}
	}

	private Map<String, IngredientProperties> ingredients;
	private Logger logger = LoggerFactory.getLogger(Day15Challenge.class);

	private static final int TOTAL_TO = 100;
	private static final int CALORIES = 500;

	@Override
	protected int executeChallenge(List<String> input, int challengeNumber) {
		logger.debug("Challenge number: " + challengeNumber);
		ingredients = new HashMap<String, IngredientProperties>();

		for (String line : input) {
			addIngredientToList(line);
		}

		int maxScore = 0;
		boolean checkCalories = (challengeNumber == 2) ? true : false;

		for (List<Integer> l : generateRecipes(TOTAL_TO, ingredients.keySet().size())) {
			Map<String, Integer> recipe = new HashMap<String, Integer>();

			int i = 0;
			for (String key : ingredients.keySet()) {
				recipe.put(key, l.get(i));
				i++;
			}

			logger.debug("Recipe: " + recipe);

			int score = calculateScore(recipe, checkCalories);
			logger.debug("Score: " + score);

			if (score > maxScore) {
				maxScore = score;
			}
		}

		return maxScore;
	}

	private void addIngredientToList(String line) {
		Pattern pattern = Pattern.compile("^(.+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String name = matcher.group(1);
			int capacity = Integer.parseInt(matcher.group(2));
			int durability = Integer.parseInt(matcher.group(3));
			int flavor = Integer.parseInt(matcher.group(4));
			int texture = Integer.parseInt(matcher.group(5));
			int calories = Integer.parseInt(matcher.group(6));

			IngredientProperties ingredientProperties = new IngredientProperties(capacity,
					durability, flavor, texture, calories);
			ingredients.put(name, ingredientProperties);
			logger.debug("<" + name + "> capacity:" + capacity + " durability:" + durability +
					" flavor:" + flavor + " texture:" + texture + " calories:" + calories);
		}
	}

	private int calculateScore(Map<String, Integer> recipe, boolean checkCalories) {
		int capacity = 0;
		int durability = 0;
		int flavor = 0;
		int texture = 0;
		int calories = 0;

		for (String ingredient : recipe.keySet()) {
			int amount = recipe.get(ingredient);
			IngredientProperties properties = ingredients.get(ingredient);
			capacity +=  amount * properties.capacity;
			durability += amount * properties.durability;
			flavor += amount * properties.flavor;
			texture += amount * properties.texture;
			calories += amount * properties.calories;
		}

		logger.debug("Subscore: capacity:" + capacity + " durability:" + durability +
					" flavor:" + flavor + " texture:" + texture + " calories:" + calories);

		if (capacity < 0) {
			capacity = 0;
		}
		if (durability < 0) {
			durability = 0;
		}
		if (flavor < 0) {
			flavor = 0;
		}
		if (texture < 0) {
			texture = 0;
		}

		int score = capacity * durability * flavor * texture;
		if (checkCalories && calories != CALORIES) {
			score = 0;
		}
		logger.debug("Score: " + score);

		return score;
	}

	private List<List<Integer>> generateRecipes(int total, int number) {
		List<List<Integer>> returnValue = new ArrayList<List<Integer>>();

		if (number == 1) {
			List<Integer> value = new ArrayList<Integer>();
			value.add(total);
			returnValue.add(value);
		} else {
			for (int i = 1; i <= total - number + 1; i++) {
				for (List<Integer> list : generateRecipes(total - i, number - 1)) {
					list.add(0, i);
					returnValue.add(list);
				}
			}
		}

		return returnValue;
	}
}
