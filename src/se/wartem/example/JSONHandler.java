package se.wartem.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class JSONHandler {

	final public static String jsonFileName = "accounts.json";

	public static ArrayList<Account> readAccountsFromFile() throws IOException, ParseException {

		ArrayList<Account> accountList = new ArrayList<>();
		if (new File(jsonFileName).exists()) {
			JSONParser parser = new JSONParser();

			Object obj = parser.parse(new FileReader(jsonFileName));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray accountContent = (JSONArray) jsonObject.get("Accounts");
			Iterator<JSONObject> iterator = accountContent.iterator();

			while (iterator.hasNext()) {
				JSONObject content = iterator.next();

				Account account = new Account((String) content.get("accountName"), (Long) content.get("accountNumber"),
						(String) content.get("firstName"), (String) content.get("lastName"),
						(String) content.get("hashedPassword"), (Long) content.get("balance"));
				accountList.add(account);
			}
		}
		return accountList;
	}

	public static void writeAccountsToFile(ArrayList<Account> arraylist) throws FileNotFoundException {

		PrintWriter printWriter = new PrintWriter(jsonFileName);
		JSONObject mainJsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (Account account : arraylist) {

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("accountName", account.getAccountName());
			jsonObj.put("accountNumber", account.getAccountNumber());
			jsonObj.put("firstName", account.getFirstName());
			jsonObj.put("lastName", account.getLastName());
			jsonObj.put("hashedPassword", account.getHashedPassword());
			jsonObj.put("balance", account.getBalance());

			jsonArray.add(jsonObj);
		}

		mainJsonObj.put("Accounts", jsonArray);

		printWriter.write(mainJsonObj.toString());
		printWriter.flush();
		printWriter.close();
	}

}
