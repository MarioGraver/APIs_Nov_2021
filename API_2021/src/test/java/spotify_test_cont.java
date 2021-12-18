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

public class spotify_test_cont {

    //variables
    static private String base_url_spot = "api.spotify.com";
    static private String token_basic = "BQClpcci2t8ChENejim90UT8ZZl3C6z-Rqg0J-8_Eo4wPtIDeZjZ3TSJ_0sOUqNiHzIXrQkIOfHaab7jSa_CcJ1tLPd_imlflGw1MArnRj4e7XgAEjcyoAnZdSqHkQUlBuKD4T26_x-5h0VEw3GymhpQ3VVlJ54gf6yyUnDaHOrnGv3T0_rj2_KMKTTJMoHhq45oWJH6g6o2vZvIb5s6TGwcbshOfPZkHgGVmQ6QSaN461lsPTL_kRrqNwciNCTTu68OJH-CHOuohC0BQwqW8Dpvr3I57OuFtT7rXxOb4p-1YRtNoML7-eihbjAU";


    @Test
    public void t01_obtener_album() {
        RestAssured.baseURI = String.format("https://%s/v1/albums/7ydMeYrv8bFFRkkHepoJM4/tracks", base_url_spot);

        Response response = given()
                .log().all()
                .header("Authorization", "Bearer " + token_basic)
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("available_markets"));
        assertTrue(body_response.contains("disc_number"));
        assertNotNull(body_response);

    }

    @Test
    public void t02_obtener_user_info() {
        RestAssured.baseURI = String.format("https://%s/v1/me", base_url_spot);

        Response response = given()
                .log().all()
                .header("Authorization", "Bearer " + token_basic)
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("Master"));
        assertTrue(body_response.contains("Mario.api.graveri@gmail.com"));
        assertNotNull(body_response);

    }

    @Test
    public void t03_obtener_personalized_recomendations() {
        RestAssured.baseURI = String.format("https://%s/v1/views/personalized-recommendations?timestamp=2021-11-29T19%3A36%3A27.417Z&platform=web&content_limit=10&limit=20&types=album%2Cplaylist%2Cartist%2Cshow%2Cstation%2Cepisode&image_style=gradient_overlay&include_external=audio&country=MX&locale=en&market=from_token", base_url_spot);

        Response response = given()
                .log().all()
                .header("Authorization", "Bearer " + token_basic)
                /*.queryParam("timestamp","2021-11-29T19%3A36%3A27.417Z")
                .queryParam("platform","web")
                .queryParam("content_limit","10")
                .queryParam("limit","20")
                .queryParam("types","album%2Cplaylist%2Cartist%2Cshow%2Cstation%2Cepisode")
                .queryParam("image_style","gradient_overlay")
                .queryParam("include_external","audio")
                .queryParam("country","MX")
                .queryParam("locale","en")
                .queryParam("market","from_token")*/
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("content"));
        assertTrue(body_response.contains("collaborative"));
        assertNotNull(body_response);
    }

    @Test
    public void t04_obtener_play_list() {
        RestAssured.baseURI = String.format("https://%s/v1/playlists/37i9dQZF1DXdPec7aLTmlC/tracks", base_url_spot);

        Response response = given()
                .log().all()
                .header("Authorization", "Bearer " + token_basic)
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("external_urls"));
        assertTrue(body_response.contains("uri"));
        assertNotNull(body_response);

    }
    @Test
    public void t05_obtener_users() {
        RestAssured.baseURI = String.format("https://%s/v1/users/spotify", base_url_spot);

        Response response = given()
                .log().all()
                .header("Authorization", "Bearer " + token_basic)
                .get();

        String body_response = response.getBody().asString();
        String headers_response = response.getHeaders().toString();

        System.out.println("Body response: " + body_response);
        System.out.println("Headers response: " + headers_response);
        System.out.println("Body response: " + response.getStatusCode());

        assertTrue(body_response.contains("display_name"));
        assertTrue(body_response.contains("policies"));
        assertNotNull(body_response);

    }

}

