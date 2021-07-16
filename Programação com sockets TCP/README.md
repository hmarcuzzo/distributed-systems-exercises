<h1 align="center">Programação com sockets TCP</h1>
<p href="#descricao" align="center">Exercícios avalativos de Sistemas Distribuidos utilizando sockets TCP.</p>

<div align="center">
  <img src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white">
</div>

[comment]: <> (<h4 align="center"> )

[comment]: <> (  ✅  Projeto finalizado ✅)

[comment]: <> (</h4>)

Tabela de conteúdos
=================
<!--ts-->
   * [Pre Requisitos](#pre-requisitos)
   * [Executando o projeto](#executando-o-projeto)
   * [Bibliotecas Utilizadas](#bibliotecas-utilizadas)
   * [Exemplos de Uso](#exemplos-de-uso)
   * [Autores](#autores)
<!--te-->

Pre-requisitos
==============

Antes de começar, vai precisar ter instalado na sua máquina as seguintes ferramentas:
- [Python](https://www.python.org/);

Executando o projeto
====================

### 🎲 Executando o Servidor

```bash
# Vá para a pasta server do ex_01 ou ex_02
$ cd [ex_0X]/server

# Execute a aplicação
$ python3 socket_server.py

# O servidor inciará na porta: 10000
```

### 🎲 Executando o Cliente

```bash
# Vá para a pasta client do ex_01 ou ex_02
$ cd [ex_0X]/client

# Execute a aplicação
$ python3 socket_client.py

# O servidor tentarar conectar incialmente na porta: 10000
```

Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:

- [socket](https://docs.python.org/3/library/socket.html)
- [_thread](https://docs.python.org/3/library/_thread.html)

Exemplos de Uso
==============

Após o início da aplicação, ver o passo [Executando o projeto](#executando-o-projeto), o cliente é capaz de executar alguns 
comandos para que o servidor possa responder, portanto, basta digitalos no ```bash```. Os comandos que cada cliente e servidor 
atendem variam de acordo com o exercício para ver quais são atendidos basta olhar no *pdf* da atividade 
**Atividade 01 - TCP - v1.pdf**.

```bash
# No cliente do ex_01 execute:
$ TIME

# No cliente do ex_02 execute:
$ GETFILESLIST
```

* No *ex_02* o **servidor** guarda um log de todas as requisições feitas em: ```history.log```.

Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
  </tr>
</table>
