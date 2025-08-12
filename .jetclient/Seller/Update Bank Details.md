```toml
name = 'Update Bank Details'
method = 'PATCH'
url = 'http://localhost:8080/api/v1/sellers/account/bank'
sortWeight = 5000000
id = 'f1d2d160-afa7-43b4-b1e7-6830e7f8368f'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU0OTE5NjcxLCJleHAiOjE3NTUwMDYwNzF9.xAeraXhZnuIK4Kn4fPvfnmc5Tnaei6R3JMBAzLSyd1A'

[body]
type = 'JSON'
raw = '''
{
  "accountNumber": "9876543210",
  "accountHolderName": "Dawood sulaimon",
  "bankName": ""
}'''
```
