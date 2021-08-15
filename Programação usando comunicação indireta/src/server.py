from MessageService import MessageService

if __name__ == '__main__':
    message = MessageService(filename='../data/Tweets.csv')

    message.start_notification()
