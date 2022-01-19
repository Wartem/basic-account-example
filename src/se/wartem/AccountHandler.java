package se.wartem;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Wartem
 */
public class AccountHandler {

	ArrayList<Account> accountArrayList;
	ConsoleInputHandler input;

	public AccountHandler() {
		input = new ConsoleInputHandler();
		this.accountArrayList = new ArrayList<Account>();
	}

	private void mainMenu() throws NoSuchAlgorithmException {
		System.out.println("""
				\nWelcome
				Account Creation: Type 1 and press Enter.
				Account Login: Type 2 and press Enter.
				Exit Program: Type 0 and press Enter.""");
		String inputString = this.input.readAndTrimInput();

		switch (inputString) {
		case "0":
			System.exit(0);
		case "1":
			createAccount();
			break;
		case "2":
			loginToAccount();
			break;
		default:
			System.out.println("Incorrect input");
		}
	}

	private void createAccount() throws NoSuchAlgorithmException {
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

	private boolean loginToAccount() throws NoSuchAlgorithmException {
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

	private void loggedIn(Account account) {
		boolean loggedIn = true;
		while (loggedIn) {
			System.out.println("\nWelcome " + account.getFirstName() + " " + account.getLastName());
			System.out.println("""
					Account Details: Type 1 and press Enter.
					Deposit: Type 2 and press Enter.
					Withdraw: Type 3 and press Enter.
					Logout: Type 4 and press Enter.
					Exit program: Type 0 and press Enter.""");

			String inputString = this.input.readAndTrimInput();

			switch (inputString) {
			case "0":
				System.exit(0);
			case "1":
				System.out.println(account.toString());
				break;
			case "2":
				runDeposit(account);
				break;
			case "3":
				runWithdraw(account);
				break;
			case "4":
				loggedIn = false;
				break;
			default:
				System.out.println("Incorrect input.");
			}
		}
	}

	private void runDeposit(Account account) {
		account.deposit(input.getLongFromInput("Deposit: Type the amount you want to deposit and press Enter."));
	}

	private void runWithdraw(Account account) {
		account.withdraw(input.getLongFromInput("Deposit: Type the amount you want to withdraw and press Enter."));
	}

	public void run() throws NoSuchAlgorithmException, InterruptedException {
		while (true) {
			Thread.sleep(100);
			mainMenu();
		}
	}
}
