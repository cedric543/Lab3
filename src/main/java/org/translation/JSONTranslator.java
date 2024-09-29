package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private Map<String, JSONObject> countryMap;  // Stores countries and their JSON data
    private List<String> countryCodes;            // List of country codes

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            countryMap = new HashMap<>();
            countryCodes = new ArrayList<>();

            // Populate the countryMap and countryCodes
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                String alpha3Code = country.getString("alpha3");
                countryMap.put(alpha3Code, country);
                countryCodes.add(alpha3Code);
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = new ArrayList<>();
        JSONObject countryData = countryMap.get(country);

        if (countryData != null) {
            for (String key : countryData.keySet()) {
                if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                    languages.add(key); // Add language codes (excluding metadata keys)
                }
            }
        }

        return languages;
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countryCodes); // Return a copy of the country codes
    }

    @Override
    public String translate(String country, String language) {
        JSONObject countryData = countryMap.get(country);

        if (countryData != null && countryData.has(language)) {
            return countryData.getString(language); // Return translation if available
        }

        return null; // Return null if country or language is not found
    }
}
