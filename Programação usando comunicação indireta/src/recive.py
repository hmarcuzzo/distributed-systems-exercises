import pika, sys, os
import pandas as pd
import json

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='tweetsList')

    def callback(ch, method, properties, body):
        print(" [x] Received %r" % json.loads(body)['tweet_id'])

    channel.basic_consume(queue='tweetsList', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages.')
    channel.start_consuming()

if __name__ == '__main__':
    main()