"""Este código faz upload de arquivos atráves de uma conexão UDP com o servidor.

Protocolo:
O protocolo utilizado para o envio de arquivos nesta atividade foi:

- 1a menssagem:
        1 byte            0 - 255 bytes        4 bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
|   Filename Size   |      Filename      |    File Size    |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-

- 2a mensagem (repetível):
     1 byte
+-+-+-+-+-+-+-+-+
|   File byte   |
+-+-+-+-+-+-+-+-+

Obs: Esta mensagem se repete até que todo arquivo seja enviado

- 3a menssagem:
     1 - 4096 bytes
+-+-+-+-+-+-+-+-+-+-
|     Checksum     |
+-+-+-+-+-+-+-+-+-+-

- 4a menssagem (recebe):
         1 - 4096 bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     Server Response     |
+-+-+-+-+-+-+-+-+-+-+-+-+-+

Obs: O cliente aguarda a resposta do servidor sobre o envio do arquivo, se foi bem sucedido ou não.

Autores:
    @hmarcuzzo (Henrique Marcuzzo)

Data de Criação: 16 de Jul de 2021
Ultima alteração: 16 de Jul de 2021
"""

import socket
import os
import hashlib
import time


# GLOBAL VARIABLES

bufferSize = 4096
serverAddressPort = ("127.0.0.1", 20001)
LOCAL_CLIENT = ''
BYTEORDER = 'big'


if __name__ == '__main__':
    LOCAL_CLIENT = os.getcwd()

    # Criando o socket de datagrama UDP
    UDPClientSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

    while True:
        request_message = input('CLIENT >> ')

        if request_message.find('SENDFILE') != -1:
            filename = request_message.split()[1]
            len_filename = len(filename)

            if os.path.isfile(filename):
                request_message = len_filename.to_bytes(1, BYTEORDER)
                request_message += bytes(filename, 'utf-8')

                file_size = os.path.getsize(filename)
                request_message += file_size.to_bytes(4, BYTEORDER)

                # Send to server using created UDP socket
                UDPClientSocket.sendto(request_message, serverAddressPort)

                with open(filename, 'rb') as file_to_send:
                    for data in file_to_send:
                        request_message = data
                        UDPClientSocket.sendto(request_message, serverAddressPort)
                        print('dormi')
                        time.sleep(0.1)

                # Criando a hash MD5 do arquivo enviado para o checksum
                md5_hash = hashlib.md5()

                a_file = open(filename, "rb")
                content = a_file.read()
                md5_hash.update(content)

                checksum_sended = md5_hash.hexdigest()
                UDPClientSocket.sendto(bytes(checksum_sended, 'utf-8'), serverAddressPort)

                msgFromServer = UDPClientSocket.recvfrom(bufferSize)[0].decode('utf-8')
                print('SERVER >> {}'.format(msgFromServer))

            else:
                print('Arquivo inexistente!')
        else:
            print('Comando não encontrado')
