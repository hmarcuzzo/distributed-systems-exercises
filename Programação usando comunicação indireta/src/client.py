import pika, sys, os, json

from datetime import datetime


def main():
    request_type = int(input('\nDeseja receber informações de tweets de sentimentos Neutro (1), Positivo (2) ou Negativo (3): '))
    while request_type > 3 or request_type < 1:
            print("Não conheço este tipo de requisição.\n")
            request_type = int(input('\nDeseja receber informações de tweets de sentimentos Neutro (1), Positivo (2) ou Negativo (3): '))

    if request_type == 1:
        name_queue = 'neutral'
    elif request_type == 2:
        name_queue = 'positive'
    elif request_type == 3:
        name_queue = 'negative'
    filename = name_queue + '.log'

    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.exchange_declare(exchange='direct_logs', exchange_type='direct')

    result = channel.queue_declare(queue='', exclusive=False)
    queue_name = result.method.queue

    channel.queue_bind(exchange='direct_logs', queue=queue_name, routing_key=name_queue)

    print(' [*] Esperando menssagens. Para sair pressione CTRL+C')
    # print(f' [*] Todas as menssagens estão sendo direcionadas para o arquivo: {filename}')

    def callback(ch, method, properties, body):
        data = json.loads(body)
        message = f'------------------\n' \
            + f'{datetime.now().strftime("%Y-%m-%d %H:%M:%S")}\n' \
            + f'\tAirlane: {data["airline"]}\n' \
            + f'\tName: {data["name"]}\n' \
            + f'\tSentiment: {data["airline_sentiment"]}\n' \
            + f'\tRetweets: {data["retweet_count"]}\n' \
            + f'\tText: {data["text"]}\n' \
            + f'\tCreated at: {data["tweet_created"]}\n' \
            + '------------------\n'

        print(message) 

        # with open(filename, "a") as file:
        #     file.write(message)

    channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

    channel.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)