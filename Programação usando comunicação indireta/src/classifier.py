import pika, sys, os, json, pandas as pd


def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='tweetsList')

    def callback(ch, method, properties, body):
        tweet = json.loads(body)
        tweet_id = tweet['tweet_id']
        tweet_sentiment = tweet['airline_sentiment']
        

        routing_key = ''

        if tweet_sentiment == 'neutral':
            routing_key = 'neutral'

        elif tweet_sentiment == 'positive':
            routing_key = 'positive'

        elif tweet_sentiment == 'negative':
            routing_key = 'negative'

        channel.exchange_declare(exchange='direct_logs', exchange_type='direct')
        channel.basic_publish(exchange='direct_logs', routing_key=routing_key, body=body)
        
        print(" [x] Classified %r" % tweet_id)

    channel.basic_consume(queue='tweetsList', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages.')
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