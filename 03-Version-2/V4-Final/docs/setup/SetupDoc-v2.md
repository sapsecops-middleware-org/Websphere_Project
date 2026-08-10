# Setup Documentation — Version 2: Login & Session

**Part:** P01 — Foundation
**Prerequisite versions completed:** v1
**Estimated setup time:** 2-3 hours

---

## 1. Overview

Version 2 introduces authentication and HTTP session management. Adds a
`users` table (SHA-256 + salt password hashing), a Login/Logout flow,
and a Dashboard landing page showing "last login." Also exercises EAR
redeploy over a running application (v2 over v1) and JVM/Application
startup log sequence observation.

## 2. VM Setup

No new VMs. Reuses `dsb-dmgr` (WebSphere) and `dsb-db` (PostgreSQL)
from Version 1 — no new packages, no new ports.

## 3. Pre-Deployment Checklist

- [x] Version 1 SetupDoc completed and verified
- [x] VM snapshot taken (pre-v2)
- [x] Git branch used for this version's work

## 4. Step-by-Step Configuration

### 4.1 WebSphere Admin Console Steps

1. Reviewed default Session timeout setting (Applications > All
   Applications > digistack-bank-v1 > Web Module Properties > Session
   management). Confirmed default value present; no change made -
   deliberate tuning deferred to Version 9 per Sprint Plan.
   **Expected result:** Default timeout value observed and recorded.

2. Redeployed via Update Application (Applications > All Applications >
   digistack-bank-v1 > Update > Replace the entire application >
   digistack-bank-v2.ear > Finish > Save).
   **Expected result:** App restarts cleanly; application object name
   unchanged (digistack-bank-v1) despite new v2 EAR content - this is
   expected WebSphere behavior (app object name is independent of the
   EAR filename).

3. Stopped and started the application to apply changes.
   **Expected result:** Green arrow / Started status.

### 4.2 wsadmin / Command-Line Steps

Session manager inspection (read-only):
```python
app = AdminConfig.getid('/Deployment:digistack-bank-v1/')
print AdminConfig.list('SessionManager', app)
```

Application update (equivalent to GUI Update Application):
```python
AdminApp.update('digistack-bank-v1', 'app', '[ -operation update -contents /home/wasadmin/deploy/digistack-bank-v2.ear ]')
AdminConfig.save()
```

Application restart:
```python
AdminControl.invoke(AdminControl.completeObjectName('type=Application,name=digistack-bank-v1,*'), 'stop')
AdminControl.invoke(AdminControl.completeObjectName('type=ApplicationManager,process=server1,*'), 'startApplication', 'digistack-bank-v1')
```

### 4.3 Database Changes

Migration script: `db/migrations/V2__create_users.sql`
Rollback script: `db/rollback/V2__create_users_rollback.sql`

Creates `users` table (id, username, password_hash, salt, created_at).
Password hashing: SHA-256 with per-user random salt (documented
simplification for this learning project - BCrypt/Argon2 would be the
production-grade choice; not used here to keep Version 2's teaching
focus on session mechanics, not cryptographic library integration).

Seed user: `testuser1` / password `Test@1234` (plaintext documented in
migration script comment ONLY because this is a lab seed row - never
acceptable practice for a real production migration).

Run via:
```bash
psql -h localhost -U digistack_app -d digistack_bank -f V2__create_users.sql
```
**Expected confirmation:** `CREATE TABLE` then `INSERT 0 1`.

### 4.4 Application Deployment

Build command: `mvn clean package` from `digistack-bank-parent/` root.
`digistack-bank-ear/pom.xml`'s `<finalName>` updated from
`digistack-bank-v1` to `digistack-bank-v2` for this version. Produces
`digistack-bank-ear/target/digistack-bank-v2.ear`.

New classes added this version:
- `com.digistack.bank.util.PasswordUtil` (SHA-256+salt hashing utility)
- `com.digistack.bank.controller.LoginServlet` (mapped to /Login)
- `com.digistack.bank.controller.LogoutServlet` (mapped to /Logout)

New JSPs: `Login.jsp`, `Dashboard.jsp` (replaces Sprint 3's temporary
`LoginSuccess.jsp`, deleted this version).

Deployed via Update Application per §4.1 Step 2 above.

## 5. Verification Steps

Full detail in `TestCases-v2.md`. Summary: 14/14 test cases Pass,
including v1 regression (TC-v2-13) and negative tests for invalid
credentials, direct-access bypass attempt, and back-button-after-logout
(TC-v2-04/05/08/10).

## 6. Rollback Procedure

- **Application:** Update Application again, replacing with
  `digistack-bank-v1.ear` (retained from Version 1's build).
- **Database:** Run `db/rollback/V2__create_users_rollback.sql` -
  drops the `users` table. Note: this does NOT affect `app_config`
  (Version 1's table), consistent with each migration only touching
  what it created.
- **WebSphere config:** Restore from `v2-baseline-config.zip` via
  `restoreConfig.sh` if a config-level (not just app-level) rollback
  is needed - unlikely for this version, since no new WAS-level config
  objects (shared libraries, class loaders, etc.) were created.

## 7. Known Issues / Troubleshooting

- SHA-256+salt password hashing is intentionally not production-grade
  (BCrypt/Argon2 deferred - not currently scheduled for a specific
  later version; flagged here as a standing simplification of this
  learning project, not a planned-and-tracked Technical Debt item like
  the Direct JDBC -> JNDI item is).
- The Dashboard.jsp direct-access guard (`if (username == null...)`) is
  an application-level check only, not container-managed security -
  real enforcement is Version 10's topic. Documented as a known,
  deliberate limitation, not a defect.
- No other issues encountered.

## 8. Sign-off

- [x] Setup completed successfully
- [x] All verification steps passed (14/14 test cases, TestCases-v2.md)
- [x] Documentation reviewed for accuracy - followed start to finish