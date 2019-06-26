# User Settings

Manage user settings.

### Available methods

## Read

**GET** /api/users/{user_id}/settings

Get user settings for a user.


#### Example Request
`curl --location --request GET "http://localhost:8080/api/users/1/settings"`
#### Example Response
`200 OK`

``` json
 {
   "id": 1,
   "sendSms": true,
   "sendEmail": true
 }
```
## Edit

**PUT** /api/users/{user_id}/settings

Update all of the user settings with new values.

#### Example Request

``` text
curl --location --request PUT "http://localhost:8080/api/users/1/settings" \
  --data "{
    \"sendSms\": false,
    \"sendEmail\": true
}"
```

