package br.unitins;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;
import br.unitins.projeto.service.tipo_produto.TipoProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TipoProdutoResourceTest {

    private String token = "";
    private Header header = null;

    @Inject
    TipoProdutoService service;

    @BeforeEach
    @Transactional
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
        given().when().get("/tipos-produtos").then().statusCode(200);
    }

    @Test
    public void testInsert() {
        TipoProdutoDTO dto = new TipoProdutoDTO(
                "Cerâmica"
        );

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .post("/tipos-produtos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "descricao", is("Cerâmica"));

    }

    @Test
    public void testUpdate() {
        TipoProdutoDTO dto = new TipoProdutoDTO(
                "Jogo de jant"
        );

        Long id = service.create(dto).id();

        TipoProdutoDTO dtoUpdate = new TipoProdutoDTO(
                "Jogo de jantar"
        );

        given().contentType(ContentType.JSON).header(header).body(dtoUpdate).when().put("/tipos-produtos/" + id).then().statusCode(200);

        TipoProdutoResponseDTO responseDTO = service.findById(id);
        assertThat(responseDTO.descricao(), is("Jogo de jantar"));
    }

    @Test
    public void testDelete() {
        TipoProdutoDTO dto = new TipoProdutoDTO(
                "Cerâmica"
        );

        Long id = service.create(dto).id();

        given().header(header).when().delete("/tipos-produtos/" + id).then().statusCode(204);

        TipoProdutoResponseDTO responseDTO = null;

        try {
            responseDTO = service.findById(id);
        } catch (Exception e) {
        } finally {
            assertNull(responseDTO);
        }
    }

    @Test
    public void testCount() {
        TipoProdutoDTO dto = new TipoProdutoDTO("Prato");

        service.create(dto);

        dto = new TipoProdutoDTO("Barro seco");

        service.create(dto);

        given().contentType(ContentType.JSON).when().get("/tipos-produtos/count").then().equals(2);
    }

    @Test
    public void testSearch() {
        TipoProdutoDTO dto = new TipoProdutoDTO("Barro branco");

        service.create(dto);

        String descricao = "BRANCO";

        given().contentType(ContentType.JSON)
                .when()
                .get("/tipos-produtos/search/" + descricao)
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].descricao", is("Barro branco"));
    }

}
