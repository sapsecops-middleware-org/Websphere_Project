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
```
Admin Console: Applications → All Applications → your app → Update → Replace the entire application → browse to digistack-bank-v3.ear → Finish → Save → Stop/Start.
```
## Examine and Document the Actual ClassLoader Policy
<img width="694" height="384" alt="image" src="https://github.com/user-attachments/assets/11a9be90-4e82-4b1e-a58e-52427f663ac6" />


# Verification
1. Open a browser and go to:
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Dashboard.jsp
```
1. Enter
```
Username: testuser1
Password: Test@1234
```
2. click on
```
View Balance / Deposit / Withdraw
```
3. Account page shows
```
Account Number: DSB-ACC-0001
Current Balance: 1000.00.
```
