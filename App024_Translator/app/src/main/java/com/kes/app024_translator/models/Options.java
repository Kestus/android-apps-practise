package com.kes.app024_translator.models;

import com.google.mlkit.nl.translate.TranslateLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Options {

    public enum LANGUAGES {
        ENGLISH,
        RUSSIAN,
        SWAHILI,
        ALBANIAN,
        WELSH
    }

    private static final Map<LANGUAGES, LanguageOption> options = loadOptions();

    private static Map<LANGUAGES, LanguageOption> loadOptions() {
        Map<LANGUAGES, LanguageOption> options = new HashMap<>();
        options.put(
                LANGUAGES.ENGLISH,
                new LanguageOption("English", TranslateLanguage.ENGLISH)
        );
        options.put(
                LANGUAGES.RUSSIAN,
                new LanguageOption("Russian", TranslateLanguage.RUSSIAN)
        );
        options.put(
                LANGUAGES.SWAHILI,
                new LanguageOption("Swahili", TranslateLanguage.SWAHILI)
        );
        options.put(
                LANGUAGES.ALBANIAN,
                new LanguageOption("Albanian", TranslateLanguage.ALBANIAN)
        );
        options.put(
                LANGUAGES.WELSH,
                new LanguageOption("Welsh", TranslateLanguage.WELSH)
        );
        return options;
    }

    public static List<String> getOptionsStrings() {
        ArrayList<String> strings = new ArrayList<>();
        // List options Strings consistently
        for (int i = 0; i < LANGUAGES.values().length; i++) {
            LanguageOption option = options.get(LANGUAGES.values()[i]);
            assert option != null;
            strings.add(option.fullName);
        }
        return strings;
    }

    public static LanguageOption getOption(LANGUAGES lang) {
        return options.get(lang);
    }

    public static LanguageOption getOption(int position) {
        LANGUAGES lang = LANGUAGES.values()[position];
        return getOption(lang);
    }

    public static String getLanguageCode(LANGUAGES lang) {
        return Objects.requireNonNull(options.get(lang)).code;
    }

    public static String getLanguageCode(int position) {
        LANGUAGES lang = LANGUAGES.values()[position];
        return Objects.requireNonNull(options.get(lang)).code;
    }

    public static String getLanguageFullName(LANGUAGES lang) {
        return Objects.requireNonNull(options.get(lang)).fullName;
    }

    public static String getLanguageFullName(int position) {
        LANGUAGES lang = LANGUAGES.values()[position];
        return Objects.requireNonNull(options.get(lang)).fullName;
    }



}
