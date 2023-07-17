# Online tea shop
Used technologies: 
Backend: Java, Spring Boot, Spring Security, Spring JPA, Hibernate, PostgreSQL, Lombok, Logback 
Frontend: HTML, CSS, Thymeleaf, Bootstrap
There are 3 types of users - guest, user and administrator.
## Permissions
  ### Guest
  Search content with filters described below
  ### User
  Has **guest** permissions, can place orders, leave reviews
  ### Administrator
  Has the permissions of **guest** and **user**, and can include, delete and change the description/characteristics of products, delete and significantly change users
## Sorting and searching
  The product is searched by name.
  It is possible to sort products by
  - Price
  - Quantity in stock
  - Date added
## Adding a product
When adding a product, the administrator must specify the characteristics:
  1. Price
  2. Manufacturer
  3. Type of tea
  4. Quantity
  5. Title
  6. Description
  7. Picture link
## Personal account and product selection
  The user selects a product, adds it to the cart, then can place an order
  ### Registration
  You must enter your email, age, first/last name and password
  ### Login
  You must provide a password and email
## Reviews
  Authorized users can leave feedback on the product in the form of a rating from 1 to 5 and a comment.
