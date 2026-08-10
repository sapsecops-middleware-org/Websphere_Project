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

## Verification-1 ==> Deposit

```
Enter 250.00 in the Deposit field, click Deposit.
```
Expected result ==> Green message "Deposit of 250.00 successful. New balance: 1250.00" — and the displayed Current Balance updates to 1250.00.

## Verification-2 ==> withdraw
```
Enter 100.00 in the Withdraw field, click Withdraw.
```
Expected result: Green message "Withdrawal of 100.00 successful. New balance: 1150.00".

## Verification-3 ==> Over-Withdrawal (Negative Test)
```
Enter 99999.00 in the Withdraw field, click Withdraw.
```
Expected result: Red error message showing the InsufficientFundsException text — balance stays unchanged at 1150.00.

## Verification-4 ==> Restore to Original Balance (Housekeeping)
```
Withdraw 150.00 to bring the balance back to 1000.00
```

## Verification-5 ==> Direct Access Guard
1. Logout

2. open Browser
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Account
```
Expected result ==> Redirected to Login (same guard pattern as Dashboard).
