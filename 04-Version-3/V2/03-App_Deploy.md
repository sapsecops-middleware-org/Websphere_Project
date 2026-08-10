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
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/DaoTest
```
Enter
```
Username: testuser1
Password: Test@1234
```
Expected result 
```
Step 1 - getBalance: 1000.00
Step 2 - updateBalance called with: 1050.00
Step 3 - getBalance after update: 1050.00
Step 4 - restored to original: 1000.00

DAO TEST PASSED
```
