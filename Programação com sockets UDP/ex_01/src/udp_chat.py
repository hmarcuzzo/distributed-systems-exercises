"""Este é um char P2P que trabalha com código UDP, e foi adaptado para executar em uma máquina só,
caso exista mais de uma máquina, alguns ajustes devem ser feito na parte de conexão.

Protocolo:
Para compreender melhor o protocolo utilizado nesta implementação, recomendo fortemente que leia o PDF
"Atividade 02 - UDP.pdf", mas uma rápida explicação do protocolo seria:

     1 byte             0 - 255 bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|   Nick Size   |      Nick [Nick Size]     |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Message Size  |   Message [Message Size]  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

Autores:
    @hmarcuzzo (Henrique Marcuzzo)

Data de Criação: 15 de Jul de 2021
Ultima alteração: 16 de Jul de 2021
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
    """Este método irá executar em paralelo com a thread main, ficando essa thread responsável por escutar
    todos os pacotes que vierem pela porta especificada.

    :param sock: socket UDP.
    :param server_address: tupla de IP e porta que será recebido as menssagens.
    """
    global bufferSize

    while True:
        bytesAddressPair = sock.recvfrom(bufferSize)

        message = bytesAddressPair[0]
        address = bytesAddressPair[1]

        len_nickname = int.from_bytes(message[0:1], BYTEORDER)
        nickname = message[1:(len_nickname + 1)].decode('utf-8')

        i_index = len_nickname + 1
        f_index = i_index + 1
        len_message = int.from_bytes(message[i_index:f_index], BYTEORDER)

        i_index = f_index
        f_index = i_index + len_message
        message = message[i_index:f_index].decode('utf-8')

        print('{}: {}'.format(nickname, message))


if __name__ == '__main__':
    nickname = input('Nickname: ')
    if nickname == '':
        nickname = 'User {}'.format((randint(1, 10)))
    len_nickname = len(nickname)
    adress = input('IP Adress: ')
    port = int(input('UDP port: '))

    if adress == '':
        adress = '127.0.0.1'

    # Criando o socket de datagrama UDP
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Ligar o socket a sua porta UDP
    server_address = (adress, port)
    listen_address = (adress, (port + 1))
    try:
        sock.bind(listen_address)

        # Conexão criada e esperando
        print('UDP server up and listening')

    except OSError:
        listen_address = server_address
        server_address = (adress, (port + 1))
        sock.bind(listen_address)

    # Criar uma thread para recebimento de menssagens
    _thread.start_new_thread(recive_message, (sock, listen_address))

    # Thread principal fica responsável pelo envio de mensagens para a endereço especificado
    while True:
        send_message = len_nickname.to_bytes(1, BYTEORDER)
        send_message += bytes(nickname, 'utf-8')

        user_message = input(f'{nickname}: ')

        send_message += len(user_message).to_bytes(1, BYTEORDER)
        send_message += bytes(user_message, 'utf-8')

        sock.sendto(send_message, server_address)