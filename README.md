# Fawry Internship E-Commerce System
This is a simple but complete console-based e-commerce system implemented in Java. It was built as a solution for the Fawry Rise Journey Full Stack Internship Challenge. 

The project simulates how a customer can browse products, add items to a cart, and checkout handling edge cases like expiry dates, shipping, and insufficient stock or balance.

## UML Class Diagram

![UML Fawary Challenge-1](https://github.com/user-attachments/assets/10673da7-a559-47bf-b2b8-95769de91467)


# Example Console Output
```** Shipment notice **
Shipping rate is: 0.336
2x Cheese        0.6kg
2x T.V           4.0kg
Total package weight: 4.60kg

** Checkout Receipt **
2x Cheese         200.00
2x T.V            700.00
2x Mobile Scratch 40.00
----------------------
Subtotal      : 940.00
Shipping      : 6.13
Total Paid    : 946.13
Balance Left  : 14053.87
```


## Test Cases Included
The main method runs the following five test scenarios:

Test Case	Description
- Test 1	Normal successful checkout with multiple product types
- Test 2	Cart contains an expired product (Pizza)
- Test 3	Out of stock quantity requested
- Test 4	Empty cart checkout attempt
- Test 5	Insufficient balance to complete the order

