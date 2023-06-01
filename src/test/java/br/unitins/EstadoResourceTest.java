package br.unitins;

import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.service.estado.EstadoService;
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
public class EstadoResourceTest {

//    @Inject
//    EstadoService service;
//
//    @Test
//    public void getAllTest() {
//        given().when().get("/estados").then().statusCode(200);
//    }
//
//    @Test
//    public void testInsert() {
//        EstadoDTO dto = new EstadoDTO("am", "Amazônas");
//
//        given().contentType(ContentType.JSON)
//                .body(dto)
//                .when()
//                .post("/estados")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "sigla", is("AM"),
//                        "descricao", is("Amazônas"));
//    }
//
//    @Test
//    public void testUpdate() {
//        EstadoDTO dto = new EstadoDTO("mg", "Minas Gera");
//
//        Long id = service.create(dto).id();
//
//        EstadoDTO dtoUpdate = new EstadoDTO("mg", "Minas Gerais");
//
//        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/estados/" + id).then().statusCode(200);
//
//        EstadoResponseDTO estadoResponseDTO = service.findById(id);
//        assertThat(estadoResponseDTO.sigla(), is("MG"));
//        assertThat(estadoResponseDTO.descricao(), is("Minas Gerais"));
//    }
//
//    @Test
//    public void testDelete() {
//        EstadoDTO dto = new EstadoDTO("ba", "Bahia");
//
//        Long id = service.create(dto).id();
//
//        given().when().delete("/estados/" + id).then().statusCode(204);
//
//        EstadoResponseDTO estadoResponseDTO = null;
//
//        try {
//            estadoResponseDTO = service.findById(id);
//        } catch (Exception e) {
//        } finally {
//            assertNull(estadoResponseDTO);
//        }
//    }
//
//    @Test
//    public void testCount() {
//        EstadoDTO dto = new EstadoDTO("rs", "Rio Grande do Sul");
//
//        service.create(dto);
//
//        dto = new EstadoDTO("MA", "Maranhão");
//
//        service.create(dto);
//
//        given().contentType(ContentType.JSON).when().get("/estados/count").then().equals(2);
//    }
//
//    @Test
//    public void testSearch() {
//        EstadoDTO dto = new EstadoDTO("CE", "Ceará");
//
//        service.create(dto);
//
//        String sigla = "CE";
//
//        given().contentType(ContentType.JSON)
//                .when()
//                .get("/estados/search/" + sigla)
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(1),
//                        "[0].sigla", is("CE"),
//                        "[0].descricao", is("Ceará"));
//    }

}
