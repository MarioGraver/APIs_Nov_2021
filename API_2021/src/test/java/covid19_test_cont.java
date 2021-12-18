import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.jayway.jsonpath.JsonPath;

import java.util.Base64;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
//los corre de manera ascendente.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class covid19_test_cont {

   //variables
    static  private  String base_url_covid = "covid19-api.com";

    @Test
    public void t01_obtener_Mexico(){
       //Configurar URI
        RestAssured.baseURI = String.format("https://%s/country?name=Mexico&format=json",base_url_covid);

       //Her el request y guardarlo en response

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .queryParam("name","Mexico")
                .queryParam("format","json;")
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();
        assertTrue(body_response.contains("country"));
        assertTrue(body_response.contains("Mexico"));
        assertNotNull(body_response);

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

    }

    @Test
    public void t02_obtener_todos_los_paises(){

        RestAssured.baseURI = String.format("https://%s/country/all?format=json",base_url_covid);

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .queryParam("format","json;")
                .get();


        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();
        assertTrue(body_response.contains("Afghanistan"));
        assertTrue(body_response.contains("confirmed"));
        assertNotNull(body_response);

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

    }

    @Test
    public void t03_obtener_codigo_paises(){
        RestAssured.baseURI = String.format("https://%s/country/code?code=MX&format=json",base_url_covid);

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .queryParam("code","MX")
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();
        assertTrue(body_response.contains("latitude"));
        assertTrue(body_response.contains("Mexico"));
        assertNotNull(body_response);

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());
    }

    @Test
    public void t04_Obt_reporte_por_pais_fecha(){
        RestAssured.baseURI = String.format("https://%s/report/country/code?code=MX&date=2021-01-01&date-format=YYYY-DD-MM&format=json",base_url_covid);

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .queryParam("code","MX")
                .queryParam("date","2021-01-01")
                .queryParam("date-format","YYYY-DD-MM")
                .queryParam("format","json")
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();
        assertTrue(body_response.contains("provinces"));
        assertTrue(body_response.contains("country"));
        assertNotNull(body_response);

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());
    }

    @Test
    public void t05_Obt_reporte_por_pais(){
        RestAssured.baseURI = String.format("https://%s/report/country/name?name=Mexico&date=2021-01-01&date-format=YYYY-MM-DD&format=json",base_url_covid);

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .queryParam("name","Mexico")
                .queryParam("date","2021-01-01")
                .queryParam("date-format","YYYY-DD-MM")
                .queryParam("format","json")
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("latitude"));
        assertTrue(body_response.contains("2021-01-01"));
        assertNotNull(body_response);

    }

}
