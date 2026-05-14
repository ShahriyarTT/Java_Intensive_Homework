package com.example;

import java.io.InputStream;
import java.util.Properties;

public class AppConfigur {

    private String stateFile;
    private boolean availabilityChangeEnabled;

    public AppConfigur() {

        try {

            Properties properties = new Properties();

            InputStream input =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            properties.load(input);

            stateFile =
                    properties.getProperty("state.file");

            availabilityChangeEnabled =
                    Boolean.parseBoolean(
                            properties.getProperty("book.availability.change.enabled")
                    );


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String getStateFile() {
        return stateFile;
    }



    public boolean isAvailabilityChangeEnabled() {
        return availabilityChangeEnabled;
    }

}
