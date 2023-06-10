import json
from channels.generic.websocket import AsyncWebsocketConsumer

class ChatRoomConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.accept()
    async def disconnect(self, close_code):
        pass
    async def receive(self, text_data):
        # message = text_data_json["message"]
        # username = text_data_json["username"]
        print("Data recived ")
        print(text_data)
        await self.send(text_data=json.dumps({'message': 'ok bye',}))
  