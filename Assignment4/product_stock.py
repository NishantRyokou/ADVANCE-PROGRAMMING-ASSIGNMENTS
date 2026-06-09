products = [
    {"name": "Laptop", "stock": 15},
    {"name": "Mouse", "stock": 5},
    {"name": "Keyboard", "stock": 8},
    {"name": "Monitor", "stock": 12},
    {"name": "USB Cable", "stock": 3},
    {"name": "Headphones", "stock": 20}
]

print("Products with low stock (less than 10):")
for product in products:
    if product["stock"] < 10:
        print(f"{product['name']}: {product['stock']}")
