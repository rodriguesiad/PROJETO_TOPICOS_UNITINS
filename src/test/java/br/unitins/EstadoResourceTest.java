package br.unitins;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.service.estado.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class EstadoResourceTest {

    private String token = "";
    private Header header = null;

    @Inject
    EstadoService service;

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
        given().when().get("/estados").then().statusCode(200);
    }

    @Test
    public void testInsert() {
        EstadoDTO dto = new EstadoDTO("am", "Amazônas");

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .post("/estados")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "sigla", is("AM"),
                        "descricao", is("Amazônas"));
    }

    @Test
    public void testUpdate() {
        EstadoDTO dto = new EstadoDTO("mg", "Minas Gera");

        Long id = service.create(dto).id();

        EstadoDTO dtoUpdate = new EstadoDTO("mg", "Minas Gerais");

        given().contentType(ContentType.JSON).header(header).body(dtoUpdate).when().put("/estados/" + id).then().statusCode(200);

        EstadoResponseDTO estadoResponseDTO = service.findById(id);
        assertThat(estadoResponseDTO.sigla(), is("MG"));
        assertThat(estadoResponseDTO.descricao(), is("Minas Gerais"));
    }

    @Test
    public void testDelete() {
        EstadoDTO dto = new EstadoDTO("ba", "Bahia");

        Long id = service.create(dto).id();

        given().header(header).when().delete("/estados/" + id).then().statusCode(204);

        EstadoResponseDTO estadoResponseDTO = null;

        try {
            estadoResponseDTO = service.findById(id);
        } catch (Exception e) {
        } finally {
            assertNull(estadoResponseDTO);
        }
    }

    @Test
    public void testSearch() {
        EstadoDTO dto = new EstadoDTO("CE", "Ceará");

        service.create(dto);

        String sigla = "CE";

        given().contentType(ContentType.JSON)
                .when()
                .get("/estados/search/" + sigla)
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].sigla", is("CE"),
                        "[0].descricao", is("Ceará"));
    }

}
