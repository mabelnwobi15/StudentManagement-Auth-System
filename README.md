# Student Management System

## Prerequisites
- **Backend:** .NET 9 SDK (or Visual Studio 2022)
- **Mobile:** Android Studio (API 31+ recommended)

## How to Run the Backend
1. Open the `Backend/` folder in Visual Studio or your terminal.
2. Run the project using `dotnet run` or by pressing F5.
3. Note the running port (e.g., `http://localhost:5257`). 
4. You can view the Swagger UI at `http://localhost:5257/swagger`.

## How to Run the Android App
1. Open the `MobileApp/` folder in Android Studio.
2. Verify that `ApiService.kt` points to your local backend port using the emulator alias: `http://10.0.2.2:5257/`.
3. Start an Android Emulator and click **Run**.

## Test Credentials
- **Admin Login:** admin@school.com / password123
- **Student Login:** student@school.com / password123
