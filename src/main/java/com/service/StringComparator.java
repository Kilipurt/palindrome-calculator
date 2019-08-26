package com.service;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1.length() > o2.length()) {
            return 1;
        }

        if (o1.length() < o2.length()) {
            return -1;
        }

        if (o1.length() == o2.length()) {
            for (int j = 0; j < o2.length(); j++) {
                int numberOfFirst = Character.getNumericValue(o1.charAt(j));
                int numberOfSecond = Character.getNumericValue(o2.charAt(j));

                if (numberOfSecond > numberOfFirst) {
                    return -1;
                }

                if (numberOfSecond < numberOfFirst) {
                    return 1;
                }
            }
        }

        return 0;
    }
}
