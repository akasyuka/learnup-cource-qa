package category;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.restassured.filter.log.LogDetail.*;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTests {
    static Properties properties = new Properties();
    static RequestSpecification logRequestSpecification;
    static ResponseSpecification responseSpecification;
    public static ResponseSpecification deleteResponseSpec;
    static ResponseSpecification categoriesResponseSpec;

    @BeforeAll
    static void beforeAll() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");

        logRequestSpecification = new RequestSpecBuilder()
                .log(METHOD)
                .log(URI)
                .log(BODY)
                .log(HEADERS)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        categoriesResponseSpec =new ResponseSpecBuilder()
                .log(ALL)
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(4L), TimeUnit.SECONDS)
                .build();
        deleteResponseSpec = new ResponseSpecBuilder()
                .expectContentType("")
                .build();
        RestAssured.requestSpecification = logRequestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }
}