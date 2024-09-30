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

    private Map<String, JSONObject> countryMap;
    private List<String> countryCodes;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    // hi
    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            countryMap = new HashMap<>();
            countryCodes = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                String alpha3Code = country.getString("alpha3");
                countryMap.put(alpha3Code, country);
                countryCodes.add(alpha3Code);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = new ArrayList<>();
        JSONObject countryData = countryMap.get(country);

        if (countryData != null) {
            for (String key : countryData.keySet()) {
                if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                    languages.add(key);
                }
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String country, String language) {
        JSONObject countryData = countryMap.get(country);

        if (countryData != null && countryData.has(language)) {
            return countryData.getString(language);
        }

        return null;
    }
}
