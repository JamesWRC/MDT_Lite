package Driver;

import java.sql.SQLException;
import java.util.Scanner;

import database.DatabaseHelper;
import model.Country;

public class Driver {
	@SuppressWarnings("unused")
	private static DatabaseHelper db;
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		db = new DatabaseHelper();
		run();

	}

	public static void run() throws SQLException {
		String countrySelect ="";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Type 'exit' to quit or Select Country: \n > ");
			countrySelect = scanner.nextLine();
			
			if(countrySelect.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye. Thank you for using MDT");
				System.exit(0);
			}else {
				Country country = db.getCountry(countrySelect);
				if(country != null) {
					System.out.println(country.toString());
				}
			}
		}
	}

}
