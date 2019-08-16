package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Country;

/**
 * 
 * @author James (JamesWRC)
 *
 */
public class DatabaseHelper {
	private final static boolean DATABASE_IN_PRODUCTION = true;
	private String databasePath = "jdbc:sqlite::resource:db/MBC.db"; // Database driver to open the database.
	private final static String TABLE_NAME = "MalariaRatesByCountry"; // Table name used.

	public DatabaseHelper() throws SQLException {
		if (DATABASE_IN_PRODUCTION) {
			databasePath = "jdbc:sqlite::resource:db/MBC.db"; // used for when running .jar
		} else {
			databasePath = "jdbc:sqlite:db/MBC.db";// used for testing and building.
		}
	}

	/**
	 * This will print out all countries which malaria exists, with any additional
	 * notes to the console.
	 * 
	 * @throws SQLException
	 */
	public void debug() throws SQLException {

		ResultSet rs = connectDB().executeQuery("select * from " + TABLE_NAME);
		while (rs.next()) {
			int falciparum = rs.getInt("falciparum");
			int vivax = rs.getInt("vivax");
			int malarae = rs.getInt("malariae");
			int ovale = rs.getInt("ovale");
			int knowlsi = rs.getInt("knowlesi");
			String notes = rs.getString("notes");
			// read the result set
			if (vivax != 0 || falciparum != 0 || malarae != 0 || ovale != 0 || knowlsi != 0) {
				System.out.println("############################################   " + rs.getString("countryName")
						+ "   ############################################\n" + "falciparum = " + falciparum + "%\n"
						+ "vivax = " + vivax + "%\n" + "malariae = " + malarae + "%\n" + "ovale = " + ovale + "\n"
						+ "knowlesi = " + knowlsi + "%\n");
				if (notes != null) {
					System.out.println("Additinal Notes : \n" + notes);
				}
				System.out.println(
						"#################################################################################################################################### \n\n\n");
			}
		}

	}

