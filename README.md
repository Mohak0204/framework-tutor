# Framework Tutor

A React and Spring Boot learning platform.

## Prerequisites

Install Java 21, Maven 3.9+, Node.js 20+ (includes npm), and Docker Desktop.

## 1. Start PostgreSQL

Copy the local environment template, then choose a local-only password in `.env`:

```powershell
Copy-Item .env.example .env
```

Start PostgreSQL (the database is named `framework_tutor` and is available on port `5432`):

```powershell
docker compose up -d
```

Confirm PostgreSQL is ready:

```powershell
docker compose exec postgres pg_isready -U framework_tutor -d framework_tutor
```

## 2. Run the backend

Open a PowerShell terminal at the repository root. Set the database values; use the exact password chosen above:

```powershell
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_NAME = "framework_tutor"
$env:DB_USERNAME = "framework_tutor"
$env:DB_PASSWORD = "YOUR_LOCAL_PASSWORD" # Must match POSTGRES_PASSWORD in .env
$env:JWT_SECRET = "REPLACE_WITH_A_BASE64_ENCODED_32_BYTE_SECRET"

cd backend
mvn spring-boot:run
```

Generate a suitable local JWT secret with:

```powershell
[Convert]::ToBase64String([System.Security.Cryptography.RandomNumberGenerator]::GetBytes(32))
```

At startup, Flyway applies the database migrations, including the initial `users` table for authentication.

Verify it is running in a second terminal:

```powershell
Invoke-RestMethod http://localhost:8080/api/health
Invoke-RestMethod http://localhost:8080/api/health/database
```

The database check should return `status : ok` and `database : connected`.

## 3. Run the frontend

Open another PowerShell terminal at the repository root:

```powershell
cd frontend
npm install
npm run dev
```

Open the URL printed by Vite (normally [http://localhost:5173](http://localhost:5173)). The page should show **Framework Tutor**.
