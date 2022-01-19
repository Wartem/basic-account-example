package se.wartem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Wartem
 */
public class ConsoleInputHandler {

	public ConsoleInputHandler() {
	}

	public static String readAndTrimInput() {

		String input = "";

		try {
			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			input = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if ("0".equals(input)) {
			System.exit(0);
		}
		return input;
	}

	public static long getNumericInput(String input) {
		long inputLong = 0;
		if (input.chars().allMatch(Character::isDigit)) {
			inputLong = Long.parseLong(input);
		}
		return inputLong > 0 ? inputLong : -1;
	}

	public static long getLongFromInput(String message) {
		while (true) {
			System.out.println(message);
			long longInput = getNumericInput(readAndTrimInput());
			if (longInput > 0) {
				return longInput;
			}
			System.out.println(message + " - failed.");
		}
	}

	public static boolean isNameValid(String name) {
		Pattern pattern = Pattern.compile("^[A-Za-z_.]+$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(name);
		boolean matchedAtoZ = matcher.matches();
		return matchedAtoZ && !name.isBlank() && name.length() >= 2;
	}

	public static boolean isPasswordValid(String password) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9_.]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(password);
		boolean matchedAtoZDigit = matcher.matches();
		return !password.isBlank() && password.length() >= 6;
	}

	public static String askForName(String nameType) {
		System.out.println("Please type your " + nameType + " name and press Enter.");
		String name = readAndTrimInput();
		if (!isNameValid(name)) {
			System.out.println(name + " is invalid as " + nameType + "  name.");
			askForName(nameType);
		}
		return name;
	}

	public static String askForPassword() {
		System.out.println("Please type your password and press Enter.");
		String password = readAndTrimInput();
		if (!isPasswordValid(password)) {
			System.out.println(password + " is invalid as password.");
			askForPassword();
		}
		return password;
	}
}