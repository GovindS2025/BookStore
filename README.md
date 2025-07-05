

# 📚 BookStore Application

A simple Book Store RESTful API built with **Spring Boot**,  **postgresq** and **MongoDB**.


## 🚀 Features

- 📘 Manage books (add, update, delete, view)
- 🧾 Checkout and return books
- ✍️ Add and view reviews
- 🧪 Unit tested with JUnit
- 🔐 **postgresq** integration
- 🐳 Docker-ready (optional)

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data postgresq
- Maven
- postgresq & MongoDB(optional) (local or Docker)
- Postman (for manual API testing)
- Docker (optional)

## 📂 Project Structure

```plaintext
bookstore/
├── src/
│   ├── main/java/com/org/bookstore_backend/
│   │   ├── entity/         # MongoDB documents
│   │   ├── repo/           # Spring Data Repositories
│   │   ├── services/       # Service interfaces
│   │   ├── services/impl/  # Service implementations
│   │   ├── controller/     # REST Controllers
│   └── resources/
│       └── application.yml
├── Dockerfile              # (optional) for containerization
├── pom.xml                 # Maven config
└── README.md               # This file
⚙️ Getting Started
🔧 Prerequisites
Java 17+

Maven

postgresq & MongoDB (running locally or via Docker)

Git (installed and configured)

💻 Local Setup
bash

# Clone the repository
git clone https://github.com/yourusername/bookstore.git
cd bookstore

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
App will be available at:
📍 http://localhost:8080

🛢️ MongoDB Configuration
In application.properties:

properties

spring.data.mongodb.uri=mongodb://localhost:27017/bookstore
Make sure postgresq or MongoDB is running locally.
You can also use Docker to spin up MongoDB:

bash

docker run -d -p 27017:27017 --name mongodb mongo

🔌 API Endpoints (Sample)
Method	Endpoint	Description
GET	/api/books	List all books
POST	/api/books	Add a new book
DELETE	/api/books/{id}	Delete a book
PUT	/api/books/{id}	Update a book
POST	/api/books/{id}/review	Add review to book

Test all APIs using Postman.

🧪 Running Tests
bash

mvn test
Tests are written using JUnit and Mockito.

🐳 Docker (Optional)
To build and run the app in Docker:

bash

docker build -t bookstore-app .
docker run -p 8080:8080 bookstore-app

👨‍💻 Author
Your Name
Govind Singh
GitHub: @GovindS2025

📜 License
This project is licensed under the MIT License. See LICENSE for details.

---

## 📌 What To Do Next

1. Save the file as `README.md` inside your `bookstore/` folder.
2. Commit it to Git:

```bash
git add README.md
git commit -m "Add project README"
git push
