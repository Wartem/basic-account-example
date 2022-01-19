package se.wartem;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Wartem
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		AccountHandler accountHandler = new AccountHandler();
		accountHandler.run();
	}

}
