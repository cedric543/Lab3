
package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// hi
/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // Maps to store the country codes and names
    private Map<String, String> countryCodeToName;
    private Map<String, String> countryNameToCode;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        // Initialize the maps
        countryCodeToName = new HashMap<>();
        countryNameToCode = new HashMap<>();

        try {
            // Load all lines from the file
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Skip the header and process each subsequent line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\t");

                String country = parts[0].trim();
                String alpha3Code = parts[2].trim().toLowerCase();
                countryCodeToName.put(alpha3Code, country);
                countryNameToCode.put(country, alpha3Code);

            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Error loading country code data from file: " + filename, ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code, or null if not found
     */
    public String fromCountryCode(String code) {
        if (code == null) {
            return null;
        }
        return countryCodeToName.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country, or null if not found
     */
    public String fromCountry(String country) {
        if (country == null) {
            return null;
        }
        return countryNameToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryCodeToName.size();
    }
}
