from rcdn_server.data.models import UserProfile
from django.contrib.auth.models import User


def create_user(request):
    data = request.POST
    user = User.objects.create_user
    serialized = UserSerializer(data = request.DATA)
    # if serialized.is_valid():
        # user = get_object_or_None(User, username=serialized.init_data['email'])
    if user:
        return Response({
            "user": ["User already exists"]
        }, status=status.HTTP_400_BAD_REQUEST)
    else:
        user = User.objects.create_user(
            serialized.init_data['email'],
            serialized.init_data['email'],
            serialized.init_data['password']
        )
        user.first_name = serialized.init_data['first_name']
        user.last_name = serialized.init_data['last_name']
        user.is_active = True
        user.save()
        token = Token.objects.get_or_create(user=user)[0]
        user = authenticate(username=serialized.init_data['email'], password=serialized.init_data['password'])
        login(request, user)
        data = serialized.data
        data['token'] = token.key
        data['user_id'] = user.id
        return Response(data, status=status.HTTP_201_CREATED)
    # else:
    #     return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)
    # pass
def update_user(request):
    pass
def destroy_user(request):
    pass