# Assignment 9: Banking System

A comprehensive banking system demonstrating core Object-Oriented Programming concepts in Java.

## Files

- **Assignment9Account.java** - Base account class
- **Assignment9SavingsAccount.java** - Savings account with interest
- **Assignment9CurrentAccount.java** - Current account with overdraft
- **Assignment9Demo.java** - Demonstration and testing

## OOP Concepts Demonstrated

### Encapsulation
- Private fields with public getters/setters
- Data validation in setter methods
- Controlled access to account information

### Constructor Overloading & Chaining
- Multiple constructors with different parameters
- Constructor chaining using `this()` and `super()`
- Default values for optional parameters

### Inheritance
- SavingsAccount extends Assignment9Account
- CurrentAccount extends Assignment9Account
- Specialized behavior in subclasses

### Method Overriding
- @Override annotation for clarity
- SavingsAccount.display() shows interest details
- CurrentAccount.withdraw() allows overdraft
- CurrentAccount.display() shows overdraft status

### Polymorphism
- Store different account types in single List<Assignment9Account>
- Call display() on different types - appropriate method executes
- Extensible design for adding new account types

### Validation & Exception Handling
- Assertions for internal consistency
- IllegalArgumentException for invalid operations
- Proper error messages for debugging

## Features

### Assignment9Account (Base Class)
- Account number, owner name, balance
- Deposit and withdraw operations
- Balance validation
- Display account information

### Assignment9SavingsAccount
- Interest rate field
- Apply interest to balance
- Show potential annual interest
- Default 2.5% interest rate

### Assignment9CurrentAccount
- Overdraft limit field
- Allow withdrawals beyond balance (up to limit)
- Show available funds (balance + overdraft)
- Display overdraft status

## Compilation & Execution

```bash
javac Assignment9Account.java Assignment9SavingsAccount.java Assignment9CurrentAccount.java Assignment9Demo.java
java Assignment9Demo
```

## Usage Examples

```java
Assignment9Account basic = new Assignment9Account("ACC001", "John Doe", 1000);
Assignment9SavingsAccount savings = new Assignment9SavingsAccount("SAV001", "Jane", 5000, 3.5);
Assignment9CurrentAccount current = new Assignment9CurrentAccount("CUR001", "Bob", 2000, 10000);

basic.deposit(500);
basic.withdraw(200);

savings.applyInterest();

current.withdraw(5000);
```

## Key Design Principles

✓ Encapsulation through private fields  
✓ Constructor chaining reduces duplication  
✓ Inheritance enables code reuse  
✓ Method overriding provides specialized behavior  
✓ Polymorphism allows flexible design  
✓ Validation ensures data integrity  
✓ Clear, readable code without excessive comments  

## Output

The demo creates three account types, performs various operations, demonstrates polymorphic behavior, and shows proper error handling for invalid operations.
