# PLUTO - Backend

## Setup

1. Clone the repository.
2. Open the project in your IDE.
3. Configure your IDE to use JDK 17 or higher.
4. Run the application from your IDE with provided IntelliJ IDEA run configuration [backend](../.idea/runConfigurations/backend.xml).

> **Note**: This application uses an in-memory H2 database. All data will be lost after a restart due to non-persistent storage.

You can access the exposed backend APIs via Swagger URL
`http://localhost:8080/swagger-ui/index.html#/`

---

## Test Data

Sample test data is preloaded into the H2 database for testing purposes when the application is launched.
You can interact with the pre-populated data via the application APIs or add new data if needed

---

## API Description & Usage

The application's APIs can be accessed via the following base URL after starting the application:

```
http://localhost:8080
```

***Product API***:

- `POST /secure/v1/products/create`<br>

Creates product with provided data, needs a valid UUID.   
Returns created product ID as UUID.

```json
{
  "productId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "productName": "string",
  "productPrice": 0.1,
  "priceCurrency": "EUR"
}
```

- `POST /secure/v1/products/get-all`<br>

Returns all products from DB.

***Customer Action API***:

- `POST /secure/v1/customer-actions/log`<br>

Logs a specific customer action.

Action type: `PRODUCT_VIEWED`<br>
Product ID must be a valid UUID from DB<br>
Session ID is optional<br>

```json
{
  "actionType": "PRODUCT_VIEWED",
  "customerId": "635fffef-dfce-4f11-93e5-3c4e22e6db11",
  "productId": "57edfb08-134f-4c06-868c-2a6e38ba03ab",
  "sessionId": "9b64f1c5-c2c3-4571-9dfb-d7bedecfaf15"
}
```

Action type: `PRODUCT_ADDED_TO_CART`<br>
Product ID must be a valid UUID from DB<br>
Session ID is optional<br>

```json
{
  "actionType": "PRODUCT_ADDED_TO_CART",
  "customerId": "635fffef-dfce-4f11-93e5-3c4e22e6db11",
  "productId": "57edfb08-134f-4c06-868c-2a6e38ba03ab",
  "sessionId": "9b64f1c5-c2c3-4571-9dfb-d7bedecfaf15"
}
```

Action type: `CHECKOUT_STARTED`<br>
Product ID is not needed<br>
Session ID is optional<br>
Customer has to have a previous action `PRODUCT_ADDED_TO_CART` to log this action

```json
{
  "actionType": "CHECKOUT_STARTED",
  "customerId": "635fffef-dfce-4f11-93e5-3c4e22e6db11",
  "sessionId": "9b64f1c5-c2c3-4571-9dfb-d7bedecfaf15"
}
```

Action type: `PURCHASE_COMPLETED`<br>
Product ID is not needed<br>
Session ID is optional<br>
Customer has to have a previous action `CHECKOUT_STARTED` to log this action<br>
Sales data CAN contain products that are not stored in DB<br>
See comments in code, line 89 [CustomerActionService.java](src/main/java/skalaengineering/pluto/service/CustomerActionService.java)

```json
{
  "actionType": "PURCHASE_COMPLETED",
  "customerId": "635fffef-dfce-4f11-93e5-3c4e22e6db11",
  "sessionId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "salesTransactionData": {
    "totalAmount": 0.1,
    "soldProducts": [
      {
        "productId": "9b64f1c5-c2c3-4571-9dfb-d7bedecfaf15",
        "quantity": 9007199254740991
      }
    ]
  }
}
```

***Sales Conversion API***:

- `POST /secure/v1/conversion/total-sales`<br>

Returns total sales amount for specified time period.

- `POST /secure/v1/conversion/total-sales`<br>

Logs a specific customer action.
By providing any of the conversion types and valid product ID (if needed for conversion type), returns the calculated conversion type.

---

## Future Updates and Real-World Implementation

For real-world use cases, the following updates and improvements should be implementeds:

1. **Event-Driven Architecture**:
    - Given the likelihood of high traffic to the app, event-driven architecture will be introduced using **Apache Kafka** or **ActiveMQ**.
        - The main application will publish customer action events to a Kafka topic.
        - The Sales Conversion Service will consume these events from the topic and update the database and metrics accordingly.

2. **Real-Time Conversion Metrics**:
    - Conversion metrics will be recalculated and updated in real time as new customer events are processed.
      This will allow conversion metrics to be served instantaneously to dashboards without recalculating on every API request and adding extra load to DB.

3. **ORM Adjustments**:
    - I would remove ORM for now, initially, the application may rely on lightweight queries or native frameworks until the app becomes large enough to justify ORM overhead.

4. **Frontend Admin Panel**:
    - A user-friendly **admin panel** will should be built for monitoring conversion metrics and administrating customer actions (view, modify, add).

5. **Security Enhancements**:
    - Add **JWT-based authentication** along with user roles and permissions for secure access.

6. **Testing Improvements**:
    - Service layer tests are currently implemented as **heavy integration tests**, these tests will be refactored into lightweight **unit tests**.

7. **Swagger example data**:
    - Swagger example data, e.g., example requests with descriptions on how to use them needs to be provided not only in readme file but also in Swagger.

---

## Current APP Limitations

1. **Data Loss on Restart**:
    - The app currently uses an in-memory H2 database. All data is wiped on restart.
      PostgreSQL database needs to be added.

2. **Performance Bottlenecks**:
    - The current conversion metrics are calculated on demand by querying the database.
      This could cause performance issues under high traffic, especially with large datasets.

3. **Security**:
    - No authentication or authorization is currently implemented. APIs are accessible without restriction.

4. **Frontend**:
    - No user interface for administration or monitoring exists as of now.

---

Note to self to add following items:

- Projections as return types in repository.<br>
- Custom headers for requests with global context.<br>
- External API client calls.<br>
- Custom exception with error interface.<br>
- Jpa auditing based on headers.<br>
- Global caching mechanics.<br>
- Micrometer for tracing and metrics.<br>
- Customized logging.<br>
