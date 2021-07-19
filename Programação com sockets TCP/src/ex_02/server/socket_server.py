"""Este código é responsavel pela parte do servidor e comunicação com o cliente, ele ira tratar as requisições
do cliente e processa-las de acordo, e em seguida irá retornar uma respostas via socket TCP.

Protocolo:
*O protocolo utilizado neste cliente/servidor pode ser encontrado detalahadamente no PDF da atividade
    "Atividade 01 - TCP - v1.pdf"

Autores:
    @hmarcuzzo

Data de Criação: 01 de Jul de 2021
Ultima alteração: 05 de Jul de 2021
"""

import copy
import socket
import sys
import _thread
import os

from datetime import datetime


# GLOBAL VARIABLES
REQ_CODE = 1
RESP_CODE = 2

COMMANDS_CODES = {
    'DEFAULT': 0,
    'ADDFILE': 1,
    'DELETE': 2,
    'GETFILESLIST': 3,
    'GETFILE': 4,
    'EXIT': 5,
    1: 'ADDFILE',
    2: 'DELETE',
    3: 'GETFILESLIST',
    4: 'GETFILE',
    5: 'EXIT',
    0: 'DEFAULT'
}

STATUS_CODE = {
    'SUCCESS': 1,
    'ERROR': 2,
    1: 'SUCCESS',
    2: 'ERROR'
}

BYTEORDER = 'big'
LOCAL_SERVER = ''


def on_new_client(clientsocket, addr):
    """Este método irá receber uma requisição, processa-la de acordo com o código da requisição
    e enviar uma resposta para o cliente que fez a requisição.


    :param clientsocket: Socket TCP que o servidor deve ficar escutando a requisição e enviar a resposta
    :param addr: Endereço do cliente
    """

    global LOCAL_SERVER
    print(f'Connection established with {addr}')

    while True:
        req_message = clientsocket.recv(1024)
        print(f'{addr} >> {req_message}')

        message_type = req_message[0:1]
        command = req_message[1:2]
        filename_size = req_message[2:3]
        filename = req_message[3:(3 + int.from_bytes(filename_size, BYTEORDER))]

        # Escreve todas as requisições feitas ao servidor e seus respectivos endereços do cliente
        with open("history.log", "a") as file_object:
            message_log = f'{datetime.now()} {addr} >> {message_type+command+filename_size+filename}\n'
            file_object.write(message_log)

        resp_message = RESP_CODE.to_bytes(1, BYTEORDER)
        resp_message += int.from_bytes(command, BYTEORDER).to_bytes(1, BYTEORDER)

        if COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'EXIT':
            resp_message += STATUS_CODE['SUCCESS'].to_bytes(1, BYTEORDER)
            clientsocket.send(resp_message)
            break

        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'DELETE':
            if os.path.isfile(filename.decode('utf-8')):
                os.remove(filename.decode('utf-8'))
                resp_message += STATUS_CODE['SUCCESS'].to_bytes(1, BYTEORDER)
            else:
                resp_message += STATUS_CODE['ERROR'].to_bytes(1, BYTEORDER)

            clientsocket.send(resp_message)

        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'GETFILESLIST':
            resp_message += STATUS_CODE['SUCCESS'].to_bytes(1, BYTEORDER)

            files = os.listdir(LOCAL_SERVER)
            resp_message += len(files).to_bytes(2, BYTEORDER)

            repeatable_resp_message = copy.deepcopy(resp_message)
            if len(files) > 0:
                repeatable_resp_message += len(files[0]).to_bytes(1, BYTEORDER)
                repeatable_resp_message += bytes(files[0], 'utf-8')
            else:
                repeatable_resp_message += (0).to_bytes(1, BYTEORDER)
                repeatable_resp_message += bytes('', 'utf-8')

            clientsocket.send(repeatable_resp_message)

            for index in range(1, len(files)):
                repeatable_resp_message = copy.deepcopy(resp_message)
                repeatable_resp_message += len(files[index]).to_bytes(1, BYTEORDER)
                repeatable_resp_message += bytes(files[index], 'utf-8')
                clientsocket.send(repeatable_resp_message)

        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'GETFILE':
            if os.path.isfile(filename):
                resp_message += STATUS_CODE['SUCCESS'].to_bytes(1, BYTEORDER)

                file_size = os.path.getsize(filename.decode('utf-8'))
                resp_message += file_size.to_bytes(4, BYTEORDER)

                with open(filename.decode('utf-8'), 'rb') as file_to_send:
                    for data in file_to_send:
                        resp_message += data
                        clientsocket.send(resp_message)
                        resp_message = bytes()
            else:
                resp_message += STATUS_CODE['ERROR'].to_bytes(1, BYTEORDER)
                resp_message += (0).to_bytes(4, BYTEORDER)
                resp_message += bytes(0)

                clientsocket.send(resp_message)

        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'ADDFILE':
            resp_message += STATUS_CODE['SUCCESS'].to_bytes(1, BYTEORDER)

            bytes_received = 0

            i_index = 3 + int.from_bytes(filename_size, BYTEORDER)
            f_index = i_index + 4
            file_size = int.from_bytes(req_message[i_index:f_index], BYTEORDER)

            data_write = req_message[f_index:]
            bytes_received += len(data_write)

            with open(os.path.join(LOCAL_SERVER, filename.decode('utf-8')), 'wb') as file_to_write:
                file_to_write.write(data_write)

                while True:
                    if bytes_received >= file_size:
                        break

                    req_message = clientsocket.recv(1024)

                    data_write = req_message
                    bytes_received += len(data_write)

                    file_to_write.write(data_write)

                    if bytes_received >= file_size:
                        break

            clientsocket.send(resp_message)

        else:
            resp_message += STATUS_CODE['ERROR'].to_bytes(1, BYTEORDER)
            clientsocket.send(resp_message)

    clientsocket.close()


if __name__ == '__main__':

    LOCAL_SERVER = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Ligar o socket a sua porta TCP
    server_address = ('localhost', 10100)
    print(f'starting up on {server_address[0]} port {server_address[1]}')
    sock.bind(server_address)

    # Esperando por uma conexão
    print('waiting for a connection')

    # Escutando novas conexões.
    sock.listen(1)

    # Criar uma thread para cada nova conexão
    while True:
        connection, client_address = sock.accept()
        _thread.start_new_thread(on_new_client, (connection, client_address))
