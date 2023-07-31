# Belvo Poc API

This is a Spring Boot 3 application that integrates the [Belvo API](https://developers.belvo.com/docs)
to:
- Create a User and store different Belvo Links
- Fetch Account and Transaction data

## General Workflow

All endpoint specification shown next can be consulted in the [API documentation](http://18.218.244.118:8080/swagger-ui/index.html#/).

### User registration
A user is register via the endpoint `/api/v1/auth/register`. This will generate a JWT that
will contain the user information required for the next requests.

Once a user is registered, the user can get the authentication token again via the endpoint `/api/v1/auth/authenticate`.

### Belvo Link creation
A Belvo Link represents the connection for the user with a financial institution via the endpoint `/api/v1/belvo/link`.

For creating this link, the user will have to provide its banking credentials when registering a link.
After this, the link will be stored in the database for future queries associated with that user/institution pair.

For testing purposes, only the [testing banking institutions provided by Belvo](https://developers.belvo.com/docs/test-in-sandbox) are supported right now, so
there's no need to provide your financial credentials for testing this API.

Testing banking credentials:

**For full activity**

username: 
bnk100

password:
full

**For low activity**
username:
bnk102

password:
low

### Fetching banking data

Right now, the API only supports fetching Account and Transaction data.
For this matter, the user will only have to provide the `institution data` for the request body.
The user banking link will be fetched with the JWT provided in each request, and validated via Spring Security.
This can be done via the endpoints `/api/v1/belvo/accounts` and `/api/v1/belvo/transactions`

## API Documentation
[Documentation link](http://18.218.244.118:8080/swagger-ui/index.html#/)

## Frontend repository
[Frontend Repository link](https://github.com/edvicaty/belvo-poc-client)

## Environmental Variables

BELVO_DB_PASSWORD=

BELVO_JWT_SECRET_KEY=

BELVO_API_BASE_URL=

BELVO_API_ID=

BELVO_API_PASSWORD=

DB_URL=
