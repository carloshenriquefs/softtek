# API Sistema de Saúde Mental - Conformidade NR-1

Esta é uma API REST completa para sistema de saúde mental empresarial, desenvolvida com Spring Boot, seguindo as diretrizes da NR-1 (Norma Regulamentadora de Segurança e Saúde no Trabalho).

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security** (Autenticação JWT)
- **Spring Data MongoDB**
- **MongoDB** (Banco de dados)
- **Lombok** (Redução de boilerplate)
- **SpringDoc OpenAPI** (Documentação da API)
- **Maven** (Gerenciamento de dependências)

## 📋 Funcionalidades

### Autenticação e Autorização
- ✅ Sistema de login com JWT
- ✅ Controle de acesso baseado em perfis (EMPLOYEE, HR, OSH, ADMIN, AUDITOR)
- ✅ Refresh token para renovação automática
- ✅ Auditoria completa de ações do usuário

### Avaliações de Saúde Mental
- ✅ Questionários dinâmicos por categoria
- ✅ Cálculo automático de scores e níveis de risco
- ✅ Recomendações personalizadas baseadas no resultado
- ✅ Histórico completo de avaliações
- ✅ Alertas para casos críticos

### Recursos de Apoio
- ✅ Biblioteca de recursos (artigos, vídeos, contatos)
- ✅ Hotlines de emergência
- ✅ Guias e orientações
- ✅ Filtros por categoria e nível de risco

### Conformidade e Auditoria
- ✅ Log completo de todas as ações (NR-1)
- ✅ Retenção de dados por 7 anos
- ✅ Hash de integridade para dados críticos
- ✅ Relatórios de auditoria
- ✅ Conformidade com LGPD

## 🏗️ Arquitetura

```
src/main/java/com/saudemental/api/
├── config/           # Configurações (Segurança, Dados Iniciais)
├── controller/       # Controladores REST
├── model/
│   ├── dto/         # Data Transfer Objects
│   ├── entity/      # Entidades JPA/MongoDB
│   └── enums/       # Enumerações
├── repository/       # Repositórios de dados
├── security/         # Configurações de segurança
└── service/         # Lógica de negócio
```

## 🛠️ Configuração e Instalação

### Pré-requisitos
- Java 17 ou superior
- MongoDB 4.4 ou superior
- Maven 3.6 ou superior

### 1. Clone o repositório
```bash
git clone [url-do-repositorio]
cd softtek
```

### 2. Configure o MongoDB
Certifique-se de que o MongoDB esteja rodando na porta padrão (27017) ou configure a URL no `application.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/saude_mental
```

### 3. Configure as variáveis de ambiente (opcional)
```bash
export JWT_SECRET=suaChaveSecretaMuitoSegura
export MONGODB_URI=mongodb://localhost:27017/saude_mental
```

### 4. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

### Swagger/OpenAPI
Acesse a documentação interativa em: `http://localhost:8080/swagger-ui.html`

### Endpoints Principais

#### Autenticação
```http
POST /api/v1/auth/login
POST /api/v1/auth/refresh
GET  /api/v1/auth/profile
POST /api/v1/auth/logout
```

#### Avaliações
```http
GET  /api/v1/assessments/questions
POST /api/v1/assessments
GET  /api/v1/assessments
GET  /api/v1/assessments/{id}
```

#### Recursos
```http
GET  /api/v1/resources
GET  /api/v1/resources/{id}
```

#### Health Check
```http
GET  /api/v1/health
```

## 🔐 Usuários de Teste

A aplicação cria automaticamente usuários de teste:

| Email | Senha | Perfil | Descrição |
|-------|-------|---------|-----------|
| admin@empresa.com | admin123 | ADMIN | Administrador do sistema |
| rh@empresa.com | rh123 | HR | Recursos Humanos |
| funcionario@empresa.com | func123 | EMPLOYEE | Funcionário padrão |

## 📊 Exemplo de Uso

### 1. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "funcionario@empresa.com",
    "password": "func123"
  }'
```

### 2. Obter Perguntas
```bash
curl -X GET http://localhost:8080/api/v1/assessments/questions \
  -H "Authorization: Bearer {seu-token}"
```

### 3. Submeter Avaliação
```bash
curl -X POST http://localhost:8080/api/v1/assessments \
  -H "Authorization: Bearer {seu-token}" \
  -H "Content-Type: application/json" \
  -d '{
    "type": "STRESS_LEVEL",
    "responses": {
      "question1": 4,
      "question2": "frequentemente",
      "question3": 3
    }
  }'
```

## 🔒 Segurança

- **Autenticação JWT** com tokens de acesso e refresh
- **Autorização baseada em perfis** para diferentes níveis de acesso
- **Validação de entrada** em todos os endpoints
- **CORS configurado** para ambientes de desenvolvimento e produção
- **Auditoria completa** de todas as ações sensíveis
- **Criptografia** de senhas com BCrypt

## 📈 Monitoramento

A aplicação inclui endpoints de monitoramento via Spring Boot Actuator:

- `/actuator/health` - Status da aplicação
- `/actuator/metrics` - Métricas da aplicação
- `/actuator/info` - Informações da aplicação

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📝 Conformidade NR-1

A API está em conformidade com a NR-1:

- ✅ **Auditoria**: Todas as ações são logadas com timestamp, usuário e IP
- ✅ **Retenção**: Dados mantidos por 7 anos conforme exigido
- ✅ **Integridade**: Hash SHA-256 para validação de dados críticos
- ✅ **Privacidade**: Conformidade com LGPD para dados pessoais
- ✅ **Rastreabilidade**: Trilha completa de auditoria para investigações

## 🐳 Docker (Opcional)

Para executar com Docker Compose:

```bash
cd docker
docker-compose up -d
```

## 📞 Suporte

Para dúvidas ou suporte técnico:
- Email: suporte@empresa.com
- Documentação: [Link para documentação técnica]
- Issues: [Link para sistema de tickets]

## 📄 Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

---

**Desenvolvido para conformidade com NR-1 e melhores práticas de saúde mental no trabalho.**
