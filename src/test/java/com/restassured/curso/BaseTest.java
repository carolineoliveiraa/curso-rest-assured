package com.restassured.curso;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;


public class BaseTest {

    @Test
    public void testGetListUsers(){

        RestAssured.given().log().all().

                when().
                    get("https://reqres.in/api/users?page=2").
                then().log().all().
                    statusCode(200).
                    body("page",is(2)).
                    body("per_page", is(6)).
                    body("total", is(12)).
                    body("total_pages", is(2)).
                    body("data.id[0]", is(7)).
                    body("data.first_name[0]", is("Michael")).
                    body("data.last_name[0]", is("Lawson")).
                    body("data.avatar[0]", is("https://reqres.in/img/faces/7-image.jpg"));

    }

    @Test
    public void testGetListUsers2(){

        RestAssured.given().log().all().
                queryParam("page", "2").
                when().
                get("https://reqres.in/api/users?page=2").
                then().log().all().
                statusCode(200).
                body("page",is(2)).
                body("per_page", is(6)).
                body("total", is(12)).
                body("total_pages", is(2)).
                body("data.id[0]", is(7)).
                body("data.first_name[0]", is("Michael")).
                body("data.last_name[0]", is("Lawson")).
                body("data.avatar[0]", is("https://reqres.in/img/faces/7-image.jpg"));

    }

    @Test
    public void testGetSingleUser(){

        RestAssured.given().log().all().
                when().
                get("https://reqres.in/api/users/2").
                then().log().all().
                statusCode(200).
                body("data.id", is(2)).
                body("data.email", is("janet.weaver@reqres.in")).
                body("data.first_name", is("Janet")).
                body("data.last_name", is("Weaver")).
                body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg")).
                body("support.url", is("https://reqres.in/#support-heading")).
                body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }

    @Test
    public void testGetSingleUserNotFound(){

        RestAssured.given().log().all().
                when().
                get("https://reqres.in/api/users/2").
                then().log().all().
                statusCode(200).
                body("data.id", is(2)).
                body("data.email", is("janet.weaver@reqres.in")).
                body("data.first_name", is("Janet")).
                body("data.last_name", is("Weaver")).
                body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg")).
                body("support.url", is("https://reqres.in/#support-heading")).
                body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }
}