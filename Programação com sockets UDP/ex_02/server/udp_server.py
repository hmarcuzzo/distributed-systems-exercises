"""Este código é o servidor que recebe arquivos de um conexão UDP e checa se o arquivo veio corretamente atráves de um
checksum MD5.

Protocolo:
O protocolo utilizado para o recebimento de arquivos nesta atividade foi:

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

Obs: Esta mensagem se repete até que todo arquivo seja recebido.

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

Obs: O servidor envia uma a resposta para o cliente sobre o envio do arquivo, se foi bem sucedido ou não.

Autores:
    @hmarcuzzo (Henrique Marcuzzo)

Data de Criação: 16 de Jul de 2021
Ultima alteração: 16 de Jul de 2021
"""


import os
import socket
import hashlib


# GLOBAL VARIABLES
bufferSize = 4096
serverAddressPort = ("127.0.0.1", 20001)
LOCAL_SERVER = ''
BYTEORDER = 'big'


if __name__ == '__main__':
    LOCAL_SERVER = os.getcwd()
    # Criando o socket de datagrama UDP
    UDPServerSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

    # Bind to address and ip
    UDPServerSocket.bind(serverAddressPort)

    # Listen for incoming datagrams
    print("UDP server up and listening")

    while (True):
        bytesAddressPair = UDPServerSocket.recvfrom(bufferSize)

        request_message = bytesAddressPair[0]
        address = bytesAddressPair[1]

        i_index = 0
        f_index = i_index + 1
        len_filename = int.from_bytes(request_message[i_index:f_index], BYTEORDER)

        i_index = 1
        f_index = i_index + len_filename
        filename = request_message[i_index:f_index]

        i_index = f_index
        f_index = i_index + 4
        file_size = int.from_bytes(request_message[i_index:f_index], BYTEORDER)

        bytes_received = 0
        with open(os.path.join(LOCAL_SERVER, filename.decode('utf-8')), 'wb') as file_to_write:
            while True:
                if bytes_received >= file_size:
                    break

                request_message = UDPServerSocket.recvfrom(bufferSize)[0]

                data_write = request_message
                bytes_received += len(data_write)

                file_to_write.write(data_write)

        # Criando a hash MD5 do arquivo recebido para o checksum
        md5_hash = hashlib.md5()

        a_file = open(filename.decode('utf-8'), "rb")
        content = a_file.read()
        md5_hash.update(content)

        checksum_recived = md5_hash.hexdigest()
        checksum_sended = UDPServerSocket.recvfrom(bufferSize)[0].decode('utf-8')

        if checksum_recived == checksum_sended:
            response_message = 'Arquivo recebido com sucesso!'
            UDPServerSocket.sendto(bytes(response_message, 'utf-8'), address)
        else:
            response_message = 'Checksum incoerente. Dados foram perdido no caminho!'
            UDPServerSocket.sendto(bytes(response_message, 'utf-8'), address)
