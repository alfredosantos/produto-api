# Produto API

Uma API bem simples pra gerenciar produtos. Feita com **Spring Boot** e **Kotlin**.

## 🚀 Tecnologias

- **Kotlin** 2.2.21
- **Spring Boot** 4.0.5
- **Spring Data JPA**
- **H2 Database** (em memória)
- **Gradle** 9.4.1
- **Java** 21

## 📋 Pré-requisitos

- JDK 21+
- Gradle 9.4.1+ (ou use o `gradlew` incluído)

## 📖 Documentação da API (Swagger)

Após subir a aplicação, acesse:

```
http://localhost:8080/docs
```

O JSON da spec OpenAPI fica em:

```
http://localhost:8080/docs/api-docs
```

## 🔧 Como executar

### 1. Compilar o projeto
```bash
./gradlew build -x test
```

### 2. Executar a aplicação
```bash
./gradlew bootRun
```

A API estará disponível em: `http://localhost:8080`

### 3. Acessar o H2 Console
```
http://localhost:8080/h2-console
```

**Credenciais:**
- URL JDBC: `jdbc:h2:mem:produtosdb`
- User: `sa`
- Password: (deixar em branco)

## 📚 Endpoints da API

### Listar todos os produtos
```http
GET /api/produtos
```

### Buscar produto por ID
```http
GET /api/produtos/{id}
```

### Buscar produtos por nome
```http
GET /api/produtos/buscar/{nome}
```

### Criar novo produto
```http
POST /api/produtos
Content-Type: application/json

{
  "nome": "Notebook",
  "descricao": "Notebook de alta performance",
  "preco": 3500.00,
  "quantidade": 10
}
```

### Atualizar produto
```http
PUT /api/produtos/{id}
Content-Type: application/json

{
  "nome": "Notebook Atualizado",
  "descricao": "Descrição atualizada",
  "preco": 3200.00,
  "quantidade": 8
}
```

### Deletar produto
```http
DELETE /api/produtos/{id}
```

## 📁 Estrutura do Projeto

```
src/main/kotlin/com/br/produto_api/
├── controller/          # Controllers REST
├── service/            # Lógica de negócio
├── repository/         # Acesso aos dados
├── model/              # Entidades JPA
├── dto/                # Data Transfer Objects
├── exception/          # Tratamento de exceções
└── ProdutoApiApplication.kt  # Classe principal
```

## 🧪 Testes

```bash
./gradlew test
```

## 📝 Modelo de Dados

Um produto tem essas informações:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Identificador único |
| nome | String | Nome do produto |
| descricao | String | Descrição |
| preco | BigDecimal | Preço |
| quantidade | Int | Quantos tem em estoque |
| dataCriacao | LocalDateTime | Quando foi criado |
| dataAtualizacao | LocalDateTime | Última vez que foi editado |

## 🎯 Recursos

- ✅ Criar produtos
- ✅ Listar todos
- ✅ Buscar por ID ou nome
- ✅ Editar
- ✅ Deletar
- ✅ Contar quantos tem

## 🎓 Desafio do Bootcamp

Este projeto foi desenvolvido como **Desafio Final do Bootcamp de Arquiteto(a) de Software** com os seguintes objetivos:

### ✅ Requisitos Atendidos

1. **Arquitetura MVC** - Padrão de design bem definido
   - Model: `Produto.kt`
   - View/Controller: `ProdutoController.kt`
   - Business Logic: `ProdutoService.kt`
   - Data Access: `ProdutoRepository.kt`

2. **Operações CRUD Completas**
   - ✅ CREATE - `POST /api/produtos`
   - ✅ READ - `GET /api/produtos/{id}`
   - ✅ UPDATE - `PUT /api/produtos/{id}`
   - ✅ DELETE - `DELETE /api/produtos/{id}`

3. **Funcionalidades Extras**
   - ✅ Find All - `GET /api/produtos`
   - ✅ Find By ID - `GET /api/produtos/{id}`
   - ✅ Find By Name - `GET /api/produtos/buscar/{nome}`
   - ✅ Count - `GET /api/produtos/contar`

4. **Persistência de Dados**
   - ✅ Banco de dados H2 (em memória)
   - ✅ JPA/Hibernate para ORM
   - ✅ Transactions gerenciadas pelo Spring

5. **Documentação**
   - ✅ README.md - Guia de uso
    - ✅ docs/produto-api-base.drawio.pdf - Diagrama arquitetural exportado
    - ✅ docs/diagrams/produto-api-base.drawio - Fonte dos diagramas no Draw.io
    - ✅ docs/Estrutura de pastas do projeto MVC.docx - Entregável de estrutura MVC
    - ✅ docs/Explicação da Estrutura e dos Elementos que Comporão o Código.docx - Entregável de explicação do código
    - ✅ postman/README.md - Guia da coleção Postman
   - ✅ requests.http - Exemplos de requisições

### 📚 Documentação Arquitetural

#### Veja também:
- **[`docs/produto-api-base.drawio.pdf`](./docs/produto-api-base.drawio.pdf)** - Diagrama arquitetural em PDF
- **[`docs/diagrams/produto-api-base.drawio`](./docs/diagrams/produto-api-base.drawio)** - Arquivo editável no Draw.io
- **[`docs/Estrutura de pastas do projeto MVC.docx`](./docs/Estrutura%20de%20pastas%20do%20projeto%20MVC.docx)** - Entregável 2
- **[`docs/Explicação da Estrutura e dos Elementos que Comporão o Código.docx`](./docs/Explicação%20da%20Estrutura%20e%20dos%20Elementos%20que%20Comporão%20o%20Código.docx)** - Entregável 3
- **[`postman/README.md`](./postman/README.md)** - Guia de testes no Postman

#### Estrutura de Responsabilidades

| Componente | Arquivo | Responsabilidade |
|------------|---------|------------------|
| **Controller** | `controller/ProdutoController.kt` | Mapear requisições HTTP e retornar responses |
| **Service** | `service/ProdutoService.kt` | Lógica de negócio e orquestração de dados |
| **Repository** | `repository/ProdutoRepository.kt` | Acesso aos dados e persistência |
| **Model** | `model/Produto.kt` | Representação da entidade de domínio |
| **DTO** | `dto/ProdutoDTO.kt` | Transferência de dados entre camadas |
| **Exception Handler** | `exception/GlobalExceptionHandler.kt` | Tratamento centralizado de erros |

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença MIT.

