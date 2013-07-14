## jaxrs-example

This is a simple Java JSON/REST web-services project example with database integration, suitable for Heroku.  Its pretty basic, but exposes a self-documenting (if silly) database-backed RESTful API with a minimum of code. 

It uses:

* [Maven](http://maven.apache.org/) for configuration
* [Jetty](http://www.eclipse.org/jetty/) as a container
* [Jackson 2.0](http://jackson.codehaus.org/) for JSON encoding/decoding
* [JDBI](http://jdbi.org) and [BoneCP](http://jolbox.com) for Postgres access
* [Postgres](http://www.postgresql.org) as the underlying database
* [Swagger](https://developers.helloreverb.com/swagger/) for API documentation

The APIs are all served from the /api/ endpoint.  To make things interesting I've included the Swagger GUI client at ```/swagger/``` - note that this is absolutely not needed for deployment, and you can use any REST client to access the API endpoints.  The endpoints are documented at ```/api/api-docs/``` which is where this copy of Swagger will look by default when its accessed.

Finally it assumes that you have a DATABASE_URL environment variable set similar to:
```
export DATABASE_URL=postgres://dbuser:dbpass@localhost/databasename
```

To run it on a self-contained [Heroku](https://get.heroku.com) environment, simply install git and the heroku toolbelt locally and issue the following commands:
```
git clone https://github.com/rjstanford/jaxrs-example.git;
heroku create;
heroku addons:add heroku-postgresql;
heroku ps:scale web=1;
git push heroku master;
```

Access it through the URL that Heroku gives you and you should be good to go.
