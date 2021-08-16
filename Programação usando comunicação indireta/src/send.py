import pika, sys, json, pandas as pd



if __name__ == '__main__':
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()


    channel.queue_declare(queue='tweetsList')

    data = pd.read_csv('../data/Tweets.csv').to_dict('records')

    for line in data:
        channel.basic_publish(exchange='', routing_key='tweetsList', body=json.dumps(line))

    connection.close()