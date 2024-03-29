package br.unitins;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UsuarioResourceTest {

    private String token = "";
    private Header header = null;

    @Inject
    UsuarioService service;

    @BeforeEach
    public void configurarTokenDeAutenticacao() {
        AuthUsuarioDTO authDTO = new AuthUsuarioDTO("maria", "123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authDTO)
                .when()
                .post("/auth");

        token = response.then().extract().header("Authorization").toString();

        header = new Header("Authorization", "Bearer " + token);
    }

    @Test
    public void getAllTest() {
        given().when().get("/usuarios").then().statusCode(200);
    }
//
//    @Test
//    public void testInsert() {
//        TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
//
//        UsuarioDTO dto = new UsuarioDTO( "mari", "123", "Maria da Paz",
//                "ma@gmail.com", "71594987041", null, telefoneDTO, telefoneDTO);
//
//
//        given()
//                .header("Content-Type", "application/json")
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body(dto)
//                .when()
//                .post("/usuarios").then().body("id",  is("Maria da Paz"));
//    }
}
