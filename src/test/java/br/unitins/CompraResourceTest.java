package br.unitins;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.unregisterParser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class CompraResourceTest {

    private String token = "";
    private Header header = null;

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
    public void getHistoricoEntrega() {
        given().header(header).when().get("/compras/" + 1 +"/historico-entrega" ).then().statusCode(200);
    }

    @Test
    public void getPagamento() {
        given().header(header).when().get("/compras/" + 1 +"/pagamento" ).then().statusCode(200);
    }

}
