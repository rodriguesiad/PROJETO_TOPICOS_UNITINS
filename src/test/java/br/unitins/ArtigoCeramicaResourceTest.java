package br.unitins;

import br.unitins.projeto.dto.artesao.ArtesaoDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.service.artesao.ArtesaoService;
import br.unitins.projeto.service.artigo_ceramica.ArtigoCeramicaService;
import br.unitins.projeto.service.tipo_produto.TipoProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class ArtigoCeramicaResourceTest {

    private String token = "";
    private Header header = null;

    @Inject
    ArtigoCeramicaService service;

    @Inject
    ArtesaoService artesaoService;

    @Inject
    TipoProdutoService tipoProdutoService;

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
        given().when().get("/artigos-ceramica").then().statusCode(200);
    }

    @Test
    public void testInsert() {
        ArtesaoDTO artesaoDTO = new ArtesaoDTO(
                "Carliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"
        );

        Long id_artesao = artesaoService.create(artesaoDTO).id();
        List<Long> idsTipoProdutos = new ArrayList<>();

        TipoProdutoDTO tipoProdutoDTO = new TipoProdutoDTO("Cerâmica");

        idsTipoProdutos.add(tipoProdutoService.create(tipoProdutoDTO).id());

        tipoProdutoDTO = new TipoProdutoDTO("Feito a mão");

        idsTipoProdutos.add(tipoProdutoService.create(tipoProdutoDTO).id());

        ArtigoCeramicaDTO dto = new ArtigoCeramicaDTO(
                "Mar azul",
                "Jogo de copo e prato feito de barro e revestido de azul.",
                45.50,
                2,
                6,
                id_artesao,
                idsTipoProdutos
        );

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .post("/artigos-ceramica")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Mar azul"),
                        "descricao", is("Jogo de copo e prato feito de barro e revestido de azul."),
                        "preco", is(45.50F),
                        "estoque", is(2),
                        "quantidadePecas", is(6),
                        "artesao.nome", is("Carliey"),
                        "tipoProduto[0].descricao", is("Cerâmica"));
    }

    @Test
    public void testUpdate() {
        ArtesaoDTO artesaoDTO = new ArtesaoDTO(
                "Carliey",
                "Tatuadora, Ilustradora e Artesã. Instagram: @carlieyed"
        );

        Long id_artesao = artesaoService.create(artesaoDTO).id();
        List<Long> idsTipoProdutos = new ArrayList<>();

        TipoProdutoDTO tipoProdutoDTO = new TipoProdutoDTO(
                "Cerâmica"
        );

        idsTipoProdutos.add(tipoProdutoService.create(tipoProdutoDTO).id());

        ArtigoCeramicaDTO dto = new ArtigoCeramicaDTO(
                "Mar azul",
                "Jogo de copo e prato feito de barro e revestido de azul.",
                45.50,
                2,
                6,
                id_artesao,
                idsTipoProdutos
        );

        Long id = service.create(dto).id();

        tipoProdutoDTO = new TipoProdutoDTO(
                "Feito a mão"
        );

        idsTipoProdutos.add(tipoProdutoService.create(tipoProdutoDTO).id());

        ArtigoCeramicaDTO dtoUpdate = new ArtigoCeramicaDTO(
                "Mar azul",
                "Jogo de copo e prato feito de barro e revestido de azul.",
                45.50,
                2,
                6,
                id_artesao,
                idsTipoProdutos
        );

        given().header(header).contentType(ContentType.JSON).body(dtoUpdate).when().put("/artigos-ceramica/" + id).then().statusCode(200);

        ArtigoCeramicaResponseDTO responseDTO = service.findById(id);
        assertThat(responseDTO.tipoProduto().get(1).descricao(), is("Feito a mão"));
    }

}
