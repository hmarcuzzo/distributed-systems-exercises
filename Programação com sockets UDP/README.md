<h1 align="center">Programação com sockets UDP</h1>
<p href="#descricao" align="center">Exercícios avalativos de Sistemas Distribuidos utilizando sockets UDP.</p>

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

## Exercício 01
### 🎲 Executando o Chat P2P
```bash
# Vá para a pasta src do ex_01 e execute a aplicação duas
#   vezes colocando seu Nome, IP e Porta (É possível omitir 
#   o nome e o IP, mas a porta é obrigatório).
$ python3 udp_chat.py
```

## Exercício 02
### 🎲 Executando o Servidor

```bash
# Vá para a pasta server do ex_02 e execute a aplicação
$ python3 udp_server.py
```

### 🎲 Executando o Cliente

```bash
# Vá para a pasta client do ex_02 e execute a aplicação
$ python3 udp_client.py
```

Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:

- [socket](https://docs.python.org/3/library/socket.html)
- [_thread](https://docs.python.org/3/library/_thread.html)
- [hashlib](https://docs.python.org/3/library/hashlib.html)

Exemplos de Uso
==============

Após o início da aplicação, ver o passo [Executando o projeto](#executando-o-projeto), o ```ex_01``` é capaz de fazer 
comunicação entre duas pessoas via chat P2P e o ```ex_02``` é capaz de fazer transferências de arquivos entre o 
***cliente*** e o ***servidor***. 

Para entender melhor o que o código de cada exercício faz, bastar olhar o *pdf* da atividade **Atividade 02 - UDP.pdf**.

```bash
# Colocando o Nome, IP e Porta no ex_01:
$ Nickname: Henrique
$ IP: 127.0.0.1
$ Port: 10000

# Apartir daqui as mensagens podem ser enviadas
$ Henrique: Ola

# No cliente do ex_02 execute:
$ SENDFILE img_teste.jpg
```

Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
  </tr>
</table>
