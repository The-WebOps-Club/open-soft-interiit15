from django.db import models
from django.contrib.auth.models import User

#class Page(models.Model):
#    title = 
class UserProfile(models.Model):
	user = models.OneToOneField(User)
	def __unicode__(self):
		return "%s's profile" % self.user.get_full_name
	phone = models.CharField(max_length=10, blank=True, null=True)
	email = models.CharField(max_length=10, blank=True, null=True)
	occupation = models.CharField(max_length=10, blank=True, null=True)
	region = models.CharField(max_length=10, blank=True, null=True)
	priority_list = models.CharField(max_length=10, blank=True, null=True)
	created_time = models.CharField(max_length=10, blank=True, null=True)
	last_synced = models.CharField(max_length=10, blank=True, null=True)
	data_size = models.IntegerField(blank=True, null=True)

class Content(models.Model):
	local_id = models.IntegerField(blank=True, null=True)
	server_id = models.IntegerField(blank=True, null=True)
	title = models.CharField(max_length=10, blank=True, null=True)
	category = models.CharField(max_length=10, blank=True, null=True)
	source = models.CharField(max_length=10, blank=True, null=True)
	text = models.CharField(max_length=10, blank=True, null=True)
	image_path = models.CharField(max_length=10, blank=True, null=True)
	local_timestamp = models.CharField(max_length=10, blank=True, null=True)
	server_timestamp = models.CharField(max_length=10, blank=True, null=True)