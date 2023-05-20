package br.unitins;

/*
 * import br.unitins.projeto.dto.cartao.CartaoDTO;
 * import br.unitins.projeto.dto.endereco.EnderecoDTO;
 * import br.unitins.projeto.dto.telefone.TelefoneDTO;
 * import br.unitins.projeto.dto.usuario.UsuarioDTO;
 * import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
 * import br.unitins.projeto.service.artigo_ceramica.ArtigoCeramicaService;
 * import br.unitins.projeto.service.usuario.UsuarioService;
 * import io.quarkus.test.junit.QuarkusTest;
 * import io.restassured.RestAssured;
 * import io.restassured.config.EncoderConfig;
 * import io.restassured.http.ContentType;
 * import org.junit.jupiter.api.Test;
 * 
 * import jakarta.inject.Inject;
 * import java.util.ArrayList;
 * import java.util.Arrays;
 * import java.util.List;
 * 
 * import static io.restassured.RestAssured.given;
 * import static org.hamcrest.CoreMatchers.is;
 * import static org.hamcrest.CoreMatchers.notNullValue;
 * import static org.hamcrest.MatcherAssert.assertThat;
 * import static org.junit.jupiter.api.Assertions.assertNull;
 * 
 * @QuarkusTest
 * public class UsuarioResourceTest {
 * 
 * @Inject
 * UsuarioService service;
 * 
 * @Inject
 * ArtigoCeramicaService ArtigoCeramicaService;
 * 
 * @Test
 * public void getAllTest() {
 * given().when().get("/usuarios").then().statusCode(200);
 * }
 * 
 * @Test
 * public void testInsert() {
 * TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
 * EnderecoDTO enderecoDTO = new EnderecoDTO(
 * Boolean.TRUE,
 * "Rua 8",
 * "Casas",
 * "18",
 * null,
 * "12345678",
 * "Casa",
 * 1L
 * );
 * CartaoDTO cartaoDTO = new CartaoDTO(
 * "1234",
 * "João Antonio",
 * "123",
 * "09/30"
 * );
 * 
 * UsuarioDTO dto = new UsuarioDTO(
 * "Maria Cristina de Souza",
 * "maria@gmail.com",
 * "71594987041",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(List.of(enderecoDTO)),
 * new ArrayList<>(List.of(cartaoDTO)),
 * null
 * );
 * 
 * given()
 * .header("Content-Type", "application/json")
 * .contentType(ContentType.JSON)
 * .accept(ContentType.JSON)
 * .body(dto)
 * .when()
 * .post("/usuarios")
 * .then()
 * .statusCode(201)
 * .body("id", notNullValue(),
 * "nome", is("Maria Cristina de Souza"),
 * "email", is("maria@gmail.com"),
 * "cpf", is("71594987041"),
 * "senha", is("12345678"),
 * "telefone.numero", is("999999999"),
 * "whatsapp.numero", is("999999999"),
 * "enderecos[0].bairro", is("Casas"),
 * "cartoes[0].numeroCartao", is("1234"));
 * }
 * 
 * @Test
 * public void testUpdate() {
 * TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
 * EnderecoDTO enderecoDTO = new EnderecoDTO(
 * Boolean.TRUE,
 * "Rua 8",
 * "Casas",
 * "18",
 * null,
 * "12345678",
 * "Casa",
 * 1L
 * );
 * CartaoDTO cartaoDTO = new CartaoDTO(
 * "1234",
 * "João Antonio",
 * "123",
 * "09/30"
 * );
 * 
 * UsuarioDTO dto = new UsuarioDTO(
 * "Maria Cristina de Souza",
 * "maria@gmail.com",
 * "71594987041",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(List.of(enderecoDTO)),
 * new ArrayList<>(List.of(cartaoDTO)),
 * null
 * );
 * 
 * Long id = service.create(dto).id();
 * 
 * telefoneDTO = new TelefoneDTO("63", "888888888");
 * 
 * UsuarioDTO dtoUpdate = new UsuarioDTO(
 * "Maria Cristina de Souza",
 * "maria@gmail.com",
 * "12345678901",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(Arrays.asList(enderecoDTO)),
 * new ArrayList<>(Arrays.asList(cartaoDTO)),
 * null
 * );
 * 
 * given().log().all()
 * .config(RestAssured.config()
 * .encoderConfig(EncoderConfig.encoderConfig()
 * .appendDefaultContentCharsetToContentTypeIfUndefined(false))
 * )
 * .header("Content-Type", "application/json")
 * .contentType(ContentType.JSON)
 * .accept(ContentType.JSON)
 * .body(dtoUpdate)
 * .when()
 * .put("/usuarios/" + id)
 * .then()
 * .statusCode(200);
 * 
 * UsuarioResponseDTO responseDTO = service.findById(id);
 * assertThat(responseDTO.telefone().numero(), is("888888888"));
 * }
 * 
 * @Test
 * public void testDelete() {
 * TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
 * EnderecoDTO enderecoDTO = new EnderecoDTO(
 * Boolean.TRUE,
 * "Rua 8",
 * "Casas",
 * "18",
 * null,
 * "12345678",
 * "Casa",
 * 1L
 * );
 * CartaoDTO cartaoDTO = new CartaoDTO(
 * "1234",
 * "João Antonio",
 * "123",
 * "09/30"
 * );
 * 
 * UsuarioDTO dto = new UsuarioDTO(
 * "Maria Cristina de Souza",
 * "maria@gmail.com",
 * "71594987041",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(List.of(enderecoDTO)),
 * new ArrayList<>(List.of(cartaoDTO)),
 * null
 * );
 * 
 * Long id = service.create(dto).id();
 * 
 * given().when().delete("/usuarios/" + id).then().statusCode(204);
 * 
 * UsuarioResponseDTO responseDTO = null;
 * 
 * try {
 * responseDTO = service.findById(id);
 * } catch (Exception e) {
 * } finally {
 * assertNull(responseDTO);
 * }
 * }
 * 
 * @Test
 * public void testCount() {
 * TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
 * EnderecoDTO enderecoDTO = new EnderecoDTO(
 * Boolean.TRUE,
 * "Rua 8",
 * "Casas",
 * "18",
 * null,
 * "12345678",
 * "Casa",
 * 1L
 * );
 * CartaoDTO cartaoDTO = new CartaoDTO(
 * "1234",
 * "João Antonio",
 * "123",
 * "09/30"
 * );
 * 
 * UsuarioDTO dto = new UsuarioDTO(
 * "Maria Cristina de Souza",
 * "maria@gmail.com",
 * "71594987041",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(List.of(enderecoDTO)),
 * new ArrayList<>(List.of(cartaoDTO)),
 * null
 * );
 * 
 * service.create(dto);
 * 
 * given().contentType(ContentType.JSON).when().get("/artigos-ceramica/count").
 * then().equals(1);
 * }
 * 
 * @Test
 * public void testSearch() {
 * TelefoneDTO telefoneDTO = new TelefoneDTO("63", "999999999");
 * EnderecoDTO enderecoDTO = new EnderecoDTO(
 * Boolean.TRUE,
 * "Rua 8",
 * "Casas",
 * "18",
 * null,
 * "12345678",
 * "Casa",
 * 1L
 * );
 * CartaoDTO cartaoDTO = new CartaoDTO(
 * "1234",
 * "Carla Beatriz",
 * "123",
 * "09/30"
 * );
 * 
 * UsuarioDTO dto = new UsuarioDTO(
 * "Carla Beatriz",
 * "carla@gmail.com",
 * "71594987041",
 * "12345678",
 * telefoneDTO,
 * telefoneDTO,
 * new ArrayList<>(List.of(enderecoDTO)),
 * new ArrayList<>(List.of(cartaoDTO)),
 * null
 * );
 * 
 * service.create(dto);
 * 
 * String nome = "Carla";
 * 
 * given().contentType(ContentType.JSON)
 * .when()
 * .get("/usuarios/search/" + nome)
 * .then()
 * .statusCode(200)
 * .body("$.size()", is(1),
 * "[0].nome", is("Carla Beatriz"));
 * }
 * 
 * }
 */
