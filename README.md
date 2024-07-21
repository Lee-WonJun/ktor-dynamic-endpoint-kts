## ktor-dynamic-endpoint-kts

### Description
![녹음 2024-07-22 003003](https://github.com/user-attachments/assets/f509e7b1-88ff-41ac-bda4-9741d99ead46)


`ktor-dynamic-endpoint-kts` is a Ktor-based server application sample that allows dynamic creation of endpoints using uploaded Kotlin script (.kts) files. This project enables users to define server routes and logic dynamically by uploading Kotlin script files

### Features
- **Dynamic Endpoint Creation**: Create new endpoints by uploading .kts script files.
- **Script Execution**: Execute the uploaded Kotlin scripts at the specified endpoints.

### Potential Use Cases

- **Prototyping and debug**: Quickly create and test new endpoints without redeploying the test server.

- **Temporary Services**: Offer short-lived services or features that are available only for a limited time.

### Important Note

For production use, ensure that dynamic route creation is disabled or restricted due to potential security and stability concerns. This feature is best suited for development, testing, and educational purposes.


### Example script
- simple
```kotlin
val greeting = "Hello, World!"
greeting
```
![image](https://github.com/user-attachments/assets/f9278821-47ee-40ee-b2b6-58520a5fca1e)


- monitoring
```kotlin
import java.lang.management.ManagementFactory
import java.sql.DriverManager

val memoryMXBean = ManagementFactory.getMemoryMXBean()
val heapMemoryUsage = memoryMXBean.heapMemoryUsage
val nonHeapMemoryUsage = memoryMXBean.nonHeapMemoryUsage

val memoryStatus = """
    Heap Memory:
        Init: ${heapMemoryUsage.init / 1024 / 1024} MB
        Used: ${heapMemoryUsage.used / 1024 / 1024} MB
        Committed: ${heapMemoryUsage.committed / 1024 / 1024} MB
        Max: ${heapMemoryUsage.max / 1024 / 1024} MB

    Non-Heap Memory:
        Init: ${nonHeapMemoryUsage.init / 1024 / 1024} MB
        Used: ${nonHeapMemoryUsage.used / 1024 / 1024} MB
        Committed: ${nonHeapMemoryUsage.committed / 1024 / 1024} MB
        Max: ${nonHeapMemoryUsage.max / 1024 / 1024} MB
"""

val dbStatus = try {
    val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT 1")
    if (resultSet.next()) {
        "Database connection successful"
    } else {
        "Database connection failed"
    }
} catch (e: Exception) {
    "Database connection error: ${e.message}"
}

val status = """
    Memory Status:
    $memoryStatus

    Database Status:
    $dbStatus
"""

status
```

![image](https://github.com/user-attachments/assets/b84bc835-1ba8-48b6-ab50-85a5fcefaca6)


- reflection
```kotlin
import dynamicEndpoint.objects.DynamicRoute

val x = try {
    val fnsfield = DynamicRoute::class.java.getDeclaredField("fns")
    fnsfield.toString()

    fnsfield.setAccessible(true);
    val fns = fnsfield.get(DynamicRoute)

    fns.toString()

} catch (e: Exception) {
    "Error: ${e.message}"
}

x
```
![image](https://github.com/user-attachments/assets/b51e729a-efb4-4095-a412-d10b60e558a7)
