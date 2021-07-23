<h1 align="center">Programação com sockets Multicast e UDP</h1>
<p href="#descricao" align="center">Exercícios avalativos de Sistemas Distribuidos utilizando sockets Multicast e UDP.</p>

<div align="center">
  <img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>
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
- [Java](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html);

Executando o projeto
====================

## Exercício 01
### 🎲 Compilando o Chat
```bash
# Vá para a pasta src do ChatIRCTemplate e execute o comando:
$ javac *.java
```
### 🎲 Executando o Chat

```bash
# Com o código compilado execute o comando:
$ java ChatGUI
```

Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:

- [DatagramPacket]()
- [DatagramSocket]()
- [HashMap]()
- [InetAddress]()
- [MulticastSocket]()
- [Properties]()

Exemplos de Uso
==============

Após o início da aplicação, ver o passo [Executando o projeto](#executando-o-projeto), uma tela ira abrir, com as configurações padrão basta clicar em ```Entrar no grupo```, quando fizer isso em duas ou mais máquinas, é possível conversar com qualquer membro (inclusive você mesmo) via UDP e com ```Todos``` via Multicast.

O chat também possui uma função extra que permite obter informações do outro ***usuário***, 

Enviando a palavra ```#INFO``` para o ***usuário*** específico. Está opção não está disponível para o chat ```Todos```!!

Ver exemplo abaixo:

![Exemplo do ChatGUI](/exemplo_chatGUI.png)

Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
    <td align="center"><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/38047989?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" title="Rafael Soratto"><img href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" src="https://img.shields.io/badge/-RafaelSoratto-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"></a></td>
  </tr>
</table>
