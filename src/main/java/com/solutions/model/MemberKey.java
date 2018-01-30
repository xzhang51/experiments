package com.solutions.model;

public class MemberKey {
    private String key;
    private String value;

    public MemberKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MemberKey) {
            return this.key.equals(((MemberKey)obj).getKey());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String numberString;
        if (key.length() > 8) {
            numberString = key.substring(key.length()-8);
        } else {
            numberString = key;
        }

        return Integer.parseInt(numberString);
    }
}
