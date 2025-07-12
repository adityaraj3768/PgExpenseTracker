# Manual CORS Verification Guide

## Quick Verification Steps

After deploying the updated backend, you can verify the CORS fix using these methods:

### Method 1: Browser Developer Tools
1. Open the frontend application at `https://lemon-desert-0b879aa10.1.azurestaticapps.net`
2. Open browser Developer Tools (F12)
3. Go to the Network tab
4. Try to perform a login or any API call
5. Look for the OPTIONS preflight request
6. Check the response headers for:
   - `Access-Control-Allow-Origin: https://lemon-desert-0b879aa10.1.azurestaticapps.net`
   - `Access-Control-Allow-Methods: GET,POST,PUT,DELETE,OPTIONS,PATCH`
   - `Access-Control-Allow-Credentials: true`

### Method 2: curl Command
Test the OPTIONS preflight request:
```bash
curl -H "Origin: https://lemon-desert-0b879aa10.1.azurestaticapps.net" \
     -H "Access-Control-Request-Method: POST" \
     -H "Access-Control-Request-Headers: Content-Type,Authorization" \
     -X OPTIONS \
     -v \
     "https://pgexpensetracker-ahc3h0bna5gfamff.canadacentral-01.azurewebsites.net/auth/login"
```

### Method 3: Test API Call
Try making an actual API call from the frontend:
```javascript
fetch('https://pgexpensetracker-ahc3h0bna5gfamff.canadacentral-01.azurewebsites.net/auth/testing', {
  method: 'GET',
  credentials: 'include',
  headers: {
    'Content-Type': 'application/json'
  }
})
.then(response => response.text())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

## What to Look For

### Success Indicators
- No CORS errors in browser console
- OPTIONS requests return 200 status
- Proper CORS headers are present in response
- API calls work without issues

### Failure Indicators
- CORS errors still appear in console
- OPTIONS requests return 403/404/500
- Missing or incorrect CORS headers
- API calls fail with network errors

## Common Issues & Solutions

### Issue: Still getting CORS errors
**Solution**: Check that the frontend origin matches exactly (including https/http and any port numbers)

### Issue: OPTIONS request returns 401/403
**Solution**: Verify that OPTIONS requests are properly excluded from authentication in SecurityConfig

### Issue: Credentials not working
**Solution**: Ensure both frontend and backend have credentials enabled and origins match exactly

## Environment Variables
Make sure the `FRONTEND_ORIGIN` environment variable is set correctly in Azure App Service:
- Go to Azure Portal → App Service → Configuration → Application settings
- Verify `FRONTEND_ORIGIN` is set to the correct frontend URL