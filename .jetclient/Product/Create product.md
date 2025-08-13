```toml
name = 'Create product'
method = 'POST'
url = 'http://localhost:8080/api/v1/sellers/create-product'
sortWeight = 1000000
id = '3f9a095a-0aa5-410f-8253-705e629c5737'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXdvb2RfcGFydG5lcjJAZ21haWwuY29tIiwicm9sZSI6WyJTRUxMRVIiXSwiaWF0IjoxNzU1MDk3MzE0LCJleHAiOjE3NTUxODM3MTR9.8IHUmbgWhSCbQxoEkK8V-c8Unj0EFaceeXLLYeZSR78'

[body]
type = 'JSON'
raw = '''
{
  "name": "Wireless Bluetooth Headphones",
  "description": "High-quality over-ear headphones with noise cancellation.",
  "price": 15000,
  "mrpPrice": 20000,
//  "discount": 25,
  "stockQuantity": 50,
//  "inStock": true,
//  "status": "IN_STOCK",
  "category": [
     "76b67f8e-2669-4fc4-a105-992bd75d9dca",
  ],
  "size": "Standard",
  "color": "Black",
  "images": [
    "https://example.com/images/headphones-front.jpg",
    "https://example.com/images/headphones-side.jpg"
  ] 
}'''
```
