# Sistema de Microsserviços com Gateway e Autenticação JWT

Este projeto implementa uma arquitetura de microsserviços segura, utilizando Spring Cloud Gateway como ponto de entrada único e gerenciador de segurança.

## Por que
O objetivo é demonstrar a segregação de responsabilidades em um ambiente distribuído:
- **API Gateway**: Centraliza a validação de tokens JWT, retirando essa carga dos serviços internos.
- **Auth Service**: Gerencia o ciclo de vida do usuário (registro/login) e emissão de tokens, persistindo dados no MongoDB.
- **Privado Service**: Exemplo de recurso protegido que só é alcançado após validação bem-sucedida no Gateway.

## Arquitetura (fluxo)
O tráfego é gerenciado pelo **API Gateway** na porta `8080`:

1. **Rotas Públicas** (`/auth/*`):
   - O Gateway repassa diretamente para o `auth-service`.
   - O usuário realiza login/registro e recebe um **JWT**.

2. **Rotas Protegidas** (`/privado`):
   - O Gateway intercepta a requisição.
   - Valida a assinatura e validade do token `Bearer` usando a `JWT_SECRET`.
   - Se válido, encaminha para o `privado-service`.
   - Se inválido, retorna `401 Unauthorized` imediatamente.

## Endpoints

### Autenticação (Público)
| Método | Rota | Descrição | Payload |
|---|---|---|---|
| `POST` | `/auth/register` | Cria um usuário | `{"email": "...", "password": "...", "name": "..."}` |
| `POST` | `/auth/login` | Autentica e gera token | `{"email": "...", "password": "..."}` |

### Recurso Protegido
| Método | Rota | Descrição | Header Obrigatório |
|---|---|---|---|
| `GET` | `/privado` | Retorna mensagem de sucesso | `Authorization: Bearer <token>` |

## Rodando
Certifique-se de ter o Docker e Docker Compose instalados.

```bash
# Sobe todos os serviços (Gateway, Auth, Privado, MongoDB)
docker compose up -d --build
```
O serviço estará disponível em: `http://localhost:8080`

## Teste Rápido

1. **Criar Usuário:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"dev@teste.com","password":"123","name":"Dev Teste"}'
```

2. **Obter Token:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"dev@teste.com","password":"123"}'
```
*(Copie o token da resposta)*

3. **Acessar Rota Privada:**
```bash
TOKEN="COLE_SEU_TOKEN_AQUI"
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/privado
```

## Variáveis de Ambiente
As principais configurações são definidas no `docker-compose.yml`:

- `JWT_SECRET`: Chave utilizada para assinar e validar os tokens (compartilhada entre Gateway e Auth).
- `SPRING_DATA_MONGODB_URI`: Endereço de conexão com o banco MongoDB.
- `AUTH_URL` / `PRIVADO_URL`: Endereços internos para roteamento do Gateway.
