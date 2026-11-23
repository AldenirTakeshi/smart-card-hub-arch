# Smart Card Hub

Projeto de estudo que simula a **gestão de cartões e limite de crédito**.

O objetivo é aprender a construir um backend financeiro do jeito certo: começando pelas **regras de negócio**, com testes, antes de pensar em banco de dados, APIs ou mensageria. O projeto evolui em etapas pequenas, e cada etapa fica registrada no histórico de commits.

> 🚧 **Em desenvolvimento.** Etapa atual: modelagem do domínio.

## Tecnologias

- Java 21
- Spring Boot 4
- JUnit 5
- Maven

## O que já está pronto

### `Money`: valor monetário

Representa dinheiro de forma segura.

- Usa `BigDecimal` em vez de `double`, que gera erros de arredondamento com centavos (`0.1 + 0.2 = 0.30000000000000004`).
- É **imutável**: operações como `somar` e `subtrair` devolvem um novo objeto, sem alterar o original.
- **Não aceita valor negativo**: um `Money` inválido não chega a existir.

### `CreditLine`: linha de crédito

Controla o limite de crédito do cliente, seguindo o fluxo que os bancos usam numa compra com cartão:

| Operação | O que acontece | Efeito no disponível |
|---|---|---|
| `reservar` | A compra é autorizada e o valor fica separado | diminui |
| `capturar` | A compra é confirmada e vira gasto efetivo | não muda |
| `liberar` | A compra é cancelada e a reserva é desfeita | aumenta |

```
disponível = limite total − reservado − capturado
```

Regras garantidas:

- Não é possível reservar acima do limite disponível (`LimiteInsuficienteException`).
- Não é possível capturar ou liberar mais do que está reservado (`ReservaInsuficienteException`).

### `Card`: cartão

Representa o cartão do cliente e controla se ele pode ser usado.

| Status | Pode comprar? |
|---|---|
| `ATIVO` | sim |
| `BLOQUEADO` | não (bloqueio temporário, pode ser desfeito) |
| `CANCELADO` | não (definitivo, sem volta) |

```
ATIVO ⇄ BLOQUEADO
ATIVO ou BLOQUEADO → CANCELADO
```

Numa compra, o cartão verifica o próprio status **antes** de pedir a reserva à linha de crédito. Assim, uma compra recusada nunca consome limite.

Vários cartões podem compartilhar a mesma linha de crédito. Por exemplo, bloquear o cartão físico não impede que o virtual continue comprando.

Regras garantidas:

- Transições de status inválidas são recusadas (`TransicaoDeStatusInvalidaException`).
- Cartão bloqueado ou cancelado não compra (`CartaoInativoException`).

## Testes

As regras do domínio são cobertas por testes unitários, incluindo os **casos de fronteira**: uma compra exatamente igual ao disponível é aprovada, e uma compra 1 centavo acima é recusada.

Para rodar:

```bash
mvn test
```

## Próximos passos

- [x] `Money`: value object monetário
- [x] `CreditLine`: reserva, captura e liberação de limite
- [x] `Card`: status do cartão (ativo, bloqueado, cancelado) e regras de bloqueio
- [ ] Casos de uso e **arquitetura hexagonal** (portas e adaptadores)
- [ ] Persistência com **PostgreSQL** e Flyway
- [ ] **API REST**
- [ ] Eventos com **Apache Kafka**
- [ ] Ambiente completo com **Docker Compose**
