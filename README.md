## Elm Wallet

### this is a programming assignment as part of Elm's programming interview.

Elm wallet has the following features: 

- User Authentication & Authorization
- Wallet operations:
  
    - adding funds
    - withdrawing to bank account
    - transferring funds to another user

- Transactions on each operation
- Paginated view of transactions
- ~~QR code payments~~
- ~~PDF generation for transaction~~

This assignment was completed within the 48 hour period, using the following tech stack:

- Backend: SpringBoot 3
- Database: PostgreSQL 13
- Environment: Docker & Docker Compose

## Running the application

To run the application you must first run the datatbase defined the `docker-compose.yml` file using the following command.

```shell
docker compose up db -d
```

you can then start the application using your favorite IDEA.


## Endpoints

The application has authenticated routes except for the `/auth` routes, 
meaning that you must first create a user then login with that user and including the jwt token
in the `Authorization` header before making requests.

The application has the following endpoints:

### /api/auth/login

```shell
curl --request POST \
  --url http://localhost:8080/api/auth/login \
  --header 'Content-Type: application/json' \
  --data '{
  "username": "malajmi",
  "password": "test"
}'
```

### /api/auth/signup

```shell
curl --request POST \
  --url http://localhost:8080/api/auth/signup \
  --header 'Content-Type: application/json' \
  --data '{
  "username": "mishal",
  "email": "mishal@gmail.com",
  "password": "test"
}'
```

### /api/wallet/add-funds

```shell
curl --request POST \
  --url http://localhost:8080/api/wallet/add-funds \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWxham1pIiwiaWF0IjoxNzIwMjgwNzE0LCJleHAiOjE3MjAyODQzMTR9.pYo2ImhB9Kl4rB9UaREqt6zdSwSt5gH2nnq7wgYOAc8' \
  --header 'Content-Type: application/json' \
  --data '{
  "user_id": "1",
  "amount": 100.0,
  "payment_method": "ApplePay"
}'
```

### /api/wallet/transfer

```shell
curl --request POST \
  --url http://localhost:8080/api/wallet/transfer \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWxham1pIiwiaWF0IjoxNzIwMjgwNzE0LCJleHAiOjE3MjAyODQzMTR9.pYo2ImhB9Kl4rB9UaREqt6zdSwSt5gH2nnq7wgYOAc8' \
  --header 'Content-Type: application/json' \
  --data '{
  "sender_id": "1",
  "recipient_id": "2",
  "amount": 100.00
}'
```

### /api/wallet/withdraw

```shell
curl --request POST \
  --url http://localhost:8080/api/wallet/withdraw \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWxham1pIiwiaWF0IjoxNzIwMjgwNzE0LCJleHAiOjE3MjAyODQzMTR9.pYo2ImhB9Kl4rB9UaREqt6zdSwSt5gH2nnq7wgYOAc8' \
  --header 'Content-Type: application/json' \
  --data '{
  "user_id": 1,
  "amount": 200.00,
  "bank_account": "idw21ioj32"
}'
```

### /api/wallet/transactions

```shell
curl --request GET \
  --url 'http://localhost:8080/api/wallet/transactions?sort_by=created_at&sort_direction=DESC&type=WITHDRAW&user_id=1&status=SUCCESS&start_date=2024-07-05T14%3A41%3A09.834211Z&end_date=' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWxham1pIiwiaWF0IjoxNzIwMjgwNzE0LCJleHAiOjE3MjAyODQzMTR9.pYo2ImhB9Kl4rB9UaREqt6zdSwSt5gH2nnq7wgYOAc8'
```