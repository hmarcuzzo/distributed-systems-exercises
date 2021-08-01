"""Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
utilizando a ferramenta gRPC e o recebimento da mensagem no mesmo formato.

Autores:
    @hmarcuzzo (Henrique Marcuzzo)
    @sorattorafa (Rafael Soratto)

Data de Criação: 1 de Ago de 2021
Ultima alteração: 1 de Ago de 2021
"""

import grpc
import database_pb2
import database_pb2_grpc

 
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

    data.append(0)
    data.append(get_discipline_code())
    data.append(get_discipline_year())
    data.append(get_discipline_semester())
    data.append(-1.0)
    data.append(-1)

    return data


def generate_gRPC_message(data, request_type):
    """Este método irá gerar os dados para serem enviados no formato gRPC.

    :param data: (list) Lista com os dados para gerar a string.
    :param request_type: (str) Tipo de requisição.
    :return (database_pb2.Request) Objeto database_pb2.Request.
    """
    type_request = ''
    if request_type == 1:
        type_request = 'addnota'
    elif request_type == 2:
        type_request = 'removenota'
    else:
        type_request = 'liststudents'

    return database_pb2.Request(opCode=type_request, RA=data[0], cod_disciplina=data[1], 
        ano=data[2], semestre=data[3], nota=data[4], faltas=data[5])


def send_gRPC_message(stub, message):
    """Este método irá enviar os dados para o servidor.

    :param stub: (database_pb2_grpc.DatabaseStub) Stub.
    :param message: (database_pb2.Request) Objeto database_pb2.Request.
    :return (database_pb2.Response) Objeto database_pb2.Response.
    """
    
    return stub.Comunication(message)


def show_response(response, request_type):
    """Este método irá imprimir na tela a resposta.

    :param response: (database_pb2.Response) Objeto database_pb2.Response.
    :param request_type: (int) Tipo de requisição.
    """

    print('SERVIDOR:')
    if (response.response == '1'):
        if request_type == 1 or request_type == 2:
            print('--\nRequisição feita com sucesso!\n--')
        elif request_type == 3:
            for matricula in response.matriculas:
                print(f'--\nRA: {matricula.RA}')
                print(f'Código: {matricula.cod_disciplina}')
                print(f'Ano: {matricula.ano}')
                print(f'Semestre: {matricula.semestre}')
                print(f'Nota: {round(matricula.nota, 2)}')
                print(f'Faltas: {matricula.faltas}\n--')
    else:
        print('---\n' + response.response + '\n---')
    

if __name__ == '__main__':
    # Conecta o socket a porta onde o servidor está escutando
    server_address = ['localhost', 7777]
    # server_address[0] = input('IP adress: ')
    # server_address[1] = int(input('Port: '))
    #configura o canal de comunicacao
    channel = grpc.insecure_channel(f'{server_address[0]}:{server_address[1]}')

    # Loop principal onde irá ocorrer a troca de mensagens entre o cliente e o servidor.
    while True:
        request_type = int(input('\nDeseja fazer a Inserção (1), Remoção (2), Consultar (3) ou Sair (0): '))
        while request_type > 4 or request_type < 0:
            print("Não conheço este tipo de requisição.\n")
            request_type = int(input('Deseja fazer a Inserção (1), Remoção (2), Consultar (3) ou Sair (0): '))

        # Pegar os dados necessários e trata-los de acordo com o tipo de requisição.
        print('\nCLIENTE:\n--')
        if request_type == 1:
            data = get_insert_data()
        elif request_type == 2:
            data = get_delete_data()
        else:
            data = get_consult_data()
        print('--\n')

        #inicializa e configura o stub
        stub = database_pb2_grpc.RequestMiddlewareStub(channel)

        message = generate_gRPC_message(data, request_type)

        response = send_gRPC_message(stub, message)

        show_response(response, request_type)

        if request_type == 0:
            break
