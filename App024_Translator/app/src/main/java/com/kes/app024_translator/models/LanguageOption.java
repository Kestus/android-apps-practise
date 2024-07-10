package com.kes.app024_translator.models;

public class LanguageOption {

    String fullName;
    String code;

    public LanguageOption(String name, String code) {
        this.fullName = name;
        this.code = code;
    }

    public LanguageOption(String code) {
        this.code = code;
    }
}
