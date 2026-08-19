# Framework Tutor

A React and Spring Boot learning platform.

## Prerequisites

Install Java 21, Maven 3.9+, Node.js 20+ (includes npm), and Docker Desktop.

## 1. Start PostgreSQL

Choose a local-only password and replace `YOUR_LOCAL_PASSWORD` below before running the command:

```powershell
docker run --name framework-tutor-postgres --env POSTGRES_DB=framework_tutor --env POSTGRES_USER=framework_tutor --env POSTGRES_PASSWORD=YOUR_LOCAL_PASSWORD --publish 5432:5432 --detach postgres:16
```

Run this once. On later days, start the existing database with:

```powershell
docker start framework-tutor-postgres
```

Confirm PostgreSQL is ready:

```powershell
docker exec framework-tutor-postgres pg_isready -U framework_tutor -d framework_tutor
```

## 2. Run the backend

Open a PowerShell terminal at the repository root. Set the database values; use the exact password chosen above:

```powershell
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_NAME = "framework_tutor"
$env:DB_USERNAME = "framework_tutor"
$env:DB_PASSWORD = "YOUR_LOCAL_PASSWORD"

cd backend
mvn spring-boot:run
```

Verify it is running in a second terminal:

```powershell
Invoke-RestMethod http://localhost:8080/api/health
```

Expected result: `status : ok`.

## 3. Run the frontend

Open another PowerShell terminal at the repository root:

```powershell
cd frontend
npm install
npm run dev
```

Open the URL printed by Vite (normally [http://localhost:5173](http://localhost:5173)). The page should show **Framework Tutor**.
