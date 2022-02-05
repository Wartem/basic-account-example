package se.wartem.example;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Wartem
 */
public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException, ParseException {
		System.out.println();
		AccountHandler accountHandler = new AccountHandler();
		accountHandler.run();
	}
}
