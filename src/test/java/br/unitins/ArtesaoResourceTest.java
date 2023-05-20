package br.unitins;

import br.unitins.projeto.dto.artesao.ArtesaoDTO;
import br.unitins.projeto.dto.artesao.ArtesaoResponseDTO;
import br.unitins.projeto.service.artesao.ArtesaoService;
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
public class ArtesaoResourceTest {

    @Inject
    ArtesaoService service;

    @Test
    public void getAllTest() {
        given().when().get("/artesoes").then().statusCode(200);
    }

    @Test
    public void testInsert() {
        ArtesaoDTO dto = new ArtesaoDTO(
                "Carliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"
        );

        given().contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/artesoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Carliey"),
                        "descricao", is("Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"));
    }

    @Test
    public void testUpdate() {
        ArtesaoDTO dto = new ArtesaoDTO(
                "Amedeo Capeli",
                "Instagram: @stoccafisso_design"
        );

        Long id = service.create(dto).id();

        ArtesaoDTO dtoUpdate = new ArtesaoDTO(
                "Amedeo Capelli",
                "Instagram: @stoccafisso_design"
        );

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/artesoes/" + id).then().statusCode(200);

        ArtesaoResponseDTO responseDTO = service.findById(id);
        assertThat(responseDTO.nome(), is("Amedeo Capelli"));
        assertThat(responseDTO.descricao(), is("Instagram: @stoccafisso_design"));
    }

    @Test
    public void testDelete() {
        ArtesaoDTO dto = new ArtesaoDTO(
                "Carliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"
        );

        Long id = service.create(dto).id();

        given().when().delete("/artesoes/" + id).then().statusCode(204);

        ArtesaoResponseDTO responseDTO = null;

        try {
            responseDTO = service.findById(id);
        } catch (Exception e) {
        } finally {
            assertNull(responseDTO);
        }
    }

    @Test
    public void testCount() {
        ArtesaoDTO dto = new ArtesaoDTO(
                "Carliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"
        );

        service.create(dto);

        dto = new ArtesaoDTO(
                "Amedeo Capelli",
                "Instagram: @stoccafisso_design"
        );

        service.create(dto);

        given().contentType(ContentType.JSON).when().get("/artesoes/count").then().equals(2);
    }

    @Test
    public void testSearch() {
        ArtesaoDTO dto = new ArtesaoDTO(
                "Karliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @karlieyed"
        );

        service.create(dto);

        String nome = "Kar";

        given().contentType(ContentType.JSON)
                .when()
                .get("/artesoes/search/" + nome)
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].nome", is("Karliey"),
                        "[0].descricao", is("Tatuadora, Ilustradora e Artesã. Instagram: @karlieyed"));
    }

}
