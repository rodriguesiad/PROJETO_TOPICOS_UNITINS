package br.unitins;

import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;
import br.unitins.projeto.service.tipo_produto.TipoProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TipoProdutoResourceTest {
//
//    @Inject
//    TipoProdutoService service;
//
//    @Test
//    public void getAllTest() {
//        given().when().get("/tipos-produtos").then().statusCode(200);
//    }
//
//    @Test
//    public void testInsert() {
//        TipoProdutoDTO dto = new TipoProdutoDTO(
//                "Cerâmica"
//        );
//
//        given().contentType(ContentType.JSON)
//                .body(dto)
//                .when()
//                .post("/tipos-produtos")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "descricao", is("Cerâmica"));
//    }
//
//    @Test
//    public void testUpdate() {
//        TipoProdutoDTO dto = new TipoProdutoDTO(
//                "Jogo de jant"
//        );
//
//        Long id = service.create(dto).id();
//
//        TipoProdutoDTO dtoUpdate = new TipoProdutoDTO(
//                "Jogo de jantar"
//        );
//
//        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/tipos-produtos/" + id).then().statusCode(200);
//
//        TipoProdutoResponseDTO responseDTO = service.findById(id);
//        assertThat(responseDTO.descricao(), is("Jogo de jantar"));
//    }
//
//    @Test
//    public void testDelete() {
//        TipoProdutoDTO dto = new TipoProdutoDTO(
//                "Cerâmica"
//        );
//
//        Long id = service.create(dto).id();
//
//        given().when().delete("/tipos-produtos/" + id).then().statusCode(204);
//
//        TipoProdutoResponseDTO responseDTO = null;
//
//        try {
//            responseDTO = service.findById(id);
//        } catch (Exception e) {
//        } finally {
//            assertNull(responseDTO);
//        }
//    }
//
//    @Test
//    public void testCount() {
//        TipoProdutoDTO dto = new TipoProdutoDTO("Prato");
//
//        service.create(dto);
//
//        dto = new TipoProdutoDTO("Barro seco");
//
//        service.create(dto);
//
//        given().contentType(ContentType.JSON).when().get("/tipos-produtos/count").then().equals(2);
//    }
//
//    @Test
//    public void testSearch() {
//        TipoProdutoDTO dto = new TipoProdutoDTO("Barro branco");
//
//        service.create(dto);
//
//        String descricao = "BRANCO";
//
//        given().contentType(ContentType.JSON)
//                .when()
//                .get("/tipos-produtos/search/" + descricao)
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(1),
//                        "[0].descricao", is("Barro branco"));
//    }

}
