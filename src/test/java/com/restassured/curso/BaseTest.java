package com.restassured.curso;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class BaseTest {

    @Test
    public void testGetListUsers(){

        RestAssured.given().log().all().

                when().
                    get("https://reqres.in/api/users?page=2").
                then().log().all().
                    statusCode(200);


    }
}
