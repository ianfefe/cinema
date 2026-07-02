#!/bin/bash
# Sobe banco (Docker) e aplicação Spring Boot
set -e

cd "$(dirname "$0")"

# 1. Sobe o container do Postgres se não estiver rodando
if [ "$(docker inspect -f '{{.State.Running}}' cinema_db 2>/dev/null)" != "true" ]; then
    echo "==> Iniciando container cinema_db..."
    docker start cinema_db >/dev/null
    sleep 3
else
    echo "==> Container cinema_db já está rodando"
fi

# 2. Libera porta 8080 se estiver em uso
if ss -tln | grep -q ':8080 '; then
    echo "==> Liberando porta 8080..."
    fuser -k 8080/tcp 2>/dev/null || true
    sleep 2
fi

# 3. Sobe a aplicação
echo "==> Iniciando Spring Boot..."
echo ""
echo "======================================================"
echo "  Aplicação: http://localhost:8080"
echo "  Swagger:   http://localhost:8080/swagger-ui.html"
echo "======================================================"
echo ""
./mvnw spring-boot:run
