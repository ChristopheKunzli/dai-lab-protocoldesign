# Lab 3 - Protocol design

## Overview

Client-server application that allows the client to send a mathematical expression to the server and get the result back
as response

## Transport layer protocol

- Communication protocol : TCP
- Server address : 1.2.3.4
- Server port : 12345

### Supported operations

Operations supported by the server are:

- ADD
- SUB
- MUL
- DIV
- POW (power)
- SQRT (square root)
- FACT (factorial)
- LOG_N (logarithm with base n)

### Operation format

An operation is sent to the server in the following format:

```
<operation> <operand1> <operand2> ... <operandN>
```

Where operands can be integers or real numbers. Positive and negative numbers are supported.
The server will respond with the result of the operation. The result will be sent as a real number.

- For the operations `SQRT` or `FACT`, the server will only consider the first operand and ignore the rest.
- For `LOG_N`, the server will consider the first operand as the base and the second operand as the number.
- For `POW`, the server will consider the first operand as the base and the second operand as the exponent.
- For `ADD`, `SUB`, `MUL`, and `DIV`, the server will consider all operands from left to right.

## Server-specifications

- The server sends a welcome message to the client listing all supported operations when a connection is established.
- The server will listen on port 12345.
- The server will close the connection after the client sends the `exit` command.
- The servers logs all operations and results to its console.
- Operations are case-insensitive. (e.g. `ADD` = `add` = `Add`)
- The server will respond with an error message if the operation results in an error (e.g. division by zero, SQRT of
  negative number, etc.)

## Client-specifications

- On launch, the client will open a connection and wait for the user to enter a command.
- Type `exit` to close the connection.
- The client sends the operation to the server and displays the received result.
- The client will display any error messages received from the server.

### Error handling client-side

Before sending an operation to the server, the client will verify that the operation is valid.
If the operation is not valid, the client will display an error message and wait for the user to enter a new command.
The previous command is ignored.

An operation is considered valid if:

- The operation does not contain an operation word.
- The operation contains an operation word that is not the first word in the request.
- The operation contains an operation word that is not in the list of supported operations.
- The operation contains an incorrect number of operands.
- One or more operands are not numbers.
- The operands are not separated by a space.
- The request is empty.

## Communication-protocol

All messages are sent in plain text, encoded in UTF-8. Every line of text is terminated by a newline character. (`\n`)

There are 2 types of messages:

1. `OPERATION <operation> <operand1> ... <operandN>` : Sent by the client to the server to perform an operation. The
   server will respond with the result of the operation.
2. `EXIT` : Sent by the client to the server to close the connection. The server will respond with a goodbye
   message.

## Examples of communication

```
Client: CONNECT 
Server: Welcome to the calculator server. Supported operations are: ADD, SUB, MUL, DIV, POW, SQRT, FACT, LOG_N

Client: ADD 10 20
Server: 30

Client: SUB 10 20
Server: -10

Client: MUL 10 20 3
Server: 600

Client: DIV 10 -20
Server: -0.5

Client: POW 2 3
Server: 8

Client: SQRT 9
Server: 3

Client: SQRT -9
Server: Square root of negative number

Client: FACT 5
Server: 120

Client: FACT -5
Server: Factorial of negative number

Client: LOG_N 2 8
Server: 3

Client: LOG_N -2 8
Server: Logarithm of negative number

Client: LOG_N 2 -8
Server: Logarithm of negative number

Client: DIV 10 0
Server: Division by zero

Client: exit
Server: Connection closed
```