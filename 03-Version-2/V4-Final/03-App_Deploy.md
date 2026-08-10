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

# Re-Deploy the Application
<img width="709" height="365" alt="image" src="https://github.com/user-attachments/assets/a1e0e6b2-f643-44e8-9cbe-166a7fe3a226" />



# Verification-1
1. Open a browser and go to:
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Login
```
Enter
```
Username: testuser1
Password: Test@1234
```
Expected result 
```
Login successful. Welcome, testuser1!
Last login: 2026-08-08 14:32:07  (your actual timestamp)
Session ID: 0000AbC...  (some real session ID string)
```
# Verification-2
1. Open a browser and go to:
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Dashboard.jsp
```
Expected result ==> Immediately redirected to the Login page — you never see the dashboard content.
