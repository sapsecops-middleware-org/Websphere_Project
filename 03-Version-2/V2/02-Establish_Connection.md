# Deploy the Schema to DB from DMGR VM
### Deploy in Localhost {DB server} it create Login Table
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -f V2__create_users.sql
```
#### Verification
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "SELECT id, username, password_hash, salt, created_at FROM users;"
```
Expected output:
```
id | username  |                        password_hash                        |               salt               |         created_at
----+-----------+--------------------------------------------------------------+----------------------------------+----------------------------
  1 | testuser1 | 4c7b3a8fb9e428599fb04998b0f08228112195552568f9bb057e8e8dc22566e1 | 7c9815e1a9a06846f91a14fa2ae60e0c |  <timestamp>
```
#### Verify Tables are created or Not
```
psql -h 192.168.10.30 -U digistack_app -d digistack_bank -c "\dt"
```
Expected output:
```
app_config
users
```
