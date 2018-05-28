// Nathan Hull
// CIS675 HW2

// Input:
// digraph G {
//		main [shape=box]; /* this is a comment */
//		main -> parse [weight=8];
//		parse -> execute;
//		main -> init [style=dotted];
//		main -> cleanup;
//		execute -> make_string;
//		init -> make_string;
//		main -> printf [style=bold,label="100 times"];
//		make_string [label="make a\nstring"];
//		node [shape=box,style=filled,color=".7 .3 1.0"];
// 		execute -> compare;
// }

import java.util.*;
import java.io.*;

class Lexer {

	static List<String> keywords;
	static HashMap<String, String> ops;
	static boolean isComment = false;
	static boolean isString = false;

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println(args.length);
			System.out.println("Input error");
			return;
		}

		FileReader fr;
		try {
			fr = new FileReader(args[0]);
		} catch (Exception e) {
			System.out.println("Error finding or opening file");
			return;
		}

		BufferedReader reader = new BufferedReader(fr);
		String line = "";

		// instantiate sets of keywords/ops
		keywords = Arrays.asList("digraph", "graph", "subgraph", "strict", "node", "edge", "shape", "box", "parse", "weight", "init", "style", "dotted", "cleanup", "make_string", "init", "printf", "bold", "label", "filled", "color", "execute", "compare");

		ops = new HashMap();
		ops.put("{", "LEFT-BRACE");
		ops.put("}", "RIGHT-BRACE");
		ops.put("[", "LEFT-BRACKET");
		ops.put("]", "RIGHT-BRACKET");
		ops.put("->", "ARROW");
		ops.put(";", "SEMICOLON");
		ops.put("=", "ASSIGNMENT");
		ops.put("\"", "QUOTATION-MARK");
		ops.put(",", "COMMA");
		ops.put("/*", "COMMENT");

		try {
		while ((line = reader.readLine()) != null) {

			String tokens[] = line.split(" ");
			for (int x = 0; x < tokens.length; x++) {
				String token = tokens[x];
				token = token.trim();

				System.out.println("\nWORD: " + token);

				printToken(token);
			}

		}
		} catch (IOException e) {
			System.out.println("IO Exception");
			try {
				reader.close();
			} catch (IOException f) {
				System.out.println("Error closing reader");
			}
			return;
		}

		try {
			reader.close();
		} catch (IOException e) {
			System.out.println("Error closing reader");
		}
	}

	static void printToken(String token) {
System.out.println("token: " + token);
		if (isString) {
			if (token.equals("\"")) {
				isString = false;
				System.out.println("END-STRING");
			}
			return;
		} else if (isComment) {
			if (token.equals("*/")) {
				isComment = false;
				System.out.println("END-COMMENT");
			}
			return;
		}

		for (char c : token.toCharArray()) {

			if (ops.containsKey(Character.toString(c))) {
				if (token.equals("/*")) {
					isComment = true;
					System.out.println("COMMENT");
					return;
				}
				else if (token.equals("*/")) {
					isComment = false;
					System.out.println("END-COMMENT");
					return;
				}
				else if (token.equals("\"")) {
					isString = !isString;
					if (!isString)
						System.out.println("STRING");
					return;
				}

				if (token.length() > 1) {

					String partsBefore = token.substring(0, token.indexOf(c)).trim();
					String partsAfter = token.substring(token.indexOf(c)+1).trim();
					if (partsBefore.length() > 1) {
						printToken(partsBefore);
					}
					System.out.println(ops.get(Character.toString(c)));

					if (partsAfter.length() > 1) {
						printToken(partsAfter);
					}
				} else {
					System.out.println(ops.get(Character.toString(c)));
				}

				return;
			}
		}

		if (keywords.contains(token)) {
			System.out.println(token.toUpperCase());
			return;
		} else if (ops.containsKey(token)) {

			if (token.equals("/*")) {
				isComment = true;
				System.out.println("COMMENT");
			}
			else if (token.equals("*/")) {
				isComment = false;
				System.out.println("END-COMMENT");
			}
			else if (token.equals("\"")) {
				isString = !isString;
				if (!isString)
					System.out.println("STRING");
			}
			else {
				System.out.println(ops.get(token));
			}

			return;
		}

		// If it's a number...
		try {
			Double.parseDouble(token);
			System.out.println("NUMERIC-VALUE");
			return;
		} catch(NumberFormatException nfe) {}

		// Otherwise, it's an ID
		System.out.println("ID");
	}
}
