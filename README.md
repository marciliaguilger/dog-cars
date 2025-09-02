# 🚗 Dog Cars - Sistema de Vendas de Carros

Sistema de vendas de carros com integração ao Mercado Pago, desenvolvido em Kotlin com Spring Boot.

## 📋 Funcionalidades

- **Gestão de Carros**: Cadastro e consulta de carros disponíveis
- **Gestão de Clientes**: Cadastro e consulta de clientes por documento
- **Vendas**: Criação, cancelamento e consulta de vendas
- **Pagamentos**: Integração com Mercado Pago via PIX
- **Webhooks**: Processamento de notificações do Mercado Pago
- **Health Checks**: Monitoramento via Spring Boot Actuator

## 🛠️ Stack Tecnológico

### Backend
- **Kotlin** + **Spring Boot 3.5**
- **Clean Architecture** (Domain-Driven Design)
- **Spring Data JPA** (persistência)
- **Spring Cloud OpenFeign** (integração HTTP)
- **Spring Boot Actuator** (observabilidade)

### Banco de Dados
- **PostgreSQL** (produção)
- **JPA/Hibernate** (ORM)

### Integrações
- **Mercado Pago API** (pagamentos PIX)
- **Webhooks** (notificações assíncronas)

### DevOps & Deploy
- **Docker** (containerização)
- **Kubernetes** (orquestração)
- **AWS ECR** (registry de imagens)
- **AWS EKS** (cluster Kubernetes)
- **GitHub Actions** (CI/CD)

## 🏗️ Arquitetura - Clean Architecture

O projeto segue os princípios da **Clean Architecture** com separação clara de responsabilidades:

```
src/main/kotlin/com/dog/cars/
├── domain/              # 🎯 Camada de Domínio (Entities + Business Rules)
│   ├── car/model/      # Entidades de Carro
│   ├── payments/model/ # Entidades de Pagamento
│   ├── person/model/   # Entidades de Pessoa
│   └── sales/model/    # Entidades de Venda
├── application/         # 🔄 Camada de Aplicação (Use Cases + Ports)
│   ├── usecase/        # Casos de Uso
│   ├── port/           # Interfaces (Ports)
│   ├── service/        # Serviços de Aplicação
│   └── config/         # Configuração de DI
├── infrastructure/      # 🔧 Camada de Infraestrutura (Adapters)
│   ├── persistence/    # Adaptadores de Persistência
│   ├── web/           # Adaptadores Web (HTTP, Feign)
│   └── config/        # Configurações de Infraestrutura
└── presentation/        # 🌐 Camada de Apresentação (Controllers + DTOs)
    ├── car/controller/
    ├── customer/controller/
    ├── sale/controller/
    └── webhook/controller/
```

### 📋 Princípios Aplicados

- ✅ **Dependency Rule**: Dependências apontam sempre para dentro
- ✅ **Separation of Concerns**: Cada camada tem responsabilidade específica
- ✅ **Ports & Adapters**: Interfaces definem contratos, implementações são intercambiáveis
- ✅ **Domain Purity**: Domínio sem dependências externas

> 📖 **Documentação completa**: Veja [CLEAN_ARCHITECTURE.md](./CLEAN_ARCHITECTURE.md)

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
export SPRING_PROFILES_ACTIVE=development
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