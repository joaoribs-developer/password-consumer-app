# 🔐 Password Consumer

Microserviço responsável por **gerar e atualizar senhas de usuários** a partir de eventos Kafka produzidos pelo `user-app`.  
Ele consome eventos de criação de senhas temporárias e definitivas e persiste essas informações no banco de dados.

---

## 🚀 Tecnologias

- **Kotlin 2.x**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Kafka**
- **PostgreSQL**

---

## ⚙️ Fluxo de Funcionamento

1. O `user-app` publica eventos nos tópicos:
   - `create_temporary_password`
   - `create_definitive_password`
2. O `consumer-app` escuta esses tópicos.
3. Ao receber os eventos:
   - Cria uma senha temporária e armazena no banco.
   - Atualiza a senha definitiva do usuário quando informado.
   - Define expiração de senha conforme a política da aplicação.
