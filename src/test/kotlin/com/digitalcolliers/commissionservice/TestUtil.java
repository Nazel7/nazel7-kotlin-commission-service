package com.digitalcolliers.commissionservice;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    public String objectStringConverter(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
