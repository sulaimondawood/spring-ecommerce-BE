```toml
name = 'Create User'
method = 'POST'
url = 'http://localhost:8080/api/v1/auth/register'
sortWeight = 1000000
id = 'e9fe0550-f0f3-4c7e-a32f-8ba1824ba0f6'

[body]
type = 'JSON'
raw = '''
{
  fullname: "Dawood Sulaimon",
  "email": "dawood_partner@gmail.com",
  "password": "123456789",
//  role: "CUSTOMER",
  role: "PARTNER",
  phoneNumber: "08134567654"
}'''
```
