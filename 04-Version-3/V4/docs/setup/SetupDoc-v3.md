# Setup Documentation — Version 3: Basic Transaction (Deposit & Withdraw)

**Part:** P01 — Foundation
**Prerequisite versions completed:** v1, v2
**Estimated setup time:** 3-4 hours

---

## 1. Overview

Version 3 implements the first real business transaction (Deposit/
Withdraw) end-to-end through Controller -> Service -> DAO -> DB
layering (per ARCH02 §2). Introduces the `accounts` table (linked to
`users` via foreign key), enforces the "cannot withdraw more than
balance" business rule, and documents WebSphere's ClassLoader policy
using this project's own PostgreSQL Shared Library as a concrete,
already-built example.

## 2. VM Setup

No new VMs. Reuses `dsb-dmgr` and `dsb-db` from Versions 1-2.

## 3. Pre-Deployment Checklist

- [x] Version 2 SetupDoc completed and verified
- [x] VM snapshot taken (pre-v3)
- [x] Git branch used for this version's work

## 4. Step-by-Step Configuration

### 4.1 WebSphere Admin Console Steps

1. Redeployed via Update Application (Replace the entire application,
   digistack-bank-v3.ear).
   **Expected result:** Clean restart, application object name
   unchanged (still `digistack-bank-v1`), all features functional.

2. Reviewed Class loading and update detection settings (Applications
   > All Applications > digistack-bank-v1 > Class loading and update
   detection).
   **Expected result / actual observed:** Class loader order = "Classes
   loaded with parent class loader first" (Parent First). WAR class
   loader policy = per-module (WAS default). Concrete example
   documented: PostgreSQL JDBC driver (registered as Shared Library at
   server1's class loader, Version 1 Sprint... setup) is visible to
   our WAR module's classes (AccountDao, LoginServlet, etc.) purely via
   upward Parent-First delegation - no additional per-app configuration
   was needed for our DAO classes to successfully call
   DriverManager.getConnection() and find the driver.

### 4.2 wsadmin / Command-Line Steps

Application update (same pattern as Version 2 Sprint 5):
```python
AdminApp.update('digistack-bank-v1', 'app', '[ -operation update -contents /home/wasadmin/deploy/digistack-bank-v3.ear ]')
AdminConfig.save()
```

ClassLoader policy inspection (read-only):
```python
app = AdminConfig.getid('/Deployment:digistack-bank-v1/')
deploymentObject = AdminConfig.showAttribute(app, 'deployedObject')
classloader = AdminConfig.list('Classloader', deploymentObject)
print AdminConfig.show(classloader)
```

### 4.3 Database Changes

Migration script: `db/migrations/V3__create_accounts.sql`
Rollback script: `db/rollback/V3__create_accounts_rollback.sql`

Creates `accounts` table (id, user_id [FK to users.id], account_number,
balance [NUMERIC(15,2)], created_at). Seed account DSB-ACC-0001, linked
to testuser1 (user_id=1), starting balance 1000.00.

Balance stored as NUMERIC, never FLOAT/DOUBLE - exact decimal
representation required for any monetary value (documented rationale
in migration script and AccountService).

Run via:
```bash
psql -h localhost -U digistack_app -d digistack_bank -f V3__create_accounts.sql
```
**Expected confirmation:** `CREATE TABLE` then `INSERT 0 1`.

### 4.4 Application Deployment

Build command: `mvn clean package`. `digistack-bank-ear/pom.xml`'s
`<finalName>` updated to `digistack-bank-v3`. Produces
`digistack-bank-ear/target/digistack-bank-v3.ear`.

New classes added this version:
- `com.digistack.bank.dao.AccountDao` (getBalance, updateBalance,
  getAccountNumberForUsername)
- `com.digistack.bank.service.AccountService` (deposit, withdraw -
  enforces overdraft rule)
- `com.digistack.bank.exception.InsufficientFundsException` (checked
  exception for overdraft rejection)
- `com.digistack.bank.controller.AccountController` (mapped to
  /Account)

Temporary test servlets (DaoTestServlet, ServiceTestServlet) created
during Sprints 2-3 for layer-by-layer verification, deleted in Sprint 4
once the real Controller/UI existed - not part of the final deliverable.

New JSP: `Account.jsp` (Balance view + Deposit/Withdraw forms).

Deployed via Update Application per §4.1 Step 1 above.

## 5. Verification Steps

Full detail in `TestCases-v3.md`. Summary: 17/17 test cases Pass,
including v1/v2 regression (TC-v3-16), FK constraint negative test
(TC-v3-02), overdraft rejection at both Service layer (TC-v3-07) and
real UI (TC-v3-11), and non-numeric input handling (TC-v3-12).

## 6. Rollback Procedure

- **Application:** Update Application again with `digistack-bank-v2.ear`.
- **Database:** Run `db/rollback/V3__create_accounts_rollback.sql` -
  drops `accounts` table only. Does not affect `app_config` or `users`.
- **WebSphere config:** Restore from `v3-baseline-config.zip` if needed
  (unlikely - no new WAS-level config objects created this version,
  only application-level classes/JSPs).

## 7. Known Issues / Troubleshooting

- `AccountDao.getAccountNumberForUsername` assumes exactly one account
  per user - correct for this version's scope, documented as a
  deliberate simplification (real banks support multiple accounts per
  customer; explicitly out of scope for P01).
- No concurrency/locking on balance updates yet (two simultaneous
  requests against the same account could theoretically race) -
  acceptable pre-clustering per P01_Foundation.md's Technical Debt
  note; revisited at Version 5 (clustering) and P05 v38.
- No other issues encountered.

## 8. Sign-off

- [x] Setup completed successfully
- [x] All verification steps passed (17/17 test cases, TestCases-v3.md)
- [x] Documentation reviewed for accuracy - followed start to finish