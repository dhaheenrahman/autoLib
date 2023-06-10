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
                cursor.execute("SELECT admno,hash FROM student")
                rows = cursor.fetchall()
                print(rows[0][1],hash,rows[0][1]==hash)
                print(rows[0][0],admno,rows[0][0]==admno)
                if(rows[0][1]==hash and rows[0][0]==admno):
                    return JsonResponse({
                                'status': 'success',
                                'token': '12345689'
                            })
    return JsonResponse({
            'status': 'failed',
            'error':'invalid creds'
        })


def AddBook(request):
    if request.method == 'POST':
        data = json.loads(request.body)
        bookno = data.get('bookno')
        bookname = data.get('bookname')
        issuedate = data.get('issuedate')
        duedate = data.get('duedate')
        admno = data.get('admno')
        returndate=""


 
        if bookno and bookname and issuedate and duedate:
            with connection.cursor() as cursor:
                print("Select * from bookissue where adm_no = "+admno+" and ret_date < "+issuedate)
                cursor.execute("Select * from bookissue where adm_no = %s and ret_date < %s ",(admno,issuedate))
                if(len(cursor.fetchall())>0):
                    return JsonResponse({ 'status': 'failed','reason':"Book due present"})
                cursor.execute("INSERT INTO bookissue(book_no,book_name,issue_date,ret_date,adm_no) values(%s,%s,%s,%s,%s)",(bookno,bookname,issuedate,returndate,admno))
                rows = cursor.fetchall()
                return JsonResponse({ 'status': 'success',})
    return JsonResponse({   
            'status': 'failed',
            'error':'invalid request'
        })


def profile(request):
    if request.method == 'GET':
        admno = request.GET.get('admno')
        if admno:
            with connection.cursor() as cursor:
                cursor.execute("SELECT * FROM student WHERE admno = %s", (admno,))
                student = cursor.fetchone()

                if student:
                    # Extract the column values from the fetched row
                    student_details = {
                        'admno': student[0],
                        'name': student[1],
                        'dept': student[2],
                        'sem': student[3],
                        'phone': student[4],
                        'email': student[5],
                        'hash': student[6]
                    }

                    return JsonResponse({'status': 'success', 'student': student_details})
                else:
                    return JsonResponse({'status': 'failed', 'reason': 'Student not found'})
        else:
            return JsonResponse({'status': 'failed', 'error': 'Invalid request'})

    return JsonResponse({'status': 'failed', 'error': 'Invalid request'})
