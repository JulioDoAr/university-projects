from django.shortcuts import get_object_or_404, render, redirect
from django.http import HttpResponse, HttpRequest
from django.contrib import messages
from django.contrib.auth.decorators import login_required
import os
from web.forms import ProductForm
from web.models import Product


@login_required
def create(request: HttpRequest) -> HttpResponse:
    context = {}
    form = ProductForm(request.POST or None)

    if form.is_valid():
        obj = form.save(commit=False)
        obj.userId = request.user
        if (request.FILES.keys() >= {"image"}):
            obj.image = request.FILES['image']
        obj.status = 0
        obj.save()
        messages.success(request, "Product created correctly.")
        return redirect("web:index")

    context['form'] = form
    return render(request, 'web/product/create.html', context)


def list(request: HttpRequest):
    context = {}
    context["dataset"] = Product.objects.all()
    return render(request, "web/product/search.html", context)


def details(request: HttpRequest, id):
    context = {}
    context["product"] = Product.objects.get(id=id)
    return render(request, "web/product/details.html", context)


@login_required
def update(request: HttpRequest, id):
    context = {}
    obj = get_object_or_404(Product, id=id)
    form = ProductForm(request.POST or None, instance=obj)

    if (request.user != obj.userId):
        messages.error(request, "You are not the owner of that product.")
        return redirect("web:index")

    if form.is_valid():
        obj = form.save(commit=False)
        obj.userId = request.user
        obj.save()
        return redirect("web:product_details", id=id)

    context["form"] = form
    return render(request, "web/product/update.html", context)


@login_required
def delete(request, id):
    context = {}
    obj = get_object_or_404(Product, id=id)

    if (request.user != obj.userId):
        messages.error(request, "You are not the owner of that product.")
        return redirect("web:index")

    if request.method == "POST":
        obj.delete()
        if (obj.image):
            os.remove(obj.image.path)
        return redirect("web:index")

    return render(request, "web/product/delete.html", context)


@login_required
def buy(request, id):
    obj = get_object_or_404(Product, id=id)
    if (obj.status == 1):
        messages.error(request, "Product already sold.")
        return redirect("web:index")

    obj.status = 1
    obj.save()
    messages.success(request, "Product bought")
    return redirect("web:index")