	/**
	 * 
	 * Connects to the database in /bin/resources/
	 * 
	 * @return connection object to the database
	 */
	@SuppressWarnings("null")
	public Statement connectDB() {
		Connection connection = null;
		Statement statement = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection(databasePath);
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					return statement;
				connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e.getMessage());
			}
		}
		return null;

	}

	/**
	 * 
	 * @param countryCode / countryName String
	 * @return malariaType object
	 * @throws SQLException
	 */
	public Country getCountry(String countryCode) throws SQLException {

		Country malariaToReturn = null;
		String query = "";
		/*
		 * Known bug: If 'Ivory Coast (Côte dvoire)' is selected in the GUI version via
		 * the country name drop down box, no country will be found
		 *
		 * Workaround: If countryCode is equal to 'Ivory Coast (Côte dvoire)', then
		 * countryCode becomes 'CI', which is the country code for 'Ivory Coast (Côte
		 * dvoire)'. This means its searching for 'Ivory Coast (Côte dvoire)' via the
		 * country code rather then the name
		 */
		// BEGIN WORK AROUND
		if (countryCode.equals("Ivory Coast (Côte dvoire)")) {
			countryCode = "CI";
		}
		// END WORKAROUND
		boolean returnVal = false;
		countryCode.replace(" ", "\\s");
		if (countryCode.length() >= 4) {
			query = "select * from " + TABLE_NAME + " WHERE countryName = '" + titleize(countryCode) + "'";
			returnVal = true;
		} else if (countryCode.length() == 2) {
			query = "select * from " + TABLE_NAME + " WHERE countryCode = '" + countryCode.toUpperCase() + "'";
			returnVal = true;
		} else {
			// invalid country
			System.out.println("No country found by '" + countryCode + "'. Please try again.\n");
		}
		if (returnVal) {

			ResultSet rs = connectDB().executeQuery(query);
			while (rs.next()) {
				malariaToReturn = new Country(rs.getString("countryCode"), rs.getString("countryName"),
						rs.getInt("falciparum"), rs.getInt("vivax"), rs.getInt("malariae"), rs.getInt("ovale"),
						rs.getInt("knowlesi"), rs.getString("notes"));
			}

			if (rs.getFetchSize() == 0 && !returnVal) {
				System.out.println("Error: Please try again. \n");
			}
		}

		return malariaToReturn;
	}

	/**
	 * 
	 * @param country The identifier for the Country, either a country code OR
	 *                country name.
	 * @param note    The notes attached to the selected country to be edited.
	 * @return
	 */
	public boolean editNote(String country, String note) {

		String updateNote = "";
		boolean returnVal = false;
		if (country.length() >= 4) {
			updateNote = "UPDATE " + TABLE_NAME + " SET notes='" + note + "' WHERE countryName='" + titleize(country)
					+ "'";
			returnVal = true;
		} else if (country.length() == 2) {
			updateNote = "UPDATE " + TABLE_NAME + " SET notes='" + note + "' WHERE countryCode='"
					+ country.toUpperCase() + "'"; // ensure the countryCode is in caps
			returnVal = true;
		} else {
			// not a valid country selection
		}

		try {
			connectDB().executeUpdate(updateNote);
			returnVal = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

	/**
	 * 
	 * @param stringToTitleize String to make the first letter of a word to capitals
	 *                         and the rest of the word to lowercase.
	 * @return
	 */
	private String titleize(String stringToTitleize) { // this method is ONLY here because the outputTrace shows the
														// ENUM values in a lowercase style
		String modifiedString = stringToTitleize.toString().toLowerCase(); // converts the passed in object and converts
		String[] splitCountry = modifiedString.split(" ");
		String returnString = "";
		String bToken = Pattern.quote("("); // token for a bracket char
		String dToken = Pattern.quote("-"); // token for a dash char
		String fToken = Pattern.quote("."); // token for a fullstop char

		for (String currentString : splitCountry) {
			if (!(currentString.equalsIgnoreCase("and") || currentString.equalsIgnoreCase("of")
					|| currentString.equalsIgnoreCase("Of") || currentString.equalsIgnoreCase("the")
					|| currentString.equalsIgnoreCase("minor") ||currentString.equalsIgnoreCase("outlying"))) {
				modifiedString = currentString.substring(0, 1).toUpperCase() + currentString.substring(1); // make first
																											// char
			} else {
				modifiedString = currentString;
			}
			returnString += modifiedString + " ";

		}
		CharSequence bCharSeq = "("; // char sequence for bracket
		if (returnString.contains(bCharSeq)) {
			String[] modifyString = returnString.split(bToken);
			for (String currentString : modifyString) {
				modifiedString = "(" + currentString.substring(0, 1).toUpperCase() + currentString.substring(1); // make first char
				returnString = modifyString[0] + modifiedString;
			}
		}
		CharSequence dCharSeq = "-"; // char sequence for dash
		if (returnString.contains(dCharSeq)) {
			String[] modifyString = returnString.split(dToken);
			for (String currentString : modifyString) {
				modifiedString = "-" + currentString.substring(0, 1).toUpperCase() + currentString.substring(1); // make first char
				returnString = modifyString[0] + modifiedString;
			}
		}
		CharSequence fCharSeq = "."; // char sequence for fullstop
		if (returnString.contains(fCharSeq)) {
			String[] modifyString = returnString.split(fToken);
			for (String currentString : modifyString) {
				modifiedString = "." + currentString.substring(0, 1).toUpperCase() + currentString.substring(1); // make first char
				if (modifyString[0].equals("Virgin Islands (U")) {
					returnString = modifyString[0] + "." + modifyString[1].toUpperCase() + modifiedString;
				} else {
					returnString = modifyString[0] + modifiedString;
				}
			}
		}
		returnString = returnString.substring(0, returnString.length() - 1);
		return returnString; // returns the modified string from the passed in object

	}

	/**
	 * 
	 * @param isCode - Set to True to get an array of country code. - Set to False
	 *               to get an array of county names. - Arrays are of type string.
	 * @return List of strings, containing countryCode / countryNames.
	 * @throws SQLException
	 */
	public List<String> getAllCountryCodes(boolean isCode) throws SQLException {
		String columnName = ""; // column name in database, either countryCode or countryName, depending on the
								// state of isCode.
		if (isCode) {
			// if array of countryCodes is wanted
			columnName = "countryCode";
		} else {
			// if array of country names are wanted.
			columnName = "countryName";
		}

		// execute query based off the state of isCode.
		ResultSet rs = connectDB().executeQuery("SELECT " + columnName + " FROM " + TABLE_NAME);

		// set array size to the number of results received from the database.
		List<String> countryList = new ArrayList<String>();

		while (rs.next()) {
			// add string to array at index.
			countryList.add(rs.getString(columnName));
		}
		// return final array of country name/code
		return countryList;
	}

}
