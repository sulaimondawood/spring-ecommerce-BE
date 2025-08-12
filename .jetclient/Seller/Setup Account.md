```toml
name = 'Setup Account'
method = 'POST'
url = 'http://localhost:8080/api/v1/sellers/account/setup'
sortWeight = 3000000
id = '16ba66c1-5124-4616-a0d4-6c39a4baeceb'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU0OTE5NjcxLCJleHAiOjE3NTUwMDYwNzF9.xAeraXhZnuIK4Kn4fPvfnmc5Tnaei6R3JMBAzLSyd1A'

[body]
type = 'JSON'
raw = '''
{
  "businessDetails": {
    "businessName": "TechMart Solutions",
    "businessEmail": "contact@techmart.com",
    "businessAddress": "123 Market Street, New York, NY 10001",
    "businessNumber": "+1-555-123-4567",
    "businessImage": "https://example.com/images/techmart-logo.png",
    "businessBanner": "https://example.com/images/techmart-banner.jpg"
  },
  "bankDetails": {
    "accountNumber": "9876543210",
    "accountHolderName": "John Doe",
    "bankName": "Bank of America"
  }
}'''
```
