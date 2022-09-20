from django.contrib.auth.models import AbstractUser
from django.db import models


class BaseUser(AbstractUser):
    name = models.CharField(max_length=10)


class Student(BaseUser):
    pass
