package se.wartem.example;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Wartem
 */
public class AccountHandler {

	private ArrayList<Account> accountArrayList;
	boolean run = true;

	public AccountHandler() throws IOException, ParseException {
		this.accountArrayList = new ArrayList<>();
		loadStoredAccounts();
	}

	private void mainMenu() throws NoSuchAlgorithmException, IOException {
		System.out.println("""
						Welcome

				Account Creation: Type 1 and press Enter.
				Account Login: Type 2 and press Enter.
				List accounts: Type 3 and press Enter.

				Exit Program: Type 0 and press Enter.""");
		String inputString = ConsoleInputHandler.readAndTrimInput();

		if ("0".equals(inputString)) {
			try {
				systemExit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		switch (inputString) {
			case "0" -> systemExit();
			case "1" -> createAccount();
			case "2" -> loginToAccount();
			case "3" -> listAccounts();
			default -> System.out.println("Incorrect input");
		}
	}

	private void loadStoredAccounts() throws IOException, ParseException {
		accountArrayList = JSONHandler.readAccountsFromFile();
	}

	private void listAccounts() {
		System.out.println("Number of accounts: " + accountArrayList.size());
		for (Account account : accountArrayList) {
			System.out.println(account.getAccountName());
		}
		System.out.println();
	}

	private void createAccount() throws NoSuchAlgorithmException {

		Account account = new Account(ConsoleInputHandler.askForName("First"),
				ConsoleInputHandler.askForName("Last"),
				ConsoleInputHandler.askForPassword(),
				accountArrayList.size() + 1);
		accountArrayList.add(account);
		System.out.println("Account created: " + account.getAccountName() + ". " + account);
	}

	private Account findAccount(String accountName) throws NoSuchAlgorithmException {
		for (Account account : accountArrayList) {
			if (account.getAccountName().equals(accountName)) {
				return account;
			}
		}
		return new Account("NULL", "NULL", "NULL", -1);
	}

	private boolean loginToAccount() throws NoSuchAlgorithmException, IOException {
		System.out.println("Please type your account name and press Enter.");
		Account account = findAccount(ConsoleInputHandler.readAndTrimInput());
		if (account.getAccountNumber() < 1) {
			System.out.println("The account name does not exist.");
			return false;
		}

		if (account.login(ConsoleInputHandler.askForPassword())) {
			loggedIn(account);
			return true;
		}
		System.out.println("Password is incorrect.");
		return false;
	}

	private void loggedIn(Account account) throws IOException {
		boolean loggedIn = true;
		while (loggedIn) {
			System.out.println("\nWelcome " + account.getFirstName() + " " + account.getLastName());
			System.out.println(account + "\n");
			System.out.println("""
					Deposit: Type 1 and press Enter.
					Withdraw: Type 2 and press Enter.

					Logout: Type 3 and press Enter.
					Exit program: Type 0 and press Enter.""");

			String inputString = ConsoleInputHandler.readAndTrimInput();

			switch (inputString) {
				case "0" -> systemExit();
				case "1" -> runDeposit(account);
				case "2" -> runWithdraw(account);
				case "3" -> loggedIn = false;
				default -> System.out.println("Incorrect input.");
			}
		}
	}

	private void runDeposit(Account account) {
		account.deposit(ConsoleInputHandler.getLongFromInput("Deposit: Type the amount you want to deposit and press Enter."));
	}

	private void runWithdraw(Account account) {
		account.withdraw(ConsoleInputHandler.getLongFromInput("Deposit: Type the amount you want to withdraw and press Enter."));
	}

	public void systemExit() throws IOException {
		JSONHandler.writeAccountsToFile(accountArrayList);
		System.out.println("System exits.");
		System.exit(0);
	}

	public void run() throws NoSuchAlgorithmException, InterruptedException, IOException {
		while (run) {
			Thread.sleep(100);
			mainMenu();
		}
	}
}
