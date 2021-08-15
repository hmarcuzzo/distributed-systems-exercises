import pika

if __name__ == '__main__':
    request_type = int(input('\nDeseja receber informações sobre Futebol (1), Vôlei (2) ou Sair (0): '))

    if request_type == 0:
        exit()

    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.exchange_declare(exchange='logs', type='fanout')

    result = channel.queue_declare(exclusive=True)
    queue_name = result.method.queue

    channel.queue_bind(exchange='logs', queue=queue_name)

    print('[*] Waiting for logs. To exit press CTRL+C')

    def callback(ch, method, properties, body):
        print('[x] %r' % body)

    channel.basic_consume(callback, queue=queue_name, no_ack=True)
    channel.start_consuming()