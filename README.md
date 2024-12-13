# banking-app

## Overview

The `banking-app` is a command-line banking application developed in Java. It provides users with the ability to manage their bank accounts, perform basic banking operations, and utilize advanced features for enhanced functionality.

## Features

### User Account Management
- **Signup**: Users can create new accounts by providing a unique username, password, first name, initial deposit amount, and a withdrawal limit.
- **Login**: Users can log in to their accounts by entering their username and password.
- **Logout**: Users can securely log out of their accounts.

### Basic Banking Operations
- **Show Balance**: Users can view their current account balance.
- **Deposit**: Users can deposit money into their accounts, adhering to a specified deposit limit.
- **Withdraw**: Users can withdraw money from their accounts within the specified withdrawal limit.
- **Reset Password**: Users can reset their password after verifying their original password.

### Advanced Features
- **Account Transfers**: Users can transfer funds between accounts using account IDs, with the recipient's name displayed during the transfer process.
- **Savings and Checking Accounts**: The application supports both savings and checking accounts, with overdraft functionality for checking accounts.
- **Multiple Account Types**: Different account types (Platinum, Gold, Silver) with varying deposit and withdrawal limits.
- **Overdraft Penalties**: Users can overdraft up to twice, with a penalty applied for each overdraft.
- **Favorite Feature**: Users can mark other users as favorites for easier transfers.
- **Account Deactivation**: Users can deactivate their accounts, preventing further login attempts.
- **Multiple Currencies**: Support for multiple currencies with currency conversion for transfers.
- **Transaction History**: Users can view a history of all transactions, filtered by date range if needed.
- **Password Encryption**: User passwords are securely hashed using a library like Bcrypt or Argon2.
- **User Roles**: Different roles (e.g., Banker, User) with specific access to features.
- **Enhanced UI/UX**: A user-friendly CLI interface with clear instructions and color-coded messages.

## Project Flow

### 1. Application Start
Upon starting the application, users are presented with the following menu options:
```
1. Signup
2. Login
3. Exit
```

### 2. Signup Flow
Users need to provide the following information to create an account:
- **Username**: Unique and lowercase
- **Password**: Minimum of 6 characters
- **First Name**
- **Initial Deposit Amount**
- **Withdraw Limit**

The application validates all input fields and prompts the user to log in after successful signup.

### 3. Login Flow
Users need to enter their username and password to log in. Upon successful login, the logged-in user menu is displayed.

## Logged-In User Menu
```
1. Show Balance
2. Deposit
3. Withdraw
4. Reset Password
5. Logout
```

### Basic Upgrades
- Additional validation for usernames, passwords, and input types.
- Universal account limits for deposits and withdrawals.
- PIN code support for user requests.
- Account transfers with recipient name display.
- Savings and checking account implementation.

### Advanced Upgrades
- Different account types with varying limits.
- Overdraft functionality with penalties.
- Lockout on failed login attempts.
- Favorite feature for easier transfers.
- Account deactivation support.
- Multi-currency support.
- Comprehensive transaction history.
- Password encryption.
- User roles with specific access to features.
- Enhanced UI/UX for a better user experience.

## Submission Requirements
1. Submit the source code with proper documentation and comments.
2. Include a README file with:
    - A brief description of the project.
    - Instructions on how to compile and run the application.
3. Ensure the code is modular and adheres to OOP principles.

## Evaluation Criteria
- Correctness: The application behaves as expected for all scenarios.
- Code Quality: Clean, readable, and well-documented code.
- Features: Implementation of all required and advanced features.
- Input Validation: Proper handling of invalid input.

---
