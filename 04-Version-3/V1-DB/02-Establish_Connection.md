# Deploy the Schema to DB from DMGR VM

### Deploy in 192.168.10.30 {DB server} it create Login Table
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -f V3__create_accounts.sql
```
#### Verification-1
Confirm testuser1's real id, to validate our assumption:
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "SELECT id, username FROM users;"
```
#### Verification-2
Confirm the seed account and its link:
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "SELECT * FROM accounts;"
```
#### Verification-3
Confirm the foreign key genuinely rejects a bad user_id (negative test - proves the constraint is real, not decorative):
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "INSERT INTO accounts (user_id, account_number, balance) VALUES (9999, 'DSB-ACC-BAD', 100.00);"
```

Expected output ==> should FAIL with something like ERROR: insert or update on table "accounts" violates foreign key constraint "fk_accounts_users"

#### Verify Tables are created or Not
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "\dt"
```
Expected output:
```
app_config
users
accounts
```
