# Users Attributes

Attributes are string key-value pairs which describe an associated user. Use the User Attributes API calls to manage attributes.

### Available methods

## Create

**POST** /api/users/{user_id}/attributes

Create attributes or update its values for a specific user. Values of attributes not featured in the request won't be affected.

#### Example Request
``` text
curl --location --request POST "http://localhost:8080/api/users/1/attributes" \
  --data "{
    \"comment\": \"new attribute value\"
}"
```

## Read

**GET** /api/users/{user_id}/attributes

Get attributes currently associated with a specific user.

#### Example Request
`curl --location --request GET "http://localhost:8080/api/users/1/attributes"`

#### Example Response
`200 OK`

``` json
{
    "phone": "+380990001111",
    "email": "test@email.com"
}
```
 
**GET** /api/users/{user_id}/attributes/{attribute_key}

Get an attribute for a user by specifying attribute's key and user's unique id.

#### Example Request
`curl --location --request GET "http://localhost:8080/api/users/1/attributes/email"`
#### Example Response
`200 OK`

``` json
{
    "email": "test@email.com"
}
```
## Edit

**POST** /api/users/{user_id}/attributes

Create attributes or update its values for a specific user. Values of attributes not featured in the request won't be affected.

#### Example Request

``` text
curl --location --request POST "http://localhost:8080/api/users/1/attributes" \
  --data "{
	\"email\": \"updated@email.com\",
    \"phone\": \"updated +380990001111\"
}"
```

## Delete

**DELETE** /api/users/{user_id}/attributes/{attribute_key}

Remove an attribute associated with a specific user.

#### Example request
`curl --location --request DELETE "http://localhost:8080/api/users/1/attributes/email"`