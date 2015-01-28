from django.contrib import admin

from data.models import UserProfile, Page

admin.site.register(Page)
admin.site.register(UserProfile)
