from django.shortcuts import render
from django.http import HttpResponse
from django.db import connection
import json
from django.http import JsonResponse

def Login(request):
    if request.method == 'POST':
        data = json.loads(request.body)
        admno = data.get('admno')
        hash= data.get('password')
        if admno and hash:
            with connection.cursor() as cursor:
                cursor.execute("SELECT admno,hash FROM users")
                rows = cursor.fetchall()
                print(rows[0][1],hash,rows[0][1]==hash)
                print(rows[0][0],admno,rows[0][0]==int(admno))
                if(rows[0][1]==hash and rows[0][0]==int(admno)):
                    return JsonResponse({   
                                'status': 'success',
                                'token': '12345689'
                            })
    return JsonResponse({   
            'status': 'failed',
            'error':'invalid creds'
        })

