# test transfer project

This is test REST Api project to transfer any amount from one account to another
In test example I have only three accounts, all preloaded information is below

| account_number | user_id | amount |
| --- | --- | --- |
| 111 | 1 | 1000.0 |
| 222 | 2 | 2000.0 |
| 333 | 3 | 3000.0 |

 Requirements
----

* Java 8
* Gradle 

 Building
----

to start rest service run start.sh bash script in the root

```bash
start.sh
```

 API
----

* **URL**

  /account/transfer

* **Method:**

    `PUT`

* **URL parameterss**

   **Required:**
 
   `userId=[integer]` \
   `accountOrigin=[integer]` \
   `accountDestination=[integer]` \
   `amount=[decimal]`
   
   **example of json**
```json
{
	"userId" : 1,
	"accountOrigin" : 111,
	"accountDestination" : 222,
	"amount": 123.321
}
```

* **Success Response:**

  * **Code:** 200 OK<br />
    **Content:** `{ "message": "Amount: 50 was successfully transfered from account: 111 to account: 222" }`
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "message": "Can't transfer required amount: not enough on your balance" }`

* **Sample Call:**

```bash
curl -X PUT -H "Content-Type: application/json" \
    -d '{"userId":1,"accountOrigin":111,"accountDestination":222,"amount":10}' \
    http://localhost:8080/account/transfer

```
