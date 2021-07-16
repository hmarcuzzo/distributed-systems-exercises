"""Este código é responsavel pela parte do servidor e comunicação com o cliente, ele ira tratar as requisições
do cliente e processa-las de acordo, e em seguida irá retornar uma respostas via socket TCP.

Autores:
    @hmarcuzzo
    @mathbatistela

Data de Criação: 15 de Jul de 2021
Ultima alteração: 15 de Jul de 2021
"""

import copy
import socket
import sys
import _thread
import os

from datetime import datetime
from random import seed
from random import randint


# GLOBAL VARIABLES
BYTEORDER = 'big'

bufferSize = 1024


def recive_message(sock, server_address):
    """Este método irá receber uma requisição, processa-la de acordo com o código da requisição
    e enviar uma resposta para o cliente que fez a requisição.

    :param sock: ...
    :param server_address: ...
    """
    global bufferSize

    while True:
        bytesAddressPair = sock.recvfrom(bufferSize)

        message = bytesAddressPair[0]
        address = bytesAddressPair[1]

        print(message)


if __name__ == '__main__':
    nickname = input('Nickname: ')
    if nickname == '':
        nickname = 'User {}'.format((randint(1, 10)))
    len_nickname = len(nickname)
    adress = input('IP Adress: ')
    port = int(input('UDP port: '))

    if adress == '':
        adress = '127.0.0.1'

    # Criando o socket UDP
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Ligar o socket a sua porta UDP
    server_address = (adress, port)
    listen_address = (adress, (port + 1))
    try:
        sock.bind(listen_address)

        # Esperando por uma conexão
        print('UDP server up and listening')

    except OSError:
        listen_address = server_address
        server_address = (adress, (port + 1))
        sock.bind(listen_address)

    # Criar uma thread para recebimento de menssagens
    _thread.start_new_thread(recive_message, (sock, listen_address))

    while True:
        send_message = len_nickname.to_bytes(1, BYTEORDER)
        send_message += bytes(nickname, 'utf-8')

        user_message = input(f'{nickname}: ')

        send_message += len(user_message).to_bytes(1, BYTEORDER)
        send_message += bytes(user_message, 'utf-8')

        sock.sendto(send_message, server_address)