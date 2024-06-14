@@ -0,0 +1,40 @@
# Install Requirements

pip install -r requirements.txt

# Create the database

## Create the necessary tables
This command create the neccesary tables for the INTALLED_APPS
```
python manage.py migrate
```

## [Optional] Migrate only one part
If we need to change the database but dont want to delete and regenerate it, we can only regenerate the part around our application. In this case, we need to prepare the migration with the next command. In this case, our proyect name is ```web```

```
python manage.py makemigrations <proyect_name>
```

### [OPTIONAL] Check generated SQL to create tables

This create a new file in ```proyect_name/migrations``` with a number. We can apply this migration file using the command

```
python manage.py sqlmigrate <proyect_name> <file_number>
python manage.py sqlmigrate web 0001
```

### [OPTIONAL] Reset Database

Reset database

```
python manage.py flush
```

# Admin section

## Create super user
It is neccesary to acces to the admin control panel, that is located in ```http://127.0.0.1:8000/admin```

```
python manage.py createsuperuser
```

# Start server
This command start the server. You only need to do the steps before one time, but you will need to start the server each time you start to work.

```
python manage.py runserver
```