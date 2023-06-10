package br.unitins;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.lista_desejo.ListaDesejoDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UsuarioLogadoResourceTest {

    private String token = "";
    private Header header = null;

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
    public void getPerfilUsuario() {
        given().header(header).when().get("/usuario-logado").then().statusCode(200);
    }

    @Test
    public void getDadosPessoais() {
        given().header(header).when().get("/usuario-logado/dados-pessoais").then().statusCode(200);
    }

    @Test
    public void setDadosPessoais() {
        DadosPessoaisDTO dto = new DadosPessoaisDTO(LocalDate.of(2003, 02, 10));

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .patch("/usuario-logado/dados-pessoais")
                .then()
                .statusCode(200)
                .body(
                        "dataNascimento", is("10/02/2003"));
    }

    @Test
    public void alterarSenha() {
        SenhaDTO dto = new SenhaDTO("123", "123");

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .patch("/usuario-logado/alterar-senha")
                .then()
                .statusCode(200);
    }

    @Test
    public void getEnderecos() {
        given().header(header).when().get("/usuario-logado/enderecos").then().statusCode(200);
    }

    @Test
    public void getCartoes() {
        given().header(header).when().get("/usuario-logado/cartoes").then().statusCode(200);
    }

    @Test
    public void insertListaDesejo() {
        ListaDesejoDTO dto = new ListaDesejoDTO(1L);

        given().contentType(ContentType.JSON)
                .header(header)
                .body(dto)
                .when()
                .patch("/usuario-logado/lista-desejo")
                .then()
                .statusCode(200);
    }

    @Test
    public void getListaDesejo() {
        given().header(header).when().get("/usuario-logado/lista-desejo").then().statusCode(200);
    }

    @Test
    public void getMinhasCompras() {
        given().header(header).when().get("/usuario-logado/minhas-compras").then().statusCode(200);
    }


}
