''' A model field to store and retrieve Google Protocol Buffer objects easily.

Uses the BlobField available on GAE for storage.

Usage:

    myfield = ProtobufField(protoclass=MyProtocolClass)

where MyProtocolClass is a protocol descriptor class generated from a .proto file.
The field is supposed to store only the given kind of protocol messages.

The `protoclass` attribute is required.
'''
from django.db import models

import wikipage_pb2

class Page(models.Model):
    timestamp   = models.DateTimeField(auto_now_add = True)
    title       = models.CharField(max_length=100)
    filename    = models.FileField()

from django.contrib.auth.models import User

class UserProfile(models.Model):
    user            = models.OneToOneField(User)
    phone           = models.CharField(max_length=10, blank=True, null=True)
    occupation      = models.CharField(max_length=10, blank=True, null=True)
    region          = models.CharField(max_length=10, blank=True, null=True)
    priority_list   = models.CharField(max_length=10, blank=True, null=True)
    last_synced     = models.DateTimeField(blank=True, null=True)
    data_size       = models.IntegerField(blank=True, null=True)

    def __unicode__(self):
        return self.user.get_full_name()
