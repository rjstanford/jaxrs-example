jaxrs-example
=============

Simple Java JSON/REST web-services project with database integration, suitable for Heroku 

It assumes that you have a Postgress installation containing a table:

CREATE TABLE counter (
  curval integer
); 

INSERT INTO counter VALUES ( 0 );

Finally it assumes that you have a DATABASE_URL environment variable set similar to:

export DATABASE_URL=postgres://dbuser:dbpass@localhost/databasename
