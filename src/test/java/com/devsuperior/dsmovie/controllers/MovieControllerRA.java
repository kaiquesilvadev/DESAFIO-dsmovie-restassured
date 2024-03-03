package com.devsuperior.dsmovie.controllers;

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

	@BeforeEach
	void setUp() throws Exception {
		
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
		
	
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
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
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
