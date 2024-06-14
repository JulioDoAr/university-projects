from django.db import models
from django.contrib.auth.models import User


class Product(models.Model):
    userId = models.ForeignKey(User, on_delete=models.CASCADE)
    name = models.CharField(max_length=100)
    description = models.CharField(max_length=200)
    price = models.FloatField(default=0)
    status = models.IntegerField(default=0)
    # 0=OnSale, 1=Selled
    image = models.ImageField(upload_to='images', null=True, blank=True)

    def __str__(self):
        return self.name
