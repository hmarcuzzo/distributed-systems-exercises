"""Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
em formato Protocol Buffer ou JSON, via socket TCP.

Protocolo de envio:
    1a Mensagem: Tipo de representação dos dados (protobuf ou json).
    2a Mensagem: Tipo de requisição (inserção, deletar ou consultar).
    3a Mensagem: Tamanho da mensagem.
    4a Mensagem: Mensagem codificada com o tipo de protocolo escolhido.

Autores:
    @hmarcuzzo (Henrique Marcuzzo)
    @sorattorafa (Rafael Soratto)

Data de Criação: 25 de Jul de 2021
Ultima alteração: 25 de Jul de 2021
"""


import socket
import database_pb2
import pickle

# VARIÁVEIS GLOBAIS
PROTOCOL_MESSAGE = 'protobuf'

 
def get_student_RA():
    """Este método irá coletar o RA do aluno.

    :return (int) RA do aluno.
    """

    return int(input('Digite o RA do aluno: '))


def get_discipline_code():
    """Este método irá coletar o CÓDIGO da disciplina.

    :return (str) CÓDIGO da disciplina.
    """

    return input('Digite o CÓDIGO da disciplina: ')


def get_discipline_year():
    """Este método irá coletar o ANO da disciplina.

    :return (int) ANO da disciplina.
    """

    return int(input('Digite o ANO da disciplina: '))


def get_discipline_semester():
    """Este método irá coletar o SEMESTRE da disciplina.

    :return (int) SEMESTRE da disciplina.
    """

    return int(input('Digite o SEMESTRE da disciplina: '))


def get_student_grade():
    """Este método irá coletar a NOTA do aluno.

    :return (float) NOTA do aluno.
    """

    return float(input('Digite a NOTA do aluno: '))


def get_student_absences():
    """Este método irá coletar o NÚMERO de faltas do aluno.

    :return (int) NÚMERO de faltas do aluno.
    """

    return int(input('Digite o NÚMERO de faltas do aluno: '))


def get_insert_data():
    """Este método irá coletar os dados para inserir uma nova nota para o aluno.

    :return (list) Os dados da matrícula necessários para inserção.
    """
    data = []

    data.append(get_student_RA())
    data.append(get_discipline_code())
    data.append(get_discipline_year())
    data.append(get_discipline_semester())
    data.append(get_student_grade())
    data.append(get_student_absences())

    return data


def get_delete_data():
    """Este método irá coletar os dados para remover as notas do aluno de um determinando ano e semestre.

    :return (list) Os dados da matrícula necessários para remoção.
    """
    data = []

    data.append(get_student_RA())
    data.append(get_discipline_code())
    data.append(get_discipline_year())
    data.append(get_discipline_semester())
    data.append(-1.0)
    data.append(-1)

    return data


def get_consult_data():
    """Este método irá coletar os dados para consultar um determinado aluno.

    :return (list) Os dados da matrícula necessários para consulta.
    """
    data = []

    data.append(get_student_RA())
    data.append(get_discipline_code())
    data.append(get_discipline_year())
    data.append(get_discipline_semester())
    data.append(-1.0)
    data.append(-1)

    return data


def generate_protobuf_message(data):
    """Este método irá gerar os dados para serem enviados no formato Protocol Buffer.

    :param data: (list) Lista com os dados para gerar a string.
    :return (byte[]) Mensagem em bytes a ser enviada no formato Protocol Buffer.
    """
    registration = database_pb2.Matricula()

    registration.RA = data[0]
    registration.cod_disciplina = data[1]
    registration.ano = data[2]
    registration.semestre = data[3]
    registration.nota = data[4]
    registration.faltas = data[5]

    return registration.SerializeToString()


def generate_json_message(data):
    """Este método irá gerar os dados para serem enviados no formato JSON.

    :param data: (list) Lista com os dados para gerar a string.
    :return (byte[]) Mensagem em bytes a ser enviada no formato Protocol Buffer.
    """
    json_data = {
        'RA': data[0],
        'cod_disciplina': data[1],
        'ano': data[2],
        'semestre': data[3],
        'nota': data[4],
        'faltas': data[5]
    }

    return pickle.dumps(json_data)


def send_request(client_socket, request_type, message):
    """Este método irá enviar a mensagem para o servidor.

    :param client_socket: Socket TCP para o destino que o cliente deve enviar a mensagem.
    :param request_type: (int) Tipo da mensagem Inserção, Remoção, Consultar, Sair.
    :param message: (byte[]) Mensagem em um array de bytes para ser enviado.
    """
    global PROTOCOL_MESSAGE

    client_socket.send((PROTOCOL_MESSAGE + '\n').encode())
    client_socket.send((str(request_type) + '\n').encode())
    client_socket.send((str(len(message)) + '\n').encode())
    client_socket.send(message)


if __name__ == '__main__':
    selected_protocol = input('Digite o tipo de protocolo desejado - "protobuf" (1) ou "json" (2): ')
    # Se o usuário digital algo diferente de "json" o protocolo padrão de comunicação será o "protobuf"
    if selected_protocol == 'json' or int(selected_protocol) == 2:
        PROTOCOL_MESSAGE = 'json'

    # Criando o socket TCP/IP
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Conecta o socket a porta onde o servidor está escutando
    server_address = ['localhost', 7000]
    # server_address[0] = input('IP adress: ')
    # server_address[1] = int(input('Port: '))
    client_socket.connect(tuple(server_address))

    # Loop principal onde irá ocorrer a troca de mensagens entre o cliente e o servidor.
    while True:
        request_type = int(input('\nDeseja fazer a Inserção (1), Remoção (2), Consultar (3) ou Sair (0): '))
        while request_type > 4 or request_type < 0:
            print("Não conheço este tipo de requisição.\n")
            request_type = int(input('Deseja fazer a Inserção (1), Remoção (2), Consultar (3) ou Sair (0): '))

        if request_type == 0:
            send_request(client_socket, request_type, "".encode())
            break

        # Pegar os dados necessários e trata-los de acordo com o tipo de requisição.
        print('\n--')
        if request_type == 1:
            data = get_insert_data()
        elif request_type == 2:
            data = get_delete_data()
        else:
            data = get_consult_data()
        print('--\n')

        if PROTOCOL_MESSAGE == 'protobuf':
            message = generate_protobuf_message(data)
        else:
            message = generate_json_message(data)
        
        message_size = len(message)

        # Protocolo de envio da mensagem
        send_request(client_socket, request_type, message)

        # TODO: Protocolo de resposta do servidor

    client_socket.close()