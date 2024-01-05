package com.example;

import com.example.utils.ApplicationProperties;
import io.restassured.RestAssured;
import org.testng.annotations.*;

public class BaseClass {
    public String tokens=ApplicationProperties.INSTANCE.getToken();

    @BeforeSuite
    public void beforeSuite(){
        RestAssured.baseURI=ApplicationProperties.INSTANCE.getUrl();
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("After Suite Completed");
    }

}