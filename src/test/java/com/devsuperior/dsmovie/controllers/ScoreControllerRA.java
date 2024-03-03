package com.devsuperior.dsmovie.controllers;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.RestAssured;

public class ScoreControllerRA {
	
private Map<String, Object> postScoreInstance;
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	
	private Long idExistente , idInexistente ;
	private String titulo;

	@BeforeEach
	void setUp() throws Exception {
		
		postScoreInstance = new HashMap<>();
		postScoreInstance.put("movieId", 1);
		postScoreInstance.put("score", 0.0);

		
		
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
	
	@DisplayName(" save Score deve retornar NotFound quando MovieId não existe")
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		postScoreInstance.put("movieId", 1111);
		JSONObject newProduct = new JSONObject(postScoreInstance);
		
		RestAssured.given()
		.header("Content-type", "application/json")
		.header("Authorization", "Bearer " + adminToken)
		.body(newProduct)
		.when()
			.put("/scores")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@DisplayName("save Score deve retornar UnprocessableEntity quando faltar MovieId")
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		postScoreInstance.put("movieId", null);
		JSONObject newProduct = new JSONObject(postScoreInstance);
		
		RestAssured.given()
		.header("Content-type", "application/json")
		.header("Authorization", "Bearer " + adminToken)
		.body(newProduct)
		.when()
			.put("/scores")
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
	}
	
	@DisplayName("save Score deve retornar entidade não processável quando a pontuação for menor que zero")
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {		
		postScoreInstance.put("score", -0.0);
		JSONObject newProduct = new JSONObject(postScoreInstance);
		
		RestAssured.given()
		.header("Content-type", "application/json")
		.header("Authorization", "Bearer " + adminToken)
		.body(newProduct)
		.when()
			.put("/scores")
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
	}
}
