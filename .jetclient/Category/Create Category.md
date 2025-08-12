```toml
name = 'Create Category'
method = 'POST'
url = 'http://localhost:8080/api/v1/category'
sortWeight = 1000000
id = '26912382-b210-4c3f-874e-672cfefe7fba'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU1MDA4OTEwLCJleHAiOjE3NTUwOTUzMTB9.rKaByF8AJvykUqskpJHJh-ZwjEhKQRZtUxcln9VZcMo'

[body]
type = 'JSON'
raw = '''
{
  "name": "Home & Kitchen",
}'''
```
