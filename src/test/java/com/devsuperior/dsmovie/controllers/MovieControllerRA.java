package com.devsuperior.dsmovie.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.RestAssured;

public class MovieControllerRA {
	
	private Map<String, Object> postMovieInstance;
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	
	private Long idExistente , idInexistente ;
	private String titulo;

	@BeforeEach
	void setUp() throws Exception {
		
		postMovieInstance = new HashMap<>();
		postMovieInstance.put("title", "eu sou a lenda");
		postMovieInstance.put("score", 2.3);
		postMovieInstance.put("count", 3);
		postMovieInstance.put("imgUrl", "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");
		
		
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
		
		titulo = "Witcher"; 
		idExistente = 2l;
		idInexistente = 1000l;
		
		RestAssured.baseURI = "http://localhost:8080";
	}
	
	@DisplayName("findAll deve retornar OK quando o filme não apresentar argumentos")
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		
		RestAssured.given()
			.when()
				.get("/movies")
			.then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@DisplayName("findAll deve retornar filmes paginados quando o parâmetro do título do filme não estiver vazio")
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
		
		RestAssured.given()
		.when()
			.get("/movies?title={titulo}" , titulo)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content.id[0]", is(1))
			.body("content.title", hasItems("The Witcher"));
	}
	
	@DisplayName("findById deve retornar o filme quando o Id existir")
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {
		
		RestAssured.given()
		.when()
			.get("/movies/{id}" , idExistente)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(2))
			.body("title", equalTo("Venom: Tempo de Carnificina"));
	}
	
	@DisplayName("findById deve retornar não encontrado quando Id não existe")
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
		RestAssured.given()
		.when()
			.get("/movies/{id}" , idInexistente)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@DisplayName("insert deve retornar entidade não processável quando o admin estiver logado e título em branco")
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
		postMovieInstance.put("title", "");
		JSONObject newProduct = new JSONObject(postMovieInstance);
		
		RestAssured.given()
		.header("Content-type", "application/json")
		.header("Authorization", "Bearer " + adminToken)
		.body(newProduct)
		.when()
			.post("/movies")
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
	}
	
	@DisplayName("insert deve retornar Forbidden quando o cliente está logado")
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		JSONObject newProduct = new JSONObject(postMovieInstance);
		
		RestAssured.given()
		.header("Content-type", "application/json")
		.header("Authorization", "Bearer " + clientToken)
		.body(newProduct)
		.when()
			.post("/movies")
		.then()
			.statusCode(HttpStatus.FORBIDDEN.value());
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
