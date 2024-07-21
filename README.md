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
