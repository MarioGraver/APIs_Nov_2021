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


public class e_comerce {

    //variables
    static  private  String base_url = "webapi.segundamano.mx";
    static  private  String access_token;
    static  private  String account_id;
    static  private  String uuid;
    static  private  String address_id;

     //Funcion crear un token

    private String getToken() {
        RestAssured.baseURI = String.format("https://%s/nga/api/v1.1/private/accounts", base_url);

        Response respos = given()
                .log().all()
                .queryParam("lang", "es")
                .auth().preemptive().basic("ventastesting293659@mailinator.com", "505050")
                .post();

        String body_response = respos.getBody().asString();
        System.out.println("Body response: " + body_response);

        access_token = JsonPath.read(body_response, "$.access_token");
        System.out.println("TOKEN: " + access_token);

        uuid = JsonPath.read(body_response, "$.account.uuid");
        System.out.println(" uuid: " + uuid);

        String datos = uuid + ":" + access_token;

        String encodedAuth = Base64.getEncoder().encodeToString(datos.getBytes());

        return encodedAuth;
    }

    @Test
    public void t01_obtener_categorias(){
        //Configurar URI con nuestra URL base
        RestAssured.baseURI = String.format("https://%s/nga/api/v1/public/categories/insert?lang=es",base_url);
       //hacer el request y guardarlo en response

        Response response = given()
                .log()
                .all()
                .queryParam("lang","es")
                .get();

       String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();
      //  String body_p = response.prettyPrint();
        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
      //  System.out.println("Body response: " + body_p);

        assertEquals(200,response.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("categories"));
    }
   @Test
    public void t02_obtener_token(){
        //congifurar URI
        RestAssured.baseURI = String.format("https://%s/nga/api/v1.1/private/accounts",base_url);
       String token_basic = "dmVudGFzdGVzdGluZzI5MzY1OUBtYWlsaW5hdG9yLmNvbToyOTM2NTk=";
       // String token_basic = "dmVudGFzdGVzdGluZzQ0MDU1MEBtYWlsaW5hdG9yLmNvbTo0NDA1NTA=";

        Response resp = given()
                .log().all()
                .queryParam("lang","es")
                .header("Authorization","Basic " + token_basic)
                .post();

        String body_response = resp.getBody().asString();
        String headers_response = resp.getHeaders().toString();
       // String body_p = resp.prettyPrint();
        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        //System.out.println("Body response: " + body_p);

      //  assertEquals(200, resp.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("access_token"));

    }
   @Test
    public void t03_obtener_tokencon_Basic_Auth_email_pass(){
        //congifurar URI
        RestAssured.baseURI = String.format("https://%s/nga/api/v1.1/private/accounts",base_url);
       // String token_basic = "dmVudGFzdGVzdGluZzI5MzY1OUBtYWlsaW5hdG9yLmNvbToyOTM2NTk";

        Response respos = given()
                .log().all()
                .queryParam("lang","es")
                .auth().preemptive().basic("ventastesting293659@mailinator.com","293659")
               // .header("Authorization","Basic " + token_basic)
                .post();

        String body_response = respos.getBody().asString();
        String headers_response = respos.getHeaders().toString();
        // String body_p = respos.prettyPrint();
        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        //System.out.println("Body response: " + body_p);

        assertEquals(200,respos.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("access_token"));

        access_token = JsonPath.read(body_response, "$.access_token");
        System.out.println("TOKEN: " +  access_token);

        System.out.println(" account_id: " +  JsonPath.read(body_response, "$.account.account_id"));
        System.out.println(" uuid: " +  JsonPath.read(body_response, "$.account.uuid"));

        account_id = JsonPath.read(body_response, "$.account.account_id");
        uuid = JsonPath.read(body_response, "$.account.uuid");

        //console.log("token: ",responseJson.access_token)
        //console.log("account_id: ",responseJson.account.account_id)
        //console.log("uuid: ",responseJson.account.uuid)

    }
   @Test
    public void t04_editar_datos_usuario(){
        //Configurar URI

        RestAssured.baseURI = String.format("https://%s/nga/api/v1/%s",base_url,account_id);

        String body2  = "{\"account\":{\"name\":\"ventastesting293659\",\"phone\":\"1324567989\",\"professional\":false}}";

        Response resp = given()
                .log().all()
                .header("Authorization","tag:scmcoord.com,2013:api " + access_token)
                .header("Content-Type","application/json")
                .header("Origin","https://www.segundamano.mx")
                .body(body2)
                .patch();

        String body_response = resp.getBody().asString();
        String headers_response = resp.getHeaders().toString();
        //String body_p = response.prettyPrint();
        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        //System.out.println("Body response: " + body_p);

        assertEquals(200, resp.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("account"));
        assertNotNull(body_response);
    }
    @Test
    public void t05_crear_direccion(){
     //   https://webapi.segundamano.mx/addresses/v1/create
//declarar la base URL
        RestAssured.baseURI = String.format("https://%s/addresses/v1/create",base_url);

        Response response = given()
                .log().all()
                .auth().preemptive().basic(uuid,access_token)
                .header("content-type","application/x-www-form-urlencoded")
                .formParam("contact", "Carmelina123 Garza")
                .formParam("phone", "1234567899")
                .formParam("rfc", "GALM051234")
                .formParam("zipCode", "80025")
                .formParam("exteriorInfo", "Camuna 21")
                .formParam("region", "5")
                .formParam("municipality", "51")
                .formParam("alias", "Minina la grande")
                .post();

        String body_response = response.getBody().asString();
        System.out.println(("Body response: " + body_response));

        assertEquals(201, response.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("addressID\""));

        address_id = JsonPath.read(body_response, "$.addressID");

        System.out.println("address_id: " + address_id);


    }

