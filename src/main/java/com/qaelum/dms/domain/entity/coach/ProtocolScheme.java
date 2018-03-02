package com.qaelum.dms.domain.entity.coach;

public class ProtocolScheme {
    private String protocolKey;
    private int year;
    private String language;

    public ProtocolScheme(String protocolKey, int year, String language) {
        this.protocolKey = protocolKey;
        this.year = year;
        this.language = language;
    }

    public String getProtocolKey() {
        return protocolKey;
    }

    public int getYear() {
        return year;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "ProtocolScheme{" +
                "protocolKey='" + protocolKey + '\'' +
                ", year=" + year +
                ", language='" + language + '\'' +
                '}';
    }
}