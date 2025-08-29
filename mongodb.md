# Mongo

## Queries
Queries using mongo-compass with the mongosh shell.

### One Customer

```javascript
db["customers"].findOne()

{
    _id: 'cbbc1a67-8460-463d-b28e-184778d699cc',
        name: 'John Doe',
        email: 'john@example.com',
        phone: '123-456-7890',
        address: '123 Main St, Anytown, USA',
        orders: [],
        _class: 'com.bsoft.reactive.model.Customer'
}
```

### One Order

```javascript
db["orders"].findOne()

{
  _id: '2fd1537a-280c-4885-b04e-6a439437c522',
  customerId: 'cbbc1a67-8460-463d-b28e-184778d699cc',
  productName: 'Mouse',
  quantity: 2,
  price: '29.99',
  totalAmount: '59.98',
  orderDate: 2025-08-29T16:54:13.495Z,
  status: 'COMPLETED',
  _class: 'com.bsoft.reactive.model.Order'
}
```
### A specific Order
```javascript
db.customers.findOne({
    _id: '97bd3f6a-4a8c-4482-b76b-06022055f161'
})
```
### Customers with orders
```javascript
.aggregate([
  // 1. Sort customers by name
  {
    $sort: { "name": 1 }
  },
  
  // 2. Join customer and order data
  {
    $lookup: {
      from: "orders",
      localField: "_id",
      foreignField: "customerId",
      as: "customerOrders",
      pipeline: [
        {
          $sort: { "productName": 1 }
        },
        {
          $project: {
            _id: 0,
            productName: "$productName",
            quantity: "$quantity",
            price: { $toDouble: "$price" },
            totalPrice: { $multiply: ["$quantity", { $toDouble: "$price" }] }
          }
        }
      ]
    }
  },
  
  // 3. Reshape the final output
  {
    $project: {
      _id: 0,
      name: "$name",
      address: "$address",
      orders: "$customerOrders"
    }
  }
])

{
  name: 'Bas Crompvoets',
  address: 'Bruglaan 12 Rhenen',
  orders: [
    {
      productName: 'chair',
      quantity: 6,
      price: 75,
      totalPrice: 450
    },
    {
      productName: 'dish wasser',
      quantity: 1,
      price: 850,
      totalPrice: 850
    },
    {
      productName: 'hammer',
      quantity: 2,
      price: 15,
      totalPrice: 30
    },
    {
      productName: 'photo camera',
      quantity: 1,
      price: 1250,
      totalPrice: 1250
    },
    {
      productName: 'skrew driver',
      quantity: 4,
      price: 12.5,
      totalPrice: 50
    }
  ]
}
{
  name: 'Bob Johnson',
  address: '789 Pine Rd, Nowhere, USA',
  orders: [
    {
      productName: 'Tablet',
      quantity: 1,
      price: 199.99,
      totalPrice: 199.99
    }
  ]
}
{
  name: 'Jane Smith',
  address: '456 Oak Ave, Somewhere, USA',
  orders: [
    {
      productName: 'Keyboard',
      quantity: 1,
      price: 79.99,
      totalPrice: 79.99
    },
    {
      productName: 'Monitor',
      quantity: 1,
      price: 299.99,
      totalPrice: 299.99
    }
  ]
}
{
  name: 'John Doe',
  address: '123 Main St, Anytown, USA',
  orders: [
    {
      productName: 'Laptop',
      quantity: 1,
      price: 999.99,
      totalPrice: 999.99
    },
    {
      productName: 'Mouse',
      quantity: 2,
      price: 29.99,
      totalPrice: 59.98
    }
  ]
}
{
  name: 'Peter van Proosdijk',
  address: 'Kerkewijk 112 Veenendaal',
  orders: [
    {
      productName: 'drill',
      quantity: 1,
      price: 250,
      totalPrice: 250
    },
    {
      productName: 'iphone',
      quantity: 2,
      price: 560,
      totalPrice: 1120
    },
    {
      productName: 'skrew driver',
      quantity: 6,
      price: 12.5,
      totalPrice: 75
    },
    {
      productName: 'smart tv',
      quantity: 3,
      price: 1450,
      totalPrice: 4350
    },
    {
      productName: 'vacuumcleaner',
      quantity: 1,
      price: 300,
      totalPrice: 300
    }
  ]
}
{
  name: 'Rik Sprenkels',
  address: 'Boslaan 5 Amerongen',
  orders: [
    {
      productName: 'chair',
      quantity: 6,
      price: 75,
      totalPrice: 450
    },
    {
      productName: 'hammer',
      quantity: 2,
      price: 15,
      totalPrice: 30
    },
    {
      productName: 'skrew driver',
      quantity: 6,
      price: 12.5,
      totalPrice: 75
    },
    {
      productName: 'skrews m4',
      quantity: 4,
      price: 10,
      totalPrice: 40
    },
    {
      productName: 'tiles',
      quantity: 40,
      price: 12.5,
      totalPrice: 500
    }
  ]
}
```