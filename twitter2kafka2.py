from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
from kafka import SimpleProducer, KafkaClient


consumer_key = '6DziMXM3V4kBy4Wfb3UOPGUCa'
consumer_secret = 'fdG9iSewXj1GgDeGRtkJLIctn2mobSLYF6zcDT5x2bth5d6TCx'
access_token = '1068725924-WBigfGfCp5YVXpGENho6WVbKyFyr8cJUnDjf4s2'
access_token_secret = 'isM3ualk8ryQ12TpkpbFg1xsr9ZmUTBNiOTKUltiPly4L'


class StdOutListener(StreamListener):
    def on_data(self, data):
        producer.send_messages("trump", data.encode('utf-8'))
        print (data)
        return True
    def on_error(self, status):
        print (status)

kafka = KafkaClient("localhost:9092")
producer = SimpleProducer(kafka)
l = StdOutListener()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
stream = Stream(auth, l)
stream.filter(track="trump")