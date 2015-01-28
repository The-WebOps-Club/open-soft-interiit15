from data.models import UserProfile
from django.contrib.auth.models import User
from annoying.functions import get_object_or_None
from django.shortcuts import get_object_or_404, HttpResponse, render_to_response, redirect, HttpResponseRedirect, render
import json
from django.views.decorators.csrf import csrf_exempt
import fetcher as fr
from django.core.servers.basehttp import FileWrapper
from django.conf import settings
@csrf_exempt
def create_user(request):
    data = request.POST
    try:
        user = get_object_or_None(User, email=data['email'])
        if user:
            return HttpResponse(json.dumps({'result':False,'msg':'User with same email already exist'}))
        else:
            user = User.objects.create_user(username=''.join(data['name'].split(' ')).lower(), email=data['email'], password=data['email'], first_name=data['name'])
            user.save()
            profile=UserProfile.objects.create(user=user,phone=data['phone'],occupation=data['occupation'],region=data['region'])
            profile.save()
            return HttpResponse(json.dumps({'result':True,'username':''.join(data['name'].split(' ')).lower(), 'id':user.pk}))
    except Exception,e:
        return HttpResponse(json.dumps({'result':False,'msg':e.message}))

@csrf_exempt
def get_data(request):
    data = request.GET
    if data['format']:
        sformat = data['format']
    filename = fr.fetch(data['keyword'],sformat)
    if filename:
        return HttpResponseRedirect(settings.MEDIA_URL+filename)
    else:
        return HttpResponse('Error')

def destroy_user(request):
    pass
