# Desafio DSMovie RestAssured

Este é um desafio proposto pela devSuperior para testar minhas habilidades em testes de API usando o RestAssured. O objetivo é implementar uma série de testes de API para o projeto DSMovie, que trata de filmes e avaliações de filmes.

## Sobre o Projeto DSMovie

O DSMovie é um sistema de filmes e avaliações de filmes. Os dados dos filmes podem ser visualizados publicamente, mas as operações de alteração de filmes (inserir, atualizar, deletar) estão disponíveis apenas para usuários ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado, seja CLIENT ou ADMIN. A entidade Score armazena uma nota de 0 a 5 que cada usuário dá a cada filme. Sempre que uma nota é registrada, o sistema calcula a média das notas e armazena essa média juntamente com a contagem de votos na entidade Movie.

## Testes de API a serem implementados

Aqui estão os testes de API que devem ser implementados utilizando o RestAssured:

### MovieControllerRA:

1. `findAllShouldReturnOkWhenMovieNoArgumentsGiven`
2. `findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty`
3. `findByIdShouldReturnMovieWhenIdExists`
4. `findByIdShouldReturnNotFoundWhenIdDoesNotExist`
5. `insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle`
6. `insertShouldReturnForbiddenWhenClientLogged`
7. `insertShouldReturnUnauthorizedWhenInvalidToken`

### ScoreControllerRA:

8. `saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist`
9. `saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId`
10. `saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero`

O mínimo para aprovação no desafio são 8 dos 10 testes implementados.

## Como Executar os Testes

Para executar os testes, siga os passos abaixo:

1. Clone este repositório.
2. Certifique-se de ter todas as dependências necessárias instaladas.
3. Execute os testes utilizando o framework RestAssured.
4. Verifique os resultados dos testes.

## Tecnologias Utilizadas

- Java
- RestAssured
- Spring boot
- Banco h2
