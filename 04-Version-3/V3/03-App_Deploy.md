# Build and Deploy the Application

Clone the Repository
```
git clone <Repo-URL>
```
## Build the Application

From the project directory.
```
mvn clean package
```
Generated artifact ==> digistack-bank-ear/target/digistack-bank-v2.ear

# Deploy the Application
<img width="680" height="280" alt="image" src="https://github.com/user-attachments/assets/0f30401c-b7b9-4f05-938b-34001dd82db2" />


# Verification
1. Open a browser and go to:
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/ServiceTest
```
Enter
```
Username: testuser1
Password: Test@1234
```
Expected result 
```
Test 1 - Deposit 100.00: new balance = 1100.00 (expected 1100.00) -> PASS
Test 2 - Withdraw 50.00: new balance = 1050.00 (expected 1050.00) -> PASS
Test 3 - Over-withdrawal correctly rejected: Withdrawal of 999999.00 exceeds available balance of 1050.00
Test 3 result -> PASS

Balance restored to original 1000.00 for clean state.
```
