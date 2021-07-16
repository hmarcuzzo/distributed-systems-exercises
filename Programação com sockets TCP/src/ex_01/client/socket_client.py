"""Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio e o
recebimento da mensagem, via socket TCP, de acordo com o código do comando enviado/recebido.

Protocolo:
       1 byte        [0 a 255] Bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|  Message size |       Message           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


* Se for um Download o message size passa a ser de 4 bytes e o message de 1 a 2^32 bytes (message é recebido byte a byte)
* Se for o comando FILE a primeira mensagem recebida é apenas com a quantidade de arquivos no Message size e após isso
    os nomes dos arquivos são recebidos individualmente.


Autores:
    @hmarcuzzo (Henrique Marcuzzo)

Data de Criação: 28 de Jun de 2021
Ultima alteração: 15 de Jul de 2021
"""

import socket
import sys
import os

LOCAL_CLIENT = ''
BYTEORDER = 'big'

def send_data(clientsocket, message):
    """Este método é responsavel por codifiar a mensagem para o padrão de protocolo utilizado e
    enviar para o servidor.

    :param clientsocket: TCP Socket que a mensagem será enviada
    :param message: A mensagem que o cliente deseja enviar
    """

    request_message = len(message).to_bytes(1, BYTEORDER)
    request_message += bytes(message, 'utf-8')

    clientsocket.send(request_message)


if __name__ == '__main__':

    LOCAL_CLIENT = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Conecta o socket a porta onde o servidor está escutando
    server_address = ['localhost', 10100]
    # server_address[0] = input('IP adress: ')
    # server_address[1] = int(input('Port: '))
    print(f'connecting to {server_address[0]} port {server_address[1]}')
    sock.connect(tuple(server_address))

    full_msg = ''
    msg_len = 0
    prefix = '~'

    # Loop principal onde irá ocorrer a troca de mensagens entre o cliente e o servidor
    while True:
        message = input(f'{prefix} ')

        send_data(sock, message)

        full_msg = sock.recv(1024)

        if message.split()[0] != 'DOWN':
            msg_len = int.from_bytes(full_msg[0:1], BYTEORDER)
            full_msg = full_msg[1:(msg_len + 1)].decode('utf-8')
        else:
            msg_len = int.from_bytes(full_msg[0:4], BYTEORDER)

        if message.split()[0] == 'FILES':
            for i in range(msg_len):
                file_message = sock.recv(1024).decode('utf-8')
                print(file_message)

        elif message.split()[0] == 'DOWN' and msg_len > 0:
            with open(os.path.join(LOCAL_CLIENT, message.split()[1]), 'wb') as file_to_write:
                index = 0
                while index < msg_len:
                    res = sock.recv(1024)
                    index += len(res)
                    file_to_write.write(res)

                file_to_write.close()

            print(f'SERVER >> Full download in {LOCAL_CLIENT}/{message.split()[1]}')
        elif msg_len <= 0:
            print(f'SERVER >> Arquivo ou comando inexistente!')
        else:
            print(f'SERVER >> {full_msg}')

        if message == 'EXIT':
            break

    print(f'closing socket')
    sock.close()