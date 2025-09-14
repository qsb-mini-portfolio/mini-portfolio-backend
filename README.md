# Backend

## Prerequisites

Make sure you have the following installed:

- [Java JDK 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Docker & Docker Compose](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com/downloads)
- [DBeaver](https://dbeaver.io/download/)

## Step 1: Clone the Repository

Open your terminal and run:

```
git clone https://gitlab.com/qs-portfolio/Backend.git
cd Backend
```

## Step 2: Set Up the Database with Docker Compose

Start Docker and run :

```
docker-compose up -d
```

## Step 3: Configure the Run/Debug Configuration

1. Start DBeaver and create a new connection
2. Select `PostgreSQL`
3. Set the DB name, username and pasword to `portfolio`

## Step 4: Import the Project into IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **File > Open**
3. Navigate to the cloned `Backend` directory and open it
4. Wait for IntelliJ to index and import the Maven/Gradle dependencies

## Step 5: Configure the Run/Debug Configuration

1. Go to **Run > Edit Configurations…**
2. Click the + button and select **Spring Boot**
3. Set the main class to: `com.qsportfolio.backend.BackendApplication`
4. Under **VM options**, add: `-Dspring.profiles.active=local`
5. Apply and save the configuration

## Step 6: Run the Application

Select the configuration you just created and click **Run**. The backend should now start with the `local` profile
