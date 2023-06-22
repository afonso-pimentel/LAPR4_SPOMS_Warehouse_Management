# Glossary


## Entities

| **_TEA_**    | **_Description_**                                        |
|:-------------|:---------------------------------------------------------|
| **Customer** | Customer that will place orders                          |
| **Order**    | Set of products ordered by a customer                    |
| **Product**  | Product that can be ordered by a customer                |
| **Category** | Category of a product, a category can have a subcategory |
| **Survey**   | Set of questions to be issued to eligible customers      |


## Aggregates

### Customer (value objects)
| **_TEA_**   | **_Description_**       |
|:------------|:------------------------|
| **Address** | Address of the customer |

### Order (value objects)
| **_TEA_**           | **_Description_**                                          |
|:--------------------|:-----------------------------------------------------------|
| **Order status**    | Order status (pending, processing,...)                     |
| **Shipment method** | Shipping method assigned to ship the products in the order |


### Product (value objects)
| **_TEA_**               | **_Description_**                                                                     |
|:------------------------|:--------------------------------------------------------------------------------------|
| **Description**         | Description of the product                                                            |
| **Barcode**             | Barcode that uniquely identifies the product                                          |
| **Item**                | Atomic unit of product, this represents the single product in a set of equal products |
| **Order Authorization** | Product availability for ordering                                                     |


### Warehouse (value objects)
| **_TEA_**     | **_Description_**                                            |
|:--------------|:-------------------------------------------------------------|
| **Warehouse** | Space where products are stored in shelves, rows, and aisles |
| **AGV**       | Robot that takes actions on orders                           |
| **Storage Area**       | Single unit of storage where a product may be stored                          |


### Survey (value objects)
| **_TEA_**     | **_Description_**                                            |
|:--------------|:-------------------------------------------------------------|
| **Answer** | Answer to a survey question |
| **Rules** | Rules that survey follow  |
| **Question** | Single question in survey  |

---
