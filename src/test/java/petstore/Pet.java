package petstore;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - create - post
    @Test(priority = 1) //Identifica o m�todo e fun��o como um teste
    public void IncluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin - Dispon�vel pelo Rest-Assured
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Lucky"))
                .body("status", is ("available"))
                .body("category.name", is("dog"))

                .body("tags.name", contains("sta"))
        ;
    }

    @Test(priority = 2)
    public void ConsultarPet(){
        String petId = "1974080123";

        String statusAnimal =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Lucky"))
                .body("category.name", is ("dog"))
                //.body("status", is ("available"))
        .extract()
                .path("status")
        ;
        System.out.println("O status do animal � " + statusAnimal);
    }
}