    @Test
    public void t06_leer_direccion(){
        //   https://webapi.segundamano.mx/addresses/v1/create

        RestAssured.baseURI = String.format("https://%s/addresses/v1/get/",base_url);

        Response response = given()
                .log().all()
                .auth().preemptive().basic(uuid,access_token)
                .get();

        String body_response = response.getBody().asString();
        System.out.println(("Body response: " + body_response));

        assertEquals(200, response.getStatusCode());
        assertNotNull(body_response);
        assertTrue(body_response.contains("addresses\""));


        assertTrue(body_response.contains(address_id));
}

 @Test
    public void  t07_modificar_direccion(){

     RestAssured.baseURI = String.format("https://%s/addresses/v1/modify/%s",base_url,address_id);

     Response response = given()
             .log().all()
             .auth().preemptive().basic(uuid,access_token)
             .header("content-type","application/x-www-form-urlencoded")
             .formParam("contact", "Carmelina123 Garza")
             .formParam("phone", "1234567899")
             .formParam("rfc", "GALM051234")
             .formParam("zipCode", "80025")
             .formParam("exteriorInfo", "Camelina 123456789")
             .formParam("region", "5")
             .formParam("municipality", "51")
             .formParam("alias", "Minina la grande")
             .put();

     String body_response = response.getBody().asString();
     System.out.println(("Body response: " + body_response));

     assertEquals(200, response.getStatusCode());
     assertNotNull(body_response);
     assertTrue(body_response.contains(address_id));
     assertTrue(body_response.contains("{\"message\":\""+ address_id + " modified correctly\"}"));
 }

 @Test
    public  void t08_borrar_direccion() {
     RestAssured.baseURI = String.format("https://%s/addresses/v1/delete/%s", base_url, address_id);

     Response response = given()
             .log().all()
             .auth().preemptive().basic(uuid, access_token)
             .delete();

     String body_response = response.getBody().asString();
     System.out.println("Body response: " + body_response);

     assertEquals(200, response.getStatusCode());
     assertNotNull(body_response);
     assertTrue(body_response.contains(address_id));
     assertTrue(body_response.contains("{\"message\":\""+ address_id + " deleted correctly\"}"));

 }

     @Test
     public void t09_crear_anuncio(){
        //funcion generar un token
         //leer token
         //hacer request crear anuncio

         String newToken = getToken();

         System.out.println("Test que regresa la funcion: " + newToken);

         RestAssured.baseURI = String.format("https://%s/v2/accounts/%s/up",base_url,uuid);

         String body = "{\"category\":\"8084\",\n" +
                 "\"subject\":\"Reparacion de  maquinas de coser\",\n" +
                 "\"body\":\"Trabajo garantizado, todos los equipos de primera\",\n" +
                 "\"price\":\"100\",\"region\":\"13\",\"municipality\":\"346\",\"area\":\"89482\",\n" +
                 "\"phone_hidden\":\"true\",\"show_phone\":\"false\",\n" +
                 "\"contact_phone\":\"1131311456\"}";

         Response response = given()
                 .log().all()
                 .header("Authorization", "Basic " + newToken)
                 .header("Content-Type", "application/json")
                 .header("x-source", "PHOENIX_DESKTOP")
                 .header("Accept", "application/json, text/plain, */*")
                 .body(body)
                 .post();

         String body_response = response.getBody().asString();
         System.out.println("Body response: " + body_response);

         assertEquals(200, response.getStatusCode());
         assertNotNull(body_response);
         assertTrue(body_response.contains("ad_id"));
    }

}