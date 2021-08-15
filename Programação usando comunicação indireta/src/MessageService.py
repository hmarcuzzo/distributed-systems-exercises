import pandas as pd

from multiprocessing import Process
from waiting import wait

    
class MessageService():
    def __init__(self, filename):
        self.data = pd.read_csv(filename)
        self.classify_queue = []
        self.buffer = []

    
    def buffer_ready(self):
        if len(self.buffer) < 100:
            return True
        return False

    
    def start_notification(self):
        Process(target=self.insert_in_queue()).start()
        Process(target=self.consume_queue()).start()
        

    def consume_queue(self):
        while True:
            print("Consuming")
            if len(self.buffer) >= 99:
                self.buffer.pop(0)

    
    def insert_in_queue(self):
        for index, row in enumerate(self.data.iterrows()):
            self.buffer.append(row)
            print(index)
            wait(lambda: self.buffer_ready(), timeout_seconds=120, waiting_for="Waiting until buffer be ready...")


    def classify(self):
        pass