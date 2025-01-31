# Application README

## Requirements to Run the Application

- **IDE**: Any IDE suitable for Java development (e.g., IntelliJ IDEA, Eclipse).
- **Java**: JDK 17 or higher must be configured on your machine.
- **Maven**: Maven 3.9.x or higher for dependencies and builds.

---

## Setup Instructions

1. Clone the repository.
2. Open the project in your IDE.
3. Configure your IDE to use JDK 17 or higher.
4. Run the application from your IDE.

> **Note**: This application uses an in-memory H2 database. All data will be lost after a restart due to non-persistent storage.

---

## Test Data

Sample test data is preloaded into the H2 database for testing purposes when the application is launched.
You can interact with the pre-populated data via the application APIs or add new data if needed

---

## API Description & Usage

1. **Customer Actions**:
    - APIs to log customer actions such as viewing a product, adding to the cart, checking out, and completing purchases.
      {provide request examples}

2. **Sales Metrics**:
    - APIs to fetch sales metrics including total sales in a specific period, and conversion rates for customer actions (e.g., viewed-to-purchased conversion rate).
      {provide request examples}

### Base URL

The application's APIs can be accessed via the following base URL after starting the application:

```
http://localhost:8080
```

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
