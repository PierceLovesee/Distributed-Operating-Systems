# example of "consuming an API"
# This is the api for Stack Exchange.  Simple example of showing what can be done with an api

import requests
import json

response = requests.get('https://api.stackexchange.com/2.3/questions?order=desc&sort=activity&site=stackoverflow')

for data in response.json()['items']:
    if data['answer_count'] == 0:
        print(data['title'])
        print(data['link'])
    else:
        print("skipped")
    print()
