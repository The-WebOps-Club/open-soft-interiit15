import global_settings

from global_settings import *

CORS_ORIGIN_ALLOW_ALL = True

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': DATA_ROOT+'/'+'rcdn.db',
    }
}