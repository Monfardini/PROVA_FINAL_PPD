# Jantar dos Filósofos Distribuído

- **Clientes (Filósofos):** Representam os filósofos que competem por recursos compartilhados (garfos).
- **Servidor Centralizado:** Gerencia os recursos (garfos) e coordena o acesso entre os filósofos.

## Componentes do Sistema

### Classes Principais
1. **Server**
   - Gerencia os clientes conectados e controla o acesso aos recursos compartilhados (garfos).
   - Métodos principais:
     - `start()`: Inicializa o servidor e aguarda conexões de clientes.
     - `handleClient()`: Gerencia as solicitações de cada cliente.
     - `assignForks()`: Libera garfos para o cliente.
     - `releaseForks()`: Recolhe garfos de volta.

2. **PhilosopherClient**
   - Representa o cliente que simula o comportamento de um filósofo.
   - Métodos principais:
     - `connectToServer()`: Estabelece conexão com o servidor.
     - `requestForks()`: Solicita os garfos ao servidor.
     - `releaseForks()`: Libera os garfos.
     - `think()` e `eat()`: Simulam o pensamento e a refeição do filósofo.

3. **Fork**
   - Representa um recurso compartilhado (garfo).
   - Métodos principais:
     - `pickUp()`: Aloca o garfo a um filósofo.
     - `pickDown()`: Libera o garfo.

4. **Philosopher**
   - Representa a lógica do filósofo, incluindo estados e interações com os garfos.
   - Métodos principais:
     - `think()`: Simula o pensamento.
     - `eat()`: Simula a refeição.

5. **ProtocolConstants**
   - Define as constantes de protocolo utilizadas na comunicação cliente-servidor.
     - `HELLO`, `REQUEST_FORKS`, `RELEASE_FORKS` etc.

### Fluxo de Execução
1. **Servidor**:
   - Inicializa e aguarda conexões de filósofos.
   - Atribui um ID único a cada filósofo conectado.
   - Gerencia solicitações de garfos e registros de atividades.

2. **Clientes (Filósofos):**
   - Conectam-se ao servidor e recebem seu ID.
   - Solicita garfos antes de comer.
   - Libera garfos após a refeição.
   - Simula os estados de pensar e comer em ciclos.

## Protocolo de Comunicação
O protocolo segue mensagens claras e bem definidas:
- `HELLO`: Mensagem inicial para estabelecer a conexão.
  - **Servidor Responde:** `HI <ID>`
- `REQUEST_FORKS`: Solicitação de garfos para comer.
  - **Servidor Responde:** `FORKS_GRANTED` ou `WAIT`
- `RELEASE_FORKS`: Notificação de liberação de garfos.
  - **Servidor Responde:** `ACK`

## Arquitetura
### Diagrama de Classes e Componentes
O diagrama inclui as classes principais e suas interações:
- **Server**: Gerencia clientes e garfos.
- **PhilosopherClient**: Filósofo cliente que interage com o servidor.
- **Fork**: Recurso compartilhado gerenciado pelo servidor.
- **Philosopher**: Lógica interna de cada filósofo.
- **ProtocolConstants**: Protocolo de comunicação.
