package utils;

import org.testng.annotations.DataProvider;

public class Data {
    @DataProvider(name = "postDataProvider")
    public static Object[][] postDataProvider() {
        return new Object[][] {
                {"title1", "examplebody1", "user1"},
                {"title2", "examplebody2", "user2"},
                {"title3", "examplebody3", "user3"}
        };
    }
}


