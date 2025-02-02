# PLUTO - Full Stack API Application

A full-stack web application with a **Spring Boot (Java 17)** backend and **Nuxt.js 3** frontend.

You can run the application in two ways:

1. Using **Docker** (recommended).
2. Using **IDE setup** for separate development of backend and frontend.

---

## 1. Running the application with Docker

### Requirements

Ensure the following tools are installed on your system before proceeding:

1. **Java > 17**  
   Open a terminal and run:
   ```bash
   java -version
   ```
   If Java is not installed, follow these installation guides:
    - **Windows**: [Install Java on Windows](https://www.baeldung.com/openjdk-windows-installation)
    - **macOS**: [Install Java on macOS](https://www.baeldung.com/java-macos-installation)
    - **Linux**: If you use Linux, you don't need instructions :)

2. **Docker (>=v27) and Docker Compose**   
   Verify the installation and versions:
   ```bash
   docker -v
   docker-compose -v
   ```
   If Docker is not installed, setup Docker following instructions: [Docker Installation Guide](https://www.docker.com/get-started).

3. **Node.js (>=v18)** and **npm (>=v9)**  
   Verify the installation and versions:
   ```bash
   node -v
   npm -v
   ```
   If Node.js is not installed, setup Node following instructions: [Node.js Website](https://nodejs.org/en/download).

---

### Setup instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project root directory:
   ```bash
   cd <project-root>
   ```
3. Run the appropriate script based on your operating system:
    - For Linux/macOS:
      ```bash
      ./dockerize.sh
      ```
    - For Windows:
      ```bash
      .\dockerize.cmd
      ```
   -Or review the script files and run commands manually

4. Access the application:
    - Backend API: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - Frontend: [http://localhost:3000](http://localhost:3000)

---

## 2. Running and developing locally using an IDE

### Requirements

1. **Development Environment**:
    - Use an IDE suitable for Java and Web development, such as **IntelliJ IDEA** or **VSCode**.

2. **Backend**:
    - **Java (>=17)**: Verify the installation and versions as specified above.
      ```bash
      java -version
      ```
    - **Maven (>=3.9)**:  
      Verify Maven installation:
      ```bash
      mvn -v
      ```
      Alternatively, you can use the provided Maven Wrapper (`./mvnw` or `.mvnw.cmd`).

3. **Frontend**:
    - **Node.js (>=v18)**: Verify the installation and versions as specified above.

---

### Setup instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in your IDE.
3. Configure and run the apps:
    - **Backend**: Follow the instructions in the backend [README.md](backend/README.md).
    - **Frontend**: Follow the instructions in the frontend [README.md](frontend/README.md).

4. You can use the **compound run configuration** [pluto](.idea/runConfigurations/pluto.xml) to start both the backend and frontend together.

---
