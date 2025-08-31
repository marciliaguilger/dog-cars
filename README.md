# 🚗 Dog Cars - Sistema de Vendas de Carros

Sistema de vendas de carros com integração ao Mercado Pago, desenvolvido em Kotlin com Spring Boot.

## 📋 Funcionalidades

- **Gestão de Carros**: Cadastro e consulta de carros disponíveis
- **Gestão de Clientes**: Cadastro e consulta de clientes por documento
- **Vendas**: Criação, cancelamento e consulta de vendas
- **Pagamentos**: Integração com Mercado Pago via PIX
- **Webhooks**: Processamento de notificações do Mercado Pago
- **Health Checks**: Monitoramento via Spring Boot Actuator

## 🛠️ Tecnologias

- **Kotlin** + **Spring Boot 3.5**
- **PostgreSQL** (banco de dados)
- **Spring Data JPA** (persistência)
- **Spring Cloud OpenFeign** (integração HTTP)
- **Mercado Pago API** (pagamentos)
- **Docker** + **Kubernetes** (deploy)
- **AWS ECR** + **EKS** (infraestrutura)

## 📁 Estrutura do Projeto

```
src/main/kotlin/com/dog/cars/
├── domain/           # Regras de negócio
│   ├── car/         # Domínio de carros
│   ├── payments/    # Domínio de pagamentos
│   ├── person/      # Domínio de pessoas
│   └── sales/       # Domínio de vendas
├── infrastructure/  # Configurações e integrações
└── presentation/    # Controllers e DTOs
    ├── car/
    ├── customer/
    ├── sale/
    └── webhook/
```

## 🚀 Executar Localmente

### Pré-requisitos

- **Java 17**
- **PostgreSQL** rodando
- **Gradle** (wrapper incluído)

### Configuração

1. **Clone o repositório**:
```bash
git clone git@github.com:marciliaguilger/dog-cars.git
cd dog-cars
```

2. **Configure as variáveis de ambiente**:
```bash
export DB_URL="jdbc:postgresql://localhost:5432/dog_cars"
export DB_USER="postgres"
export DB_PASSWORD="sua_senha"
export DB_NAME="dog_cars"
```

3. **Execute a aplicação**:
```bash
./gradlew bootRun
```

### Docker Local

```bash
# Build da imagem
docker build -t dog-cars .

# Executar com Docker Compose
cd local/
docker-compose up
```

## 📡 API Endpoints

### Carros
- `POST /cars` - Criar carro
- `GET /cars?available=true` - Listar carros disponíveis

### Clientes  
- `POST /customers` - Criar cliente
- `GET /customers/{document}` - Buscar cliente por documento

### Vendas
- `POST /sales` - Criar venda
- `GET /sales` - Listar todas as vendas
- `GET /sales/{buyerDocument}` - Vendas por cliente
- `DELETE /sales/{saleId}` - Cancelar venda

### Pagamentos
- `PUT /sales/{saleId}/payments` - Criar pagamento

### Webhooks
- `POST /webhooks/mercadopago` - Webhook do Mercado Pago

### Monitoramento
- `GET /actuator/health` - Status da aplicação
- `GET /actuator/info` - Informações da aplicação

## 🐳 Deploy

### CI/CD Automático

O projeto usa GitHub Actions para deploy automático:

1. **Build**: Compila e testa a aplicação
2. **Docker**: Constrói e publica imagem no AWS ECR
3. **Deploy**: Aplica no cluster EKS

### Deploy Manual

```bash
# Build da aplicação
./gradlew clean build

# Deploy no Kubernetes
kubectl apply -f k8s/
```

## ⚙️ Configuração

### Secrets Necessários (GitHub Actions)

```
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY  
AWS_ACCOUNT_ID
AWS_ROLE_TO_ASSUME
EKS_CLUSTER_NAME
DB_NAME
DB_USER
DB_PASSWORD
DB_HOST
```

### Variáveis de Ambiente

```yaml
DB_URL: jdbc:postgresql://host:5432/database
DB_USER: usuario_do_banco
DB_PASSWORD: senha_do_banco
DB_NAME: nome_do_banco
AWS_REGION: us-east-1
```

## 🔍 Troubleshooting

### Logs da Aplicação
```bash
kubectl logs deployment/dog-cars -n dog-cars -f
```

### Health Check
```bash
kubectl port-forward svc/dog-cars 8080:8080 -n dog-cars
curl http://localhost:8080/actuator/health
```

### Conexão com Banco
A aplicação usa logs detalhados para debug de conexão:
- HikariCP pool status
- SQL queries executadas
- Erros de conexão JDBC

## 📄 Documentação da API

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request