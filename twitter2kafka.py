#!/usr/bin/env python
# ________         _______________            ____________                               
# ___  __/__      ____(_)_  /__  /______________  ___/_  /__________________ _______ ___ 
# __  /  __ | /| / /_  /_  __/  __/  _ \_  ___/____ \_  __/_  ___/  _ \  __ `/_  __ `__ \
# _  /   __ |/ |/ /_  / / /_ / /_ /  __/  /   ____/ // /_ _  /   /  __/ /_/ /_  / / / / /
# /_/    ____/|__/ /_/  \__/ \__/ \___//_/    /____/ \__/ /_/    \___/\__,_/ /_/ /_/ /_/ 
#                                                                                       

import tweepy
import threading, logging, time
from kafka.consumer import SimpleConsumer
from kafka import SimpleProducer, KafkaClient
import string

######################################################################
# Authentication details. To  obtain these visit dev.twitter.com
######################################################################

consumer_key = '6DziMXM3V4kBy4Wfb3UOPGUCa'
consumer_secret = 'fdG9iSewXj1GgDeGRtkJLIctn2mobSLYF6zcDT5x2bth5d6TCx'
access_token = '1068725924-WBigfGfCp5YVXpGENho6WVbKyFyr8cJUnDjf4s2'
access_token_secret = 'isM3ualk8ryQ12TpkpbFg1xsr9ZmUTBNiOTKUltiPly4L'

mytopic='rawtweets'
kafka = KafkaClient("localhost:9092")
producer = SimpleProducer(kafka)

######################################################################
#Create a handler for the streaming data that stays open...
######################################################################

class StdOutListener(tweepy.StreamListener):

    #Handler
    ''' Handles data received from the stream. '''

    ######################################################################
    #For each status event
    ######################################################################

    def on_status(self, status):
        

        try:
            # Prints the text of the tweet
            print('%s,%s,%s' % ( status.user.id_str, status.user.screen_name,status.text))

            # Schema changed to add the tweet text
            topic = 'test'
            msg = 'Hello World'

            message =  status.text + ',' + status.user.screen_name
            msg = filter(lambda x: x in string.printable, message)
            print('%s -> %s' % ( status.user.screen_name,  message))


            finalmsg =bytes(message, 'utf-8')
            producer.send_messages(topic,finalmsg)
           # producer.send(mytopic, status.user.screen_name,str(message))
        except Exception  as e:
            print( e)
            return True
        
        return True
       
    ######################################################################
    #Supress Failure to keep demo running... In a production situation 
    #Handle with seperate handler
    ######################################################################
 
    def on_error(self, status_code):

        print('Got an error with status code: ' + str(status_code))
        return True # To continue listening
 
    def on_timeout(self):

        print('Timeout...')
        return True # To continue listening

######################################################################
#Main Loop Init
######################################################################


if __name__ == '__main__':
    
    listener = StdOutListener()

    #sign oath cert

    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

    auth.set_access_token(access_token, access_token_secret)

    #uncomment to use api in stream for data send/retrieve algorythms 
    #api = tweepy.API(auth)

    stream = tweepy.Stream(auth, listener)

    stream.filter(track=['@LSEplc', '@SHARESmag‏', '@WarrenBuffett', '@alphatrends‏', '@FousAlerts‏', '@DanZanger‏', '@traderstewie‏', '@harmongreg‏', '@WSJmarkets‏', '@The_Real_Fly‏',
                         '@EIAgov', '@Schuldensuehner', '@paulkrugman', '@AswathDamodaran', '@ukarlewitz', '@lindayueh', '@JStanleyFX', '@JMahony_IG', '@IGTV', '@IGClientHelp',
                         '@IGSquawk‏', '@IGcom', '@OANDAbusiness', '@OANDAlerts', '@OANDA', '@markets', '@FTAlphaville', '@FT', '@coindesk', '@Reuters',
                         '@SkyNewsBiz', '@BBCBusiness', '@ChrisB_IG', '@LiveSquawk', '@DailyFXTeam', '@CNBC', '@economics', '@RANsquawk', '@business', '@CityAM',
                         '@ReutersBiz', '@ForexLive', '@nikolaslippmann'])

    


    
    stream.sample()

    
  
