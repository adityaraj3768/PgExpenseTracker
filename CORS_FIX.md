# CORS Configuration Fix

## Problem
The frontend application hosted at `https://lemon-desert-0b879aa10.1.azurestaticapps.net` was experiencing CORS (Cross-Origin Resource Sharing) errors when trying to access the backend API at `https://pgexpensetracker-ahc3h0bna5gfamff.canadacentral-01.azurewebsites.net`.

Error message:
```
Access to XMLHttpRequest at 'https://pgexpensetracker-ahc3h0bna5gfamff.canadacentral-01.azurewebsites.net/auth/login' 
from origin 'https://lemon-desert-0b879aa10.1.azurestaticapps.net' has been blocked by CORS policy: 
Response to preflight request doesn't pass access control check: 
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

## Solution
Updated the CORS configuration in the Spring Boot application to properly handle cross-origin requests.

### Changes Made

1. **Enhanced SecurityConfig.java**
   - Modified `corsConfigurationSource()` method to support multiple frontend origins
   - Added the specific Azure Static Web App origin causing the issue
   - Included localhost origins for development
   - Enhanced allowed headers and methods
   - Added cache configuration for preflight requests

2. **Added @CrossOrigin annotations**
   - Added `@CrossOrigin` annotation to `AuthController`
   - Added `@CrossOrigin` annotation to `TestController`
   - Specified allowed origins and credentials for additional security

3. **Java Version Compatibility**
   - Changed Java version from 21 to 17 for compatibility

### Configuration Details

The CORS configuration now allows:
- **Origins**: 
  - `https://lemon-desert-0b879aa10.1.azurestaticapps.net` (Azure Static Web App)
  - `http://localhost:3000` (React development)
  - `http://localhost:5173` (Vite development)
  - Any configured `FRONTEND_ORIGIN` environment variable

- **Methods**: GET, POST, PUT, DELETE, OPTIONS, PATCH
- **Headers**: All headers allowed (`*`)
- **Credentials**: Enabled
- **Max Age**: 3600 seconds (1 hour cache for preflight)

### Files Modified
1. `Backend/pom.xml` - Java version updated
2. `Backend/src/main/java/com/example/java/Security/Config/SecurityConfig.java` - Enhanced CORS configuration
3. `Backend/src/main/java/com/example/java/Security/Controller/AuthController.java` - Added @CrossOrigin
4. `Backend/src/main/java/com/example/java/Controller/TestController.java` - Added @CrossOrigin
5. `.gitignore` - Added to exclude build artifacts

## Deployment
After these changes are deployed, the frontend should be able to successfully make requests to the backend API without CORS errors.

## Testing
To test the CORS configuration after deployment:
1. Deploy the updated backend to Azure
2. Test from the frontend application
3. Check browser developer tools for CORS-related errors
4. Verify that preflight OPTIONS requests return appropriate CORS headers