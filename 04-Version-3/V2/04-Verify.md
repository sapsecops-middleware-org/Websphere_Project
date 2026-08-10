# Verification-1
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

# Verification-1 ==> Check DB

```
psql -h localhost -U digistack_app -d digistack_bank -c "SELECT * FROM accounts;"
```
Expected output ==> balance = 1000.00, unchanged.
