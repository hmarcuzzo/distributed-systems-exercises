"""Este código é responsavel pela parte do servidor e comunicação com o cliente, ele ira tratar as requisições
do cliente e processa-las de acordo, e em seguida irá retornar uma respostas via socket TCP.

Protocolo:
       1 byte        [0 a 255] Bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|  Message size |       Message           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


* Se for um Download o message size passa a ser de 4 bytes e o message de 1 a 2^32 bytes (message é enviado byte a byte)
* Se for o comando FILE a primeira mensagem vai apenas com a quantidade de arquivos no Message size e após isso
    os nomes dos arquivos são enviados individualmente.

Autores:
    @hmarcuzzo (Henrique Marcuzzo)

Data de Criação: 28 de Jun de 2021
Ultima alteração: 15 de Jul de 2021
"""

import socket
import sys
import _thread
import datetime
import os

HEADERSIZE = 10
LOCAL_SERVER = ''
BYTEORDER = 'big'


def send_data(clientsocket, message):
    """Este método é responsavel por codifiar a mensagem para o padrão de protocolo utilizado e
    enviar para o servidor.

    :param clientsocket: TCP Socket que a mensagem será enviada
    :param message: A mensagem que o cliente deseja enviar
    """

    response_message = len(message).to_bytes(1, BYTEORDER)
    response_message += bytes(message, 'utf-8')

    clientsocket.send(response_message)


def on_new_client(clientsocket, addr):
    """Este método irá receber uma requisição, processa-la de acordo com o código da requisição
    e enviar uma resposta para o cliente que fez a requisição.


    :param clientsocket: Socket TCP que o servidor deve ficar escutando a requisição e enviar a resposta
    :param addr: Endereço do cliente
    """

    print(f'Connection established with {addr}')

    while True:
        full_msg = clientsocket.recv(1024)

        msg_len = int.from_bytes(full_msg[0:1], BYTEORDER)
        full_msg = full_msg[1:(msg_len + 1)].decode('utf-8')

        # do some checks and if msg == someWeirdSignal: break:
        print(f'{addr}, >> {full_msg}')
        response_message = ''

        if full_msg == 'EXIT':
            response_message = 'Ok! Tchau!'

        elif full_msg == 'TIME':
            current_time = datetime.datetime.now()
            response_message = f'{current_time.hour}:{current_time.minute}:{current_time.second}'

        elif full_msg == 'DATE':
            current_time = datetime.datetime.now()
            response_message = f'{current_time.day}/{current_time.month}/{current_time.year}'

        elif full_msg == 'FILES':
            files = os.listdir(LOCAL_SERVER)

            response_message = len(files).to_bytes(1, BYTEORDER)
            clientsocket.send(response_message)
            for index in range(len(files)):
                response_message = bytes(files[index], 'utf-8')
                clientsocket.send(response_message)

        elif full_msg.split()[0] == 'DOWN':
            if os.path.isfile(full_msg.split()[1]):
                clientsocket.send(os.path.getsize(full_msg.split()[1]).to_bytes(4, BYTEORDER))
                with open(full_msg.split()[1], 'rb') as file_to_send:
                    for data in file_to_send:
                        clientsocket.send(data)
            else:
                response_message = (0).to_bytes(4, BYTEORDER)
                clientsocket.send(response_message)
        else:
            response_message += ''

        if full_msg.split()[0] != 'DOWN':
            if full_msg != 'FILES':
                send_data(clientsocket, response_message)

        if full_msg == 'EXIT':
            break

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

    # Escutando novas conexões
    sock.listen(5)

    # Criar uma thread para cada nova conexão
    while True:
        connection, client_address = sock.accept()
        _thread.start_new_thread(on_new_client, (connection, client_address))
