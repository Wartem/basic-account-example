package se.wartem.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Wartem
 */
public class AccountHandler {

	private static ArrayList<Account> accountArrayList;
	private ConsoleInputHandler input;

	public AccountHandler() throws FileNotFoundException, IOException, ParseException {
		input = new ConsoleInputHandler();
		this.accountArrayList = new ArrayList<Account>();
		loadStoredAccounts();
	}

	private void mainMenu() throws NoSuchAlgorithmException, IOException {
		System.out.println("""
						Welcome

				Account Creation: Type 1 and press Enter.
				Account Login: Type 2 and press Enter.
				List accounts: Type 3 and press Enter.

				Exit Program: Type 0 and press Enter.""");
		String inputString = this.input.readAndTrimInput();

		switch (inputString) {
		case "0":
			systemExit();
		case "1":
			createAccount();
			break;
		case "2":
			loginToAccount();
			break;
		case "3":
			listAccounts();
			break;
		default:
			System.out.println("Incorrect input");
		}
	}

	private void loadStoredAccounts() throws FileNotFoundException, IOException, ParseException {
		accountArrayList = JSONHandler.readAccountsFromFile();
	}

	private void listAccounts() {
		System.out.println("Number of accounts: " + accountArrayList.size());
		for (Account account : accountArrayList) {
			System.out.println(account.getAccountName());
		}
		System.out.println();
	}

	private void createAccount() throws NoSuchAlgorithmException, FileNotFoundException {

		Account account = new Account(input.askForName("First"), input.askForName("Last"), input.askForPassword(),
				accountArrayList.size() + 1);
		accountArrayList.add(account);
		System.out.println("Account created: " + account.getAccountName() + ". " + account.toString());
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
		Account account = findAccount(input.readAndTrimInput());
		if (account.getAccountNumber() < 1) {
			System.out.println("The account name does not exist.");
			return false;
		}

		if (account.login(input.askForPassword())) {
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
			System.out.println(account.toString() + "\n");
			System.out.println("""
					Deposit: Type 1 and press Enter.
					Withdraw: Type 2 and press Enter.

					Logout: Type 3 and press Enter.
					Exit program: Type 0 and press Enter.""");

			String inputString = this.input.readAndTrimInput();

			switch (inputString) {
			case "0":
				systemExit();
				break;
			case "1":
				runDeposit(account);
				break;
			case "2":
				runWithdraw(account);
				break;
			case "3":
				loggedIn = false;
				break;
			default:
				System.out.println("Incorrect input.");
			}
		}
	}

	private void runDeposit(Account account) throws FileNotFoundException {
		account.deposit(input.getLongFromInput("Deposit: Type the amount you want to deposit and press Enter."));
	}

	private void runWithdraw(Account account) throws FileNotFoundException {
		account.withdraw(input.getLongFromInput("Deposit: Type the amount you want to withdraw and press Enter."));
	}

	public static void systemExit() throws IOException {
		JSONHandler.writeAccountsToFile(accountArrayList);
		System.out.println("System exits.");
		System.exit(0);
	}

	public void run() throws NoSuchAlgorithmException, InterruptedException, IOException {
		while (true) {
			Thread.sleep(100);
			mainMenu();
		}
	}
}
