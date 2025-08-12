```toml
name = 'Update Business Details'
method = 'PATCH'
url = 'http://localhost:8080/api/v1/sellers/account/business-details'
sortWeight = 6000000
id = '96f8976a-cbac-4818-aef5-869bcc51357f'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU0OTE5NjcxLCJleHAiOjE3NTUwMDYwNzF9.xAeraXhZnuIK4Kn4fPvfnmc5Tnaei6R3JMBAzLSyd1A'

[body]
type = 'JSON'
raw = '''
{
  "businessName": "Dawood Solutions",
  "businessEmail": "contact@techmart.com",
//  "businessAddress": "123 Market Street, New York, NY 10001",
  "businessNumber": "082435678645",
  "businessImage": "https://example.com/images/techmart-logo.png",
  "businessBanner": "https://example.com/images/techmart-banner.jpg"
}'''
```
