# Student Management – Deployment Guide

## Requirements

* **Apache Tomcat 8+**
* **MySQL 10+**
* **`quanlysinhvien.sql`** (included in this repo)

## Demo

A live version of this project is available at:  
[https://youtu.be/nWFKbyns1-4](https://youtu.be/nWFKbyns1-4)

## 1 – Build and Deploy

1. Execute:

   ```bash
   mvn clean package
   ```
   This produces `.war` in the *target/* directory.
2. Copy the WAR into **`${CATALINA_BASE}/webapps/`**.
3. Start (or restart) Tomcat – it unpacks and serves the app automatically.

## 2 – Initialise the Database

Import the schema **once** before first run:

```bash
mysql -u <user> -p < quanlysinhvien.sql
```

## 3 – Configure Credentials

Open **`webapp/META-INF/context.xml`** and adjust the `<Resource>` element:

```xml

<Resource name="services/ConnectionService"
          type="org.decade.studentmanangement.services.ConnectionService"
          factory="org.decade.studentmanangement.services.factories.ConnectionServiceFactory"
          singleton="true"
          username="YOUR_USERNAME"
          password="YOUR_PASSWORD"/>

```

## 4 – First Run Checklist

1. MySQL is running and the *quanlysinhvien* schema exists.
2. Tomcat has been started/restarted.
3. Navigate to `http://localhost:8080//StudentManangement` – the login page should appear.
