
# Projeto Full Stack com Spring Boot e Angular

Este é um projeto full stack que combina um backend desenvolvido em Spring Boot com um frontend em Angular. O objetivo do projeto é fornecer uma aplicação web completa que inclui um sistema de gerenciamento de produtos e categorias com autenticação baseada em roles.

## Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.3.2**
  - Spring Data JPA
  - Spring Security
  - Spring Web
  - Lombok
  - Swagger
- **MySQL** (dependendo do ambiente)
- **Maven**

### Frontend
- **Angular 17**
- **TypeScript**
- **Boostrap**

### Banco de Dados
- **MySQL** (utilizando Docker)

## Configuração e Execução

### 1. Configurar o Banco de Dados com Docker

Primeiro, certifique-se de que você tem o Docker instalado. Execute o seguinte comando na raiz do projeto para iniciar o banco de dados MySQL:

```bash
docker-compose up -d
```

Isso iniciará um contêiner MySQL com as configurações especificadas no \`docker-compose.yml\`.

### 2. Executar o Backend

#### Pré-requisitos
- Java 17 instalado
- Maven instalado

#### Passos para executar
1. Navegue até o diretório do backend:
   ```bash
   cd backend
   ```
2. Compile o projeto e baixe as dependências:
   ```bash
   mvn clean install
   ```
3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

#### Acessar o Swagger

A documentação da API está disponível através do Swagger. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

### 3. Executar o Frontend

#### Pré-requisitos
- Node.js e npm instalados

#### Passos para executar
1. Navegue até o diretório do frontend:
   ```bash
   cd front
   ```
2. Instale as dependências do projeto:
   ```bash
   npm install
   ```
3. Execute a aplicação Angular:
   ```bash
   ng serve
   ```

4. Acesse a aplicação no navegador:

```
http://localhost:4200
```

### 4. Executar os Testes Unitários do Backend

Para executar os testes unitários do backend, siga os passos abaixo:

1. Navegue até o diretório do backend (se ainda não estiver lá):
   ```bash
   cd backend
   ```
2. Execute os testes unitários:
   ```bash
   mvn test
   ```

### Estrutura do Projeto

- **backend/**: Contém todo o código-fonte e recursos do backend desenvolvido em Spring Boot.
- **front/**: Contém todo o código-fonte e recursos do frontend desenvolvido em Angular.
- **docker-compose.yml**: Arquivo de configuração Docker para iniciar o banco de dados MySQL.

