# Laboratory 4 - Database

## Why?

I have choosen the option of add a database to the project becouse I have seen it as the most interesting option. One of the most important part of an application is the data, and be able to persist it correctly is basic in all applications.

## How?

Looking at the general structure of the application, I decided to add a database that will help to load all the data that was created in previous executions and save the new data of other executions.

If a entity will be created, will be checked if previously exist (Usually by the name or code). If the entity existed before, it will be loaded by the database, if not, it will be created and added to the database.

We will have 3 types of entities:
- **Bank:** It will contains all the data of the bank.
- **Client:** It will contains all the data of a client and the bank that is member of.
- **Account:** It will contains all the data of an account and will be linked with the client that is the owner.

The relations between entities will be:
- **Bank : Client** -> One to many
- **Client : Account** -> One to many