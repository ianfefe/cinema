# Guia de Testes - Swagger UI

## Acesso

Com a aplicação rodando (`./mvnw spring-boot:run`), acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Autenticação JWT

> **Nota:** A autenticação JWT está ativa. Endpoints protegidos retornarão `403` sem um token válido.

### 1. Obter token

**POST** `/api/v1/usuarios/auth`

```json
{
  "login": "seu_login",
  "senha": "sua_senha"
}
```

**Resposta (`200 OK`):**
```json
{
  "login": "seu_login",
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

### 2. Usar o token no Swagger UI

1. Clique no botão **Authorize** (cadeado) no topo direito da página.
2. No campo **Value**, insira:
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9...
   ```
   (inclua a palavra `Bearer` seguida de espaço antes do token)
3. Clique em **Authorize** e depois em **Close**.
4. A partir desse momento, todas as requisições enviadas pelo Swagger incluirão o header `Authorization: Bearer <token>` automaticamente.

### 3. Controle de acesso por endpoint

| Endpoint | Público | USER/ADMIN | Apenas ADMIN |
|---|:---:|:---:|:---:|
| `GET /api/v1/cinemas` | ✓ | | |
| `GET /api/v1/sessoes-cinema` | ✓ | | |
| `GET /api/v1/generos-filme` | ✓ | | |
| `POST /api/v1/clientes` | ✓ | | |
| `POST /api/v1/usuarios/auth` | ✓ | | |
| `GET/POST /api/v1/enderecos` | | ✓ | |
| `POST /api/v1/tipo-ingressos` | | ✓ | |
| `/api/v1/assentos` | | | ✓ |
| `/api/v1/tipo-assentos` | | | ✓ |
| `/api/v1/tipo-audios` | | | ✓ |
| `/api/v1/tipo-imagens` | | | ✓ |
| `/api/v1/tipo-salas` | | | ✓ |
| `/api/v1/salas` | | | ✓ |
| `POST/PUT/DELETE /api/v1/cinemas` | | | ✓ |
| `POST/PUT/DELETE /api/v1/sessoes-cinema` | | | ✓ |
| `POST/PUT/DELETE /api/v1/generos-filme` | | | ✓ |
| `/api/v1/usuarios` | | | ✓ |
| `GET /api/v1/clientes` | | | ✓ |

### 4. Erros de autenticação

| Situação | Código |
|---|---|
| Token ausente em endpoint protegido | `403 Forbidden` |
| Token inválido ou expirado | `401 Unauthorized` |
| Role insuficiente | `403 Forbidden` |

---

## Ordem recomendada de cadastro

Algumas entidades dependem de outras. Siga esta ordem:

```
1. Endereço
2. Cinema (depende de Endereço)
3. TipoSala / TipoAssento / TipoAudio / TipoImagem / TipoIngresso
4. Sala (depende de Cinema + TipoSala)
5. Assento (depende de Sala + TipoAssento)
6. Artista / Diretor / Genero
7. Filme
8. FilmesArtista / FilmesDiretor / GenerosFilme / FilmesCinema
9. Sessão (depende de Filme + Sala + TipoAudio + TipoImagem)
10. SessoesCinema (depende de Sessão + Cinema)
11. Funcionário (depende de Cinema)
12. Cliente (depende de Endereço)
13. Compra (depende de Cinema + Cliente + Funcionário)
14. Ingresso (depende de Compra + Assento + Sessão + TipoIngresso)
```

---

## Endpoints por Controller

### 1. Endereço — `/api/v1/enderecos`

**POST** — Criar endereço
```json
{
  "rua": "Rua das Flores",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "Juiz de Fora",
  "estado": "MG",
  "cep": "36000-000"
}
```
**Resultado esperado:** `201 Created` com o objeto criado e `id` gerado.

**GET** `/api/v1/enderecos` — Lista todos | Esperado: `200 OK` com array.

**GET** `/api/v1/enderecos/{id}` — Busca por ID | Esperado: `200 OK` ou `404 Not Found`.

**PUT** `/api/v1/enderecos/{id}` — Atualiza (mesmo body do POST) | Esperado: `200 OK`.

**DELETE** `/api/v1/enderecos/{id}` — Remove | Esperado: `204 No Content`.

---

### 2. Cinema — `/api/v1/cinemas`

**POST**
```json
{
  "nome": "Cinema Central",
  "idEndereco": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 3. TipoSala — `/api/v1/tiposalas`

**POST**
```json
{
  "tipo": "IMAX"
}
```
Outros tipos válidos: `LASER`, `VIP`, `COMUM`.

**Resultado esperado:** `201 Created`.

---

### 4. TipoAssento — `/api/v1/tipoassentos`

**POST**
```json
{
  "tipo": "COMUM"
}
```
**Resultado esperado:** `201 Created`.

---

### 5. TipoAudio — `/api/v1/tipoaudios`

**POST**
```json
{
  "tipo": "DUBLADO"
}
```
Outros: `LEGENDADO`, `NACIONAL`.

**Resultado esperado:** `201 Created`.

---

### 6. TipoImagem — `/api/v1/tipoimagens`

**POST**
```json
{
  "tipo": "2D"
}
```
Outros: `3D`, `VIP`, `IMAX`.

**Resultado esperado:** `201 Created`.

---

### 7. TipoIngresso — `/api/v1/tipoingressos`

**POST**
```json
{
  "tipo": "INTEIRA"
}
```
Outros: `MEIA`.

**Resultado esperado:** `201 Created`.

---

### 8. Sala — `/api/v1/salas`

**POST**
```json
{
  "numeroSala": 1,
  "idCinema": 1,
  "idTipoSala": 1
}
```
**Resultado esperado:** `201 Created`.

**Erro esperado:** `400 Bad Request` se `idCinema` ou `idTipoSala` não existirem.

---

### 9. Assento — `/api/v1/assentos`

**POST**
```json
{
  "posicao": "A1",
  "tipoAssentoId": 1,
  "idSala": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 10. Artista — `/api/v1/artistas`

**POST**
```json
{
  "nome": "Leonardo DiCaprio",
  "nacionalidade": "Americano"
}
```
**Resultado esperado:** `201 Created`.

---

### 11. Diretor — `/api/v1/diretores`

**POST**
```json
{
  "nome": "Christopher Nolan",
  "nacionalidade": "Britânico"
}
```
**Resultado esperado:** `201 Created`.

---

### 12. Gênero — `/api/v1/generos`

**POST**
```json
{
  "nome": "Ação"
}
```
**Resultado esperado:** `201 Created`.

---

### 13. Filme — `/api/v1/filmes`

**POST**
```json
{
  "nome": "Inception",
  "sinopse": "Um ladrão que rouba segredos dos sonhos.",
  "poster": "https://exemplo.com/poster.jpg",
  "duracao": 148,
  "classificacaoIndicativa": "DEZESSEIS"
}
```
Classificações válidas: `LIVRE`, `DEZ`, `DOZE`, `QUATORZE`, `DEZESSEIS`, `DEZOITO`.

**Resultado esperado:** `201 Created`.

---

### 14. FilmesArtista — `/api/v1/filmes-artista`

**POST**
```json
{
  "filmeId": 1,
  "artistaId": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 15. FilmesDiretor — `/api/v1/filmes-diretor`

**POST**
```json
{
  "filmeId": 1,
  "diretorId": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 16. GenerosFilme — `/api/v1/generos-filme`

**POST**
```json
{
  "idFilme": 1,
  "idGenero": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 17. FilmesCinema — `/api/v1/filmes-cinema`

**POST**
```json
{
  "filmeId": 1,
  "cinemaId": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 18. Sessão — `/api/v1/sessoes`

**POST**
```json
{
  "idFilme": 1,
  "idSala": 1,
  "idTipoAudio": 1,
  "idTipoImagem": 1,
  "horarioInicial": "2026-07-01T19:00:00"
}
```
**Resultado esperado:** `201 Created` com `precoBase` calculado automaticamente.

**Erros esperados:**
- `400 Bad Request` — sala ocupada no horário escolhido.
- `400 Bad Request` — campos obrigatórios ausentes.

**Cálculo do preço base:**
- Base: R$ 40,00
- TipoImagem `VIP`: +R$ 4,00
- TipoSala `LASER`: +R$ 4,00
- TipoSala `IMAX`: ×1,25
- TipoSala `VIP`: ×2,00

---

### 19. SessoesCinema — `/api/v1/sessoes-cinema`

**POST**
```json
{
  "cinemaId": 1,
  "sessaoId": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 20. Funcionário — `/api/v1/funcionarios`

**POST**
```json
{
  "nome": "João Silva",
  "matricula": 1001,
  "idCinema": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 21. Cliente — `/api/v1/clientes`

**POST**
```json
{
  "nome": "Maria Souza",
  "email": "maria@email.com",
  "senha": "senha123",
  "telefone": "32999999999",
  "endereco": 1
}
```
**Resultado esperado:** `201 Created`.

---

### 22. Compra — `/api/v1/compras`

**POST**
```json
{
  "dataHora": "2026-07-01T20:00:00",
  "formaPagamento": "CARTAO",
  "cinemaId": 1,
  "clienteId": 1,
  "usuarioId": 1
}
```
**Resultado esperado:** `201 Created` com `total` calculado automaticamente.

**Erro esperado:** `400 Bad Request` — compra sem ingressos associados.

---

### 23. Ingresso — `/api/v1/ingressos`

**POST**
```json
{
  "idTipoIngresso": 1,
  "idCompra": 1,
  "idAssento": 1,
  "idSessao": 1
}
```
**Resultado esperado:** `201 Created`.

**Erros esperados:**
- `400 Bad Request` — assento não pertence à sala da sessão.
- `400 Bad Request` — assento já ocupado nessa sessão.

---

## Resumo dos códigos HTTP esperados

| Situação | Código |
|---|---|
| Criação bem-sucedida | `201 Created` |
| Listagem / busca bem-sucedida | `200 OK` |
| Registro não encontrado | `404 Not Found` |
| Erro de validação / regra de negócio | `400 Bad Request` |
| Exclusão bem-sucedida | `204 No Content` |
