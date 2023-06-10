
from django.urls import re_path
from dhaheen import consumers

# URLs that handle the WebSocket connection are placed here.
websocket_urlpatterns=[
                    re_path(
                        r"ws/chat/", consumers.ChatRoomConsumer.as_asgi()
                    ),
                ]

