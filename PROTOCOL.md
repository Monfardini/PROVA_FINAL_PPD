# Protocolo de Comunicação

## Conexão
1. **Cliente envia:** `HELLO`
2. **Servidor responde:** `HI <ID_UNICO>`

## Solicitação de Garfos
1. **Cliente envia:** `REQUEST_FORKS`
2. **Servidor responde:** `FORKS_GRANTED` ou `WAITING`

## Liberação de Garfos
1. **Cliente envia:** `RELEASE_FORKS`
2. **Servidor responde:** `FORKS_RELEASED`
