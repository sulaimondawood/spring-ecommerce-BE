```toml
name = 'Get all Category'
method = 'GET'
url = 'http://localhost:8080/api/v1/category'
sortWeight = 2000000
id = '387479ea-5096-4391-bbd8-f64439a8b996'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU1MDA4OTEwLCJleHAiOjE3NTUwOTUzMTB9.rKaByF8AJvykUqskpJHJh-ZwjEhKQRZtUxcln9VZcMo'

[body]
type = 'JSON'
raw = '''
{
  "name": "Home & Kitchen"
}'''
```
