# Verification-1 ==> Confirm in the database that the balance really is back to 1000.00:

```
psql -h localhost -U digistack_app -d digistack_bank -c "SELECT * FROM accounts;"
```
Expected output ==> balance = 1000.00, unchanged.

# Verification-2 ==> Also check the log for the rejection line proving it happened before any DB write:
```
grep "REJECTED" /apps/IBM/WebSphere/AppServer/profiles/devdsbinappserver01/logs/server1/SystemOut.log
```

