// Stefan Miklsovovic
// smiklo11@student.aau.dk

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
    // read an input from a user on a prompt
    static String getChoice() throws IOException {
	BufferedReader reader = new BufferedReader(
		new InputStreamReader(System.in));
	return reader.readLine();
    }

    // prints a help
    static void printHelp() {
	System.out.println("possible commands: ");
	for (UserChoice c : UserChoice.values()) {
	    System.out.println(c.info());
	}
    }

    // prints a prompt
    static void printPrompt() {
	System.out.print("prompt> ");
    }
}
