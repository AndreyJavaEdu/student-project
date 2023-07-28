package edu.pet_project.studentorder.config;

import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties;

    public static String getProperty(String name){
        if (properties == null) {
            Config.class.getClassLoader().getResourceAsStream("dao.properties");
        }
        return properties.getProperty(name);
    }
}
