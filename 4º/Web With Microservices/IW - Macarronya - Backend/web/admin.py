from django.contrib import admin
from .models import Product


class ProductAdmin(admin.ModelAdmin):
    model = Product
    extra = 1


admin.site.register(Product, ProductAdmin)
