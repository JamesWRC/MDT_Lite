package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Countries listing the malaria types and likeliness.
 * 
 * @author James (JamesWRC)
 *
 */

public class Country {

	private int falciparum, vivax, malariae, ovale, knowlesi;
	private String countryCode, countryName, notes;
	private static final int charsPerLine = 75;

	/**
	 * 
	 * @param countryCode Get malaria types and percentages by the country code
	 * @param countryName Get malaria types and percentages by the country name
	 * @param falciparum  Approximate percentage of Plasmodium falciparum
	 * @param vivax       Approximate percentage of Plasmodium vivax
	 * @param malariae    Approximate percentage of Plasmodium malariae
	 * @param ovale       Approximate percentage of Plasmodium ovale
	 * @param knowlesi    Approximate percentage of Plasmodium knolwsi
	 * @param notes       Any additional notes relevant to the country and/or
	 *                    malaria types and percentages.
	 */
	public Country(String countryCode, String countryName, int falciparum, int vivax, int malariae, int ovale,
			int knowlesi, String notes) {
		// country code and country name. the user can use either to get a prediction of
		// likeliness of malaria percentage.
		this.countryCode = countryCode;
		this.countryName = countryName;

		// percentages of malaria type likeliness
		this.falciparum = falciparum;
		this.vivax = vivax;
		this.malariae = malariae;
		this.ovale = ovale;
		this.knowlesi = knowlesi;

		// any additional notes about the country
		this.notes = notes;
	}

	//generic getter and setters below.
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getFalciparum() {
		return falciparum;
	}

	public int getVivax() {
		return vivax;
	}

	public int getMalariae() {
		return malariae;
	}

	public int getOvale() {
		return ovale;
	}

	public int getKnowlesi() {
		return knowlesi;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public List<Integer> getOrderedList() {
		List<Integer> orderedList = new ArrayList<Integer>();
		orderedList.add(falciparum);
		orderedList.add(vivax);
		orderedList.add(malariae);
		orderedList.add(ovale);
		orderedList.add(knowlesi);
		Collections.sort(orderedList);
		Collections.reverse(orderedList);
		return orderedList;
		
		//bootleg way to ensure that the values are ordered and are displayed as such.
	}
	
	//a custom 'toString()' method to nicely format and list out each malaria type and its likeliness in an ascending order.
	public String getReport() {
		String toFormat = "";

		//Once set to true, it can't be printed out again
		boolean falciparumSet = false;
		boolean vivaxSet = false;
		boolean malariaeSet = false;
		boolean ovaleSet = false;
		boolean knowlsiSet = false;

		for (int currentPercentage : getOrderedList()) {
			if (falciparum == currentPercentage && !falciparumSet) {
				toFormat += "P.falciparum: \t" + progressBarCLI(falciparum) + "\n";
				falciparumSet = true;
			} else if (vivax == currentPercentage && !vivaxSet) {
				toFormat += "P.Vivax: \t" + progressBarCLI(vivax) + "\n";
				vivaxSet = true;
			} else if (malariae == currentPercentage && !malariaeSet) {
				toFormat += "P.Malariae: \t" + progressBarCLI(malariae) +"\n";
				malariaeSet = true;
			} else if (ovale == currentPercentage && !ovaleSet) {
				toFormat += "P.Ovale: \t" + progressBarCLI(ovale) +"\n";
				ovaleSet = true;
			} else if (knowlesi == currentPercentage && !knowlsiSet) {
				toFormat += "P.Knowlesi: \t" + progressBarCLI(knowlesi) +"\n";
				knowlsiSet = true;
			}

		}
		/*
		 * This segment of code, essentially checks to see if the country has a note attched to it,
		 * if there is, see if it is more then the value determined by 'charsPerLine' default is 75 chars.
		 * If a country has a not and it more then 75 chars long, it will essentially break it up using new lines.
		 * It does this by inserting the new line character '\n' every 75 chars, then looks for the next whitespace.
		 */
		
		if (notes != null) { 
			boolean hasCut = false;
			int length = notes.length();
			if (length > charsPerLine) { 
				int lines = length / charsPerLine;
				int charCount = 75;
				int currentLine = 0;
				while (currentLine != lines && !hasCut) {
					
					for (int i = charCount; i < length; i++) {
						if (notes.charAt(i) == ' ') {
							charCount += 75;
							StringBuilder string = new StringBuilder(notes);
							string.setCharAt(i, '\n');
							string.setCharAt(i + 1, '\t');
							notes = string.toString();

							currentLine++;
							break;
						}
					}
				}
			}
		}
		return String.format("\n \t Report for: %s (%s) \n\n%s", countryName, countryCode, toFormat);
	}
	
	public String toString() {


		return String.format("%s %s", getReport(), getFormatedNotes());
	}
	
	public String getFormatedNotes() {
		String retString = "";
		if(notes == null) {
			retString = "None";
		}else {
			retString = notes;
		}
		return "= = = = = = = = = = = = = =\n Additional Notes: \n\t" + retString
		+ "\n= = = = = = = = = = = = = =\n\n";
	}
	/**
	 * 
	 * @param percentage
	 * 		The percentage of malaria type
	 * @return
	 * 		A string in the form of a percentage and a 'progress bar' displaying hoe likely the malaria type is.
	 */
	public String progressBarCLI(int percentage) {
		final int STEP_AMT = 5; //amount for each 'segment' in the progress bar.
		int amount = percentage / STEP_AMT;
		final String BAR_TEMPLATE = "[                    ]"; // CAN look like this: '[#########          ]' if 50 %

		StringBuilder string = new StringBuilder(BAR_TEMPLATE);
		for(int i = 0; i < amount; i++) {
			string.setCharAt(i+1, '#');
		}
		
		return "~" + percentage + "% "
				+ "\t" + string.toString() ;//+ "\t~ " + percentage + "%";
	}

}
