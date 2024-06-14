from django.urls import path
from . import views
from .view import product

app_name = 'web'
urlpatterns = [
    path('', views.index, name='index'),

    # Register
    path('register/', views.register, name='register'),
    path('profile/', views.profile, name='profile'),

    # Products
    path('products/create', product.create, name='product_create'),
    path('products/<int:id>', product.details, name='product_details'),
    path('products/<int:id>/update', product.update, name='product_update'),
    path('products/<int:id>/delete', product.delete, name='product_delete'),
    path('products/<int:id>/buy', product.buy, name='product_buy'),

    # Search
    path('search/', views.search, name='search')
]
