# Real-Time Data Processing System

A real-time data processing application that ingests data from Apache Kafka, processes it using a Spring Boot application, and stores the results in a PostgreSQL database. This system demonstrates a full pipeline from data ingestion to storage, suitable for scalable and high-throughput environments.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Results](#results)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Features

- **Real-Time Data Ingestion**: Utilizes Apache Kafka for high-throughput, low-latency ingestion of data streams.
- **Data Processing**: Implements a Spring Boot application that consumes Kafka messages, processes them (filtering, transformation, aggregation), and persists the results.
- **Database Storage**: Stores processed data in PostgreSQL, a reliable and scalable relational database.
- **Scalability**: Designed to handle large volumes of data with the ability to scale horizontally.

---

## Architecture

Below is the architecture diagram of the Real-Time Data Processing System:

```
+------------------+
|                  |
|   Data Producer  |
| (Kafka Producer) |
|                  |
+---------+--------+
          |
          | 1. Send messages to Kafka topic
          v
+---------+--------+
|                  |
|  Kafka Broker    |
| (incoming-data)  |
|                  |
+---------+--------+
          |
          | 2. Kafka Consumer subscribes to topic
          v
+---------+--------+
|                  |
|  Spring Boot     |
|  Application     |
| (Kafka Consumer) |
|                  |
+---------+--------+
          |
          | 3. Process data (filter, transform)
          v
+---------+--------+
|                  |
|  PostgreSQL      |
|  Database        |
| (processed_data) |
|                  |
+------------------+
```

### Component Overview

1. **Data Producer (Kafka Producer)**:
   - Any application or service that generates data to be processed.
   - Sends messages to the Kafka topic `incoming-data`.

2. **Kafka Broker**:
   - Acts as a messaging system that receives and holds messages from producers.
   - Manages the `incoming-data` topic where messages are stored until consumed.

3. **Spring Boot Application (Kafka Consumer)**:
   - Listens to the Kafka topic `incoming-data`.
   - Consumes messages in real-time.
   - Performs data processing tasks such as filtering, transformation, and aggregation.

4. **PostgreSQL Database**:
   - Stores the processed data in the `processed_data` table.
   - Allows for querying and analysis of the stored data.

### Data Flow

- **Step 1**: Data producers send messages to the Kafka broker's `incoming-data` topic.
- **Step 2**: The Spring Boot application consumes messages from the `incoming-data` topic.
- **Step 3**: The application processes the data (e.g., filters invalid messages, transforms the data).
- **Step 4**: Processed data is saved to the `processed_data` table in the PostgreSQL database.

---

## Prerequisites


Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 17 or higher**
- **Apache Kafka** (latest version)
- **Apache Zookeeper** (comes bundled with Kafka)
- **PostgreSQL** (version 12 or higher)
- **Apache Maven**
- **Git**

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/real-time-data-processing-system.git
cd real-time-data-processing-system
```

### 2. Set Up PostgreSQL Database

- **Create Database**: Log in to PostgreSQL and create a new database.

  ```sql
  CREATE DATABASE your_postgres_database;
  ```

- **Set Up User (Optional)**: Ensure you have a user with the necessary privileges.

  ```sql
  CREATE USER postgres WITH PASSWORD 'your_postgres_password';
  GRANT ALL PRIVILEGES ON DATABASE your_postgres_database TO your_postgres_username;
  ```

### 3. Configure Application Properties

- Open `src/main/resources/application.properties` and update the following properties:

  ```properties
  # Database Configuration
  spring.datasource.url=jdbc:postgresql://localhost:5432/your_postgres_databse
  spring.datasource.username=your_postgres_username
  spring.datasource.password=your_postgres_password

  # Kafka Configuration
  spring.kafka.bootstrap-servers=localhost:9092
  spring.kafka.consumer.group-id=data-processing-group

  # Hibernate Configuration
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  ```

### 4. Install Dependencies

```bash
mvn clean install
```

### 5. Start Zookeeper and Kafka

#### Start Zookeeper

```bash
# Navigate to Kafka installation directory
cd path/to/kafka

# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
```

#### Start Kafka Broker

Open a new terminal:

```bash
# Navigate to Kafka installation directory
cd path/to/kafka

# Start Kafka Broker
bin/kafka-server-start.sh config/server.properties
```

### 6. Create Kafka Topic

Open a new terminal:

```bash
# Navigate to Kafka installation directory
cd path/to/kafka

# Create topic 'incoming-data'
bin/kafka-topics.sh --create --topic incoming-data --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
```

---

## Usage

### 1. Run the Spring Boot Application

```bash
mvn spring-boot:run
```

The application will start and listen for messages on the `incoming-data` Kafka topic.

### 2. Send Messages to Kafka Topic

Open a new terminal:

```bash
# Navigate to Kafka installation directory
cd path/to/kafka

# Start Kafka Console Producer
bin/kafka-console-producer.sh --topic incoming-data --bootstrap-server localhost:9092
```

Type messages and press **Enter** after each one:

```
Hello World
This is a test message
Another message for processing
```

### 3. Verify Processing and Storage

- **Application Logs**: Check the terminal running the Spring Boot application to see logs indicating message receipt and processing.

- **Database Verification**: Connect to the PostgreSQL database to verify that data has been stored.

  ```bash
  psql -h localhost -U postgres -d your_postgres_database
  ```

  ```sql
  SELECT * FROM processed_data;
  ```

---

## Results

- **Message Processing Output**

  ![image](https://github.com/user-attachments/assets/d8d52ea2-de5e-4771-b4d5-54820d4fe496)


- **Database Entries**

  ![Screenshot 2024-09-29 212915](https://github.com/user-attachments/assets/b3849206-f3f2-4a7a-b96a-ba9fec738715)


### Explanation of Results

The application logs showcase the successful real-time processing of messages received from the Kafka topic `incoming-data` by the Spring Boot application. Here's a breakdown of the key steps illustrated in the logs:

1. **Message Reception**: The application receives various messages sent to the Kafka topic, such as:
   - "Hello World"
   - "This is for test purposes"
   - "Data processing test"
   - "Everything working as expected!"
   - An empty message (noted in the logs)
   - "123456789+-*/!"

2. **Filtering**: The application implements a filtering mechanism to exclude invalid or empty messages. In the logs, we see an empty message being received and filtered out:
   ```
   Received message: 
   Message filtered out:
   ```

3. **Transformation**: Valid messages undergo a transformation process. Specifically, each message is reversed and converted to uppercase. For example:
   - Original: "Hello World"
   - Transformed: "DLROW OLLEH"
   - Original: "This is for test purposes"
   - Transformed: "SESOPRUP TSET ROF SI SIHT"

4. **Data Aggregation**: Additional metadata is computed for each message, such as the length of the original message (`dataLength`) and a timestamp of when the message was processed (`processedAt`).

5. **Database Insertion**: The processed data is saved to the PostgreSQL database. The logs display Hibernate executing SQL `INSERT` statements to store each `ProcessedData` object:
   ```
   Hibernate:
       insert
       into
           processed_data
           (data_length, original_data, processed_at, transformed_data, valid)
       values
           (?, ?, ?, ?, ?)
   Processed and saved data: ProcessedData{id=1, originalData='Hello World', transformedData='DLROW OLLEH', processedAt=2024-09-29T21:17:39.997929400, dataLength=11, valid=true}
   ```

6. **Error Handling**: The application gracefully handles network interruptions or other transient issues. For instance, the log entry:
   ```
   2024-09-29T21:26:39.688+05:30  INFO 31204 --- [ntainer#0-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-data-processing-group-1, groupId=data-processing-group] Node -1 disconnected.
   ```
   indicates a temporary disconnection that doesn't halt the application's operation.

**Summary**: The logs confirm that the system effectively ingests messages from Kafka, processes them according to the defined business logic (filtering out invalid entries, transforming valid messages), and persists the results in a PostgreSQL database. This demonstrates the application's capability to handle real-time data processing tasks reliably and efficiently.


---

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/YourFeature`)
3. Commit your Changes (`git commit -m 'Add Your Feature'`)
4. Push to the Branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## License

Distributed under the MIT License. See `LICENSE` file for more information.

---

## Contact

**Your Name**

- Email: srikanthamsa@gmail.com
- LinkedIn: [Srikant Hamsa](https://linkedin.com/in/srikanthamsa)
- GitHub: [srikanthamsa](https://github.com/srikanthamsa)

---

*Feel free to reach out if you have any questions or suggestions!*

---
