# Guia Postman - Produto API

## Introdução

Esta coleção Postman contém todos os endpoints da **Produto API**, incluindo documentação Swagger, CRUD completo e buscas avançadas.

## Como Usar

### 1. Importar no Postman

- Abra o Postman
- Clique em **Import** → **File** → selecione `produto-api.postman_collection.json`
- Clique em **Import** novamente → **File** → selecione `produto-api.local.postman_environment.json`

### 2. Selecionar Ambiente

No canto superior direito do Postman, escolha **Produto API Local** no dropdown de ambientes.

### 3. Configurar Variáveis

As variáveis globais são:
- `baseUrl`: `http://localhost:8080` (padrão)
- `produtoId`: capturado automaticamente após criar um produto
- `nomeBusca`: nome do produto para busca (padrão: "Mouse Gamer")

Você pode alterá-las em **Environments** → **Produto API Local** → botão **Edit**.

---

## Endpoints Disponíveis

### Documentação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/docs` | Swagger UI para visualizar documentação |
| GET | `/docs/api-docs` | Documentação em formato OpenAPI JSON |

### CRUD - Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/produtos` | Criar novo produto |
| GET | `/api/produtos` | Listar todos os produtos |
| GET | `/api/produtos/{id}` | Buscar produto por ID |
| PUT | `/api/produtos/{id}` | Atualizar produto |
| DELETE | `/api/produtos/{id}` | Deletar produto |

### Consultas Especiais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/produtos/contar` | Contar total de produtos |
| GET | `/api/produtos/buscar/{nome}` | Buscar produtos por nome |

---

## Fluxo de Execução Recomendado

### Executar Manualmente (Passo a Passo)

1. **Docs - Swagger UI**: Valida que a documentação está acessível
2. **Docs - OpenAPI JSON**: Valida o schema OpenAPI
3. **POST - Criar produto**: Cria um novo produto e captura o `id`
4. **GET - Listar todos**: Valida se o produto foi inserido
5. **GET - Contar produtos**: Verifica o total
6. **GET - Buscar por ID**: Recupera o produto criado
7. **GET - Buscar por nome**: Busca por padrão no nome
8. **PUT - Atualizar produto**: Modifica o produto
9. **DELETE - Remover produto**: Deleta o produto
10. **GET - Buscar por ID após delete**: Valida que retorna 404

### Executar Automático (Runner)

No Postman:
- Clique em **Collection** → **Produto API - Endpoints**
- Clique em **Run** (ou o ícone de play ao lado)
- Escolha o ambiente **Produto API Local**
- Clique em **Run Produto API - Endpoints**

Os testes automaticamente validarão status codes e capturam variáveis.

---

## Exemplos de Payloads

### POST - Criar Produto

```json
{
  "nome": "Mouse Gamer",
  "descricao": "Mouse com RGB e sensor premium",
  "preco": 129.90,
  "quantidade": 25
}
```

### PUT - Atualizar Produto

```json
{
  "nome": "Mouse Gamer Pro",
  "descricao": "Mouse gamer com RGB e sensor premium",
  "preco": 159.90,
  "quantidade": 18
}
```

---

## Testes Automatizados

Cada endpoint tem scripts de teste que validam:

- **Status Code**: Verifica se a resposta tem o código HTTP esperado
- **Estrutura de Dados**: Valida se a resposta contém campos obrigatórios
- **Captura de Variáveis**: Salva o `id` do produto para uso em requisições subsequentes

Exemplo de teste incluído:
```javascript
pm.test('Status 201', function () {
    pm.response.to.have.status(201);
});
```

---

## Troubleshooting

### Erro: "Connection refused"
- Verifique se a aplicação está rodando em `http://localhost:8080`
- Verifique a variável `baseUrl` no ambiente

### Erro: "404 Not Found"
- O produto foi deletado ou não existe
- Verifique se o `produtoId` foi capturado corretamente

### Erro: "JSON parse error"
- Verifique se o JSON do payload está válido
- Confirme se o `Content-Type` é `application/json`

### Erro: "Port 8080 already in use"
```powershell
taskkill /F /IM java.exe
```

---

## URLs Rápidas

- **Swagger UI**: http://localhost:8080/docs
- **OpenAPI JSON**: http://localhost:8080/docs/api-docs
- **H2 Console**: http://localhost:8080/h2-console

---

## Dicas

1. **Reutilizar produtoId**: Após criar um produto, o `id` é automaticamente salvo na variável `produtoId`. Use `{{produtoId}}` em qualquer URL.

2. **Testar múltiplos produtos**: Cada vez que roda a coleção completa, um novo produto é criado. O banco H2 é em memória e limpa ao reiniciar.

3. **Editar variáveis em tempo de execução**: Clique com botão direito em uma variável para editar valores rapidamente.

4. **Visualizar histórico**: Toda requisição fica no histórico do Postman para referência futura.

---

