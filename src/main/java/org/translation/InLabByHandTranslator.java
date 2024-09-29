package org.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {

    public static final String CANADA = "can";

    // Define the translations in a map
    private static final Map<String, String> TRANSLATIONS = new HashMap<>();

    static {
        // German translation
        TRANSLATIONS.put("de", "Kanada");

        // English translation
        TRANSLATIONS.put("en", "Canada");

        // Chinese translation
        TRANSLATIONS.put("zh", "加拿大");

        // Spanish translation
        TRANSLATIONS.put("es", "Canadá");

        // French translation
        TRANSLATIONS.put("fr", "Canada");
    }

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        if (CANADA.equals(country)) {
            return new ArrayList<>(TRANSLATIONS.keySet());
        }
        return new ArrayList<>();
    }

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        if (CANADA.equals(country)) {
            return TRANSLATIONS.getOrDefault(language, null);
        }
        return null;
    }
}
