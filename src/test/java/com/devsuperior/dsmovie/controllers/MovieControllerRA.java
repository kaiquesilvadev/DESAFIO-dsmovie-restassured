package com.devsuperior.dsmovie.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.RestAssured;

public class MovieControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	
	private Long idExistente , idInexistente ;
	private String titulo;

	@BeforeEach
	void setUp() throws Exception {
		
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
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
