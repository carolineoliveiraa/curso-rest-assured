package com.restassured.curso;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;


public class BaseTest {

    @Test
    public void testGetListUsers() {

        RestAssured.given().log().all().

                when().
                get("https://reqres.in/api/users?page=2").
                then().log().all().
                statusCode(200).
                body("page", is(2)).
                body("per_page", is(6)).
                body("total", is(12)).
                body("total_pages", is(2)).
                body("data.id[0]", is(7)).
                body("data.first_name[0]", is("Michael")).
                body("data.last_name[0]", is("Lawson")).
                body("data.avatar[0]", is("https://reqres.in/img/faces/7-image.jpg"));

    }

    @Test
    public void testGetListUsers2() {

        RestAssured.given().log().all().
                queryParam("page", "2").
                when().
                get("https://reqres.in/api/users?page=2").
                then().log().all().
                statusCode(200).
                body("page", is(2)).
                body("per_page", is(6)).
                body("total", is(12)).
                body("total_pages", is(2)).
                body("data.id[0]", is(7)).
                body("data.first_name[0]", is("Michael")).
                body("data.last_name[0]", is("Lawson")).
                body("data.avatar[0]", is("https://reqres.in/img/faces/7-image.jpg"));

    }

    @Test
    public void testGetSingleUser() {

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
    public void testGetSingleUserNotFound() {

        RestAssured.given().log().all().
                pathParams("id", "23").
                when().
                get("https://reqres.in/api/users/{id}").
                then().log().all()
                .statusCode(404).body("$", anEmptyMap());
    }

    @Test
    public void testGetListResourceSize() {

        RestAssured.given().log().all().
                when().
                get("https://reqres.in/api/unknown").
                then().log().all()
                .statusCode(200)
                .body("data.size()", is(6));
    }


    @Test
    public void testPostCreateUser() {

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "morpheus");
        jsonAsMap.put("job", "leader");

        RestAssured.given().contentType(ContentType.JSON)
                .body(jsonAsMap).when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .statusCode(201)
                .body("name", is(jsonAsMap.get("name")))
                .body("job", is("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());

    }

    @Test
    public void TestUpdateSingleUser() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "morpheus");
        jsonAsMap.put("job", "leader");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(jsonAsMap).when()
                .put("https://reqres.in/api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is(jsonAsMap.get("name")))
                .body("job", is(jsonAsMap.get("job")))
                .body("updatedAt", containsString(LocalDate.now().toString()));
    }

    @Test
    public void testDeleteUser(){
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .delete("https://reqres.in/api/users/2")
                .then().log().all()
                .statusCode(204);
    }
}