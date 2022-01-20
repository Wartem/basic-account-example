package se.wartem.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Wartem
 */
public class Account {

	private String accountName = "";
	private long accountNumber = 0;
	private String firstName = "";
	private String lastName = "";
	private String hashedPassword = "";
	private long balance = 0;

	MessageDigest messageDigest;

	public Account(String firstName, String lastName, String password, int accountArrayListSize)
			throws NoSuchAlgorithmException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashString(password);
		setAccountNumber(accountArrayListSize);
		generateAccountName();
		this.toString();
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public Account(String accountName, long accountNumber, String firstName, String lastName, String hashedPassword,
			long balance) {
		super();
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.balance = balance;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public MessageDigest getMessageDigest() {
		return messageDigest;
	}

	public void setMessageDigest(MessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAccountName() {
		return accountName;
	}

	public long getBalance() {
		return balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	private void generateAccountName() {
		accountName = firstName + accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	private String hashString(String toBeHashed) throws NoSuchAlgorithmException {
		messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(toBeHashed.getBytes());
		// System.out.println(messageDigest.digest());
		return new BigInteger(messageDigest.digest()).toString();
	}

	public void withdraw(long amount) {
		balance -= (amount > 0 && amount <= balance) ? amount : 0;
		printBalance();
	}

	public void deposit(long amount) {
		balance += amount > 0 ? amount : 0;
		printBalance();
	}

	public boolean login(String password) throws NoSuchAlgorithmException {
		return hashedPassword.equals(hashString(password));
	}

	public void printBalance() {
		System.out.println(accountName + " balance: " + balance);
	}

	@Override
	public String toString() {
		return "Your Account Information:\n" + " {Account Name = " + accountName + ", Account Number = " + accountNumber
				+ ", First name = " + firstName + ", Last name = " + lastName + ", Balance = " + balance + '}';
	}
}