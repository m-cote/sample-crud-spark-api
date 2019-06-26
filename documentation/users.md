# Users

Manage users.

### Subresources

* [Settings]
* [Attributes]

[Settings]: user-settings.md "User Settings"
[Attributes]: user-attributes.md "User Attributes"

### Available methods

## Create

**POST** /api/users

Create a new user

#### Example Request
``` text
curl --location --request POST "http://localhost:8080/api/users" \
  --data "{
    \"firstName\": \"Created user name\",
    \"lastName\": \"Created user last name\"
}"
```

#### Example Response
`201 Created`

``` json
{
  "id": 8,
  "firstName": "Created user name",
  "lastName": "Created user last name"
}
```

## Read

**GET** /api/users/

Get all users

#### Example Request
`curl --location --request GET "http://localhost:8080/api/users"`

#### Example Response
`200 OK`

``` json
 [
     {
         "id": 1,
         "firstName": "Anna",
         "lastName": "Gutkowski"
     },
     {
         "id": 2,
         "firstName": "Dashawn",
         "lastName": "Deckow"
     },
     {
         "id": 3,
         "firstName": "Joesph",
         "lastName": "Cormier"
     },
     {
         "id": 4,
         "firstName": "Missouri",
         "lastName": "Lemke"
     }
 ]
```
 
**GET** /api/users/{user_id}

Get information about one specific user

#### Example Request
`curl --location --request GET "http://localhost:8080/api/users/1"`
#### Example Response
`200 OK`

``` json
 {
     "id": 1,
     "firstName": "Anna",
     "lastName": "Gutkowski"
 }
```
## Edit

**PUT** /api/users/{user_id}

Update all information about a user

#### Example Request

``` text
curl --location --request PUT "http://localhost:8080/api/users/1" \
  --data "{
    \"firstName\": \"Anna Updated\",
    \"lastName\": \"Gutkowski Updated\"
}"
```

## Delete

**DELETE** /api/users/{user_id}

Delete a user

#### Example request
`curl --location --request DELETE "http://localhost:8080/api/users/1"`