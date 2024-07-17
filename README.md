# Registration Form 

## Requirements 
1. MySQL Database 
2. J Connector from [MySQL](https://dev.mysql.com/downloads/connector/j/)
3. VS Code IDE

## How to Use 
1. Clone the [repository](https://github.com/samsonthomas951/RegistrationForm-java.git). 
2. Open the folder in VS Code.
3. Java dependencies will be loaded automatically. After loading, click on `JAVA PROPERTIES`.

    ![JAVA PROPERTIES](https://github.com/samsonthomas951/RegistrationForm-java/blob/main/java.png)

4. Click on `Referenced Libraries` to add J Connector to the class path.

    ![Referenced Libraries](https://github.com/samsonthomas951/RegistrationForm-java/blob/main/Library.png)

5. Navigate to the folder where you placed your connector and select the `mysql-connector-j-9.0.0.jar` file.

### Database
#### SQL Structure 
> **Note:** Make sure the database name is `registration_db`.

```sql
CREATE TABLE users (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Gender ENUM('Male', 'Female') NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Contact VARCHAR(15) NOT NULL
);
```

###APP
![App](https://github.com/samsonthomas951/RegistrationForm-java/blob/main/RegistrationForm.png)


#### Database Connection 

```java
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_db", "root", "");
```
