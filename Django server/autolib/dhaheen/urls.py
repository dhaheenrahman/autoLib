
from django.urls import path

from .views import Login,AddBook,profile

urlpatterns = [
    path('login',Login),
    path('bookissue',AddBook),
    path('profile',profile)
]