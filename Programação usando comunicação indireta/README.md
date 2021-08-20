<h1 align="center">Programação com Comunicação Indireta</h1>
<p href="#descricao" align="center"> Exercícios avalativos de Sistemas Distribuidos utilizando comunicação indireta.
 O objetivo é tornar a entrada, classificação e geração de filas direcionadas de acordo com o conteúdo do tweet.
Pode-se utilizar essas informações para um sistema de recomendação de melhorias ou mudanças na empresa.
Filas de classificação de tweets para viagens aéreas. O algoritmo recebe uma entrada no formato csv contendo tweets, processa as informações do tweet, classifica ele como positivo, neutro e negativo em relação a compania aérea. Cada tweet é classificado e colocado em uma fila: a fila de tweets negativos vão para o RH da empresa. Os positivos vão para publicidade. E os neutros são contabilizados para análise de melhoria. </p>

<div align="center">
  <img alt="Python" src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white">
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
- [Python](https://www.python.org/)
- [RabbitMQ](https://www.rabbitmq.com/)

Executando o projeto
====================

## 🎲 Cliente
```bash
# Na pasta do src/ execute:
$ python3 client.py
```

## 🎲 Recive
```bash
# Na pasta do src/ execute:
$ python3 classifier.py
```
## 🎲 Send
```bash
# Na pasta do src/ execute:
$ python3 collector.py
```


Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:
#### Python
- [Pika]()
- [Datatime]()
- [Pandas]()
- [Json]()

Exemplos de Uso
==============

Nesta versão o cliente seleciona o tópico de interesse que deseja receber notificações. 

Assim toda mensagem encontrada relacionada ao assunto de interesse do cliente será enviado a notificação.

*Obs:* É importante que o cliente seja iniciado préviamente. 

Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
    <td align="center"><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/38047989?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" title="Rafael Soratto"><img href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" src="https://img.shields.io/badge/-RafaelSoratto-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"></a></td>
  </tr>
</table>
