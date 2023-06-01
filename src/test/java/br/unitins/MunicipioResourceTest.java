package br.unitins;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.service.municipio.MunicipioService;
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
public class MunicipioResourceTest {
//
//    @Inject
//    MunicipioService service;
//
//    @Test
//    public void getAllTest() {
//        given().when().get("/municipios").then().statusCode(200);
//    }
//
//    @Test
//    public void testInsert() {
//        MunicipioDTO dto = new MunicipioDTO(
//                "Colinas",
//                1L
//        );
//
//        given().contentType(ContentType.JSON)
//                .body(dto)
//                .when()
//                .post("/municipios")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "descricao", is("Colinas"),
//                        "estado.sigla", is("TO"));
//    }
//
//    @Test
//    public void testUpdate() {
//        MunicipioDTO dto = new MunicipioDTO("Divinópolis", 2L);
//
//        Long id = service.create(dto).id();
//
//        MunicipioDTO dtoUpdate = new MunicipioDTO("Divinópolis", 1L);
//
//        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/municipios/" + id).then().statusCode(200);
//
//        MunicipioResponseDTO responseDTO = service.findById(id);
//        assertThat(responseDTO.descricao(), is("Divinópolis"));
//        assertThat(responseDTO.estado().sigla(), is("TO"));
//    }
//
//    @Test
//    public void testDelete() {
//        MunicipioDTO dto = new MunicipioDTO(
//                "Colinas",
//                1L
//        );
//
//        Long id = service.create(dto).id();
//
//        given().when().delete("/municipios/" + id).then().statusCode(204);
//
//        MunicipioResponseDTO responseDTO = null;
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
//        MunicipioDTO dto = new MunicipioDTO(
//                "Augustinópolis",
//                1L
//        );
//
//        service.create(dto);
//
//        dto = new MunicipioDTO(
//                "Rio de Janeiro",
//                4L
//        );
//
//        given().contentType(ContentType.JSON).when().get("/municipios/count").then().equals(2);
//    }
//
//    @Test
//    public void testSearch() {
//        MunicipioDTO dto = new MunicipioDTO(
//                "Pico do Papagaio",
//                1L
//        );
//        service.create(dto);
//
//        String descricao = "Pico";
//
//        given().contentType(ContentType.JSON)
//                .when()
//                .get("/municipios/search/" + descricao)
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(1),
//                        "[0].descricao", is("Pico do Papagaio"),
//                        "[0].estado.sigla", is("TO"));
//    }

}
