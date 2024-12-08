# SmartCalcV3.0-on-Java

## Implementation of SmartCalc

## Usage

- To install the app on macOS, download the installer from the [Releases](https://github.com/HeisYenberg/SmartCalcV3.0-on-Java/releases/tag/3.0)
  section on GitHub.

- To run the app locally, use:
  ```shell
  mvn clean install javafx:run
  ```  
- To view tests, run:
  ```shell
  mvn clean test
  ```  

### Overview of "SmartCalc" Functions

| **Operator Name** | **Arithmetic Operators** | **Function Description**   | **Functions** |  
|-------------------|--------------------------|----------------------------|---------------|  
| Brackets          | (a + b)                  | Computes cosine            | `cos(x)`      |  
| Addition          | a + b                    | Computes sine              | `sin(x)`      |  
| Subtraction       | a - b                    | Computes tangent           | `tan(x)`      |  
| Multiplication    | a * b                    | Computes arc cosine        | `acos(x)`     |  
| Division          | a / b                    | Computes arc sine          | `asin(x)`     |  
| Power             | a ^ b                    | Computes arc tangent       | `atan(x)`     |  
| Modulus           | a mod b                  | Computes square root       | `sqrt(x)`     |  
| Unary Plus        | +a                       | Computes natural logarithm | `ln(x)`       |  
| Unary Minus       | -a                       | Computes common logarithm  | `log(x)`      |  

### New Features in V3.0

1. **Logging of Operations**:
    - All operations performed are automatically logged for reference.

2. **History of Operations**:
    - A detailed list of previously performed calculations is displayed.
    - Users can reuse expressions by clicking on them.

### Functionality

#### Graph of a Function:

- Generates a graph for a user-provided mathematical function.

#### Credit Calculator:

- **Input**:
    - Total credit amount
    - Term
    - Interest rate
    - Credit type (annuity or differentiated)

- **Output**:
    - Monthly payment
    - Overpayment on credit
    - Total payment

#### Deposit Calculator:

- **Input**:
    - Deposit amount
    - Deposit term
    - Interest rate
    - Tax rate
    - Payment periodicity
    - Capitalization of interest
    - Replenishments list
    - Partial withdrawals list

- **Output**:
    - Accrued interest
    - Tax amount
    - Final deposit amount at the end of the term  
