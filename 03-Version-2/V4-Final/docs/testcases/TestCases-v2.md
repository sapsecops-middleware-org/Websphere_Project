# Test Cases — Version 2: Login & Session

**Part:** P01 — Foundation
**Prerequisite versions' regression pack:** TestCases-v1.md

## Test Case Table

| ID | Description | Type | Priority | Steps | Expected Result | Actual Result | Status | Notes |
|---|---|---|---|---|---|---|---|---|
| TC-v2-01 | users table created via migration, seed user present | Functional | Critical | SELECT * FROM users | Row returned for testuser1, correct hash/salt | Confirmed | Pass | |
| TC-v2-02 | v2 migration runs cleanly against existing v1 database | Regression | Critical | \dt after migration | Both app_config and users tables present | Confirmed | Pass | |
| TC-v2-03 | Valid login succeeds | Functional | Critical | Login as testuser1/Test@1234 | Dashboard shown, "Welcome, testuser1!" | Confirmed | Pass | |
| TC-v2-04 | Invalid password rejected | Negative | Critical | Login with wrong password | "Invalid username or password." shown | Confirmed | Pass | |
| TC-v2-05 | Unknown username rejected with same generic message | Negative | High | Login with nonexistent username | Same generic error as TC-v2-04 (no username enumeration) | Confirmed | Pass | |
| TC-v2-06 | Session created on successful login | Functional | Critical | Login, check session ID + lastLogin in log | Session ID and lastLogin logged, matches Dashboard display | Confirmed | Pass | |
| TC-v2-07 | Last login timestamp displays correctly on Dashboard | Functional | High | Login, view Dashboard | Real timestamp shown, not hardcoded | Confirmed | Pass | |
| TC-v2-08 | Direct access to Dashboard.jsp without login is blocked | Negative | High | Incognito window, browse directly to Dashboard.jsp | Redirected to Login, no dashboard content shown | Confirmed | Pass | |
| TC-v2-09 | Logout invalidates session | Functional | Critical | Login, click Logout | Redirected to Login; log shows "Session invalidated" | Confirmed | Pass | |
| TC-v2-10 | Back button after logout does not show stale Dashboard | Negative | High | Logout, press browser Back | Redirected to Login again, not stale Dashboard content | Confirmed | Pass | |
| TC-v2-11 | v2 EAR redeployed cleanly over running v1 | Functional | Critical | Update Application with digistack-bank-v2.ear | App restarts, WSVR0028I "Started" logged for correct app | Confirmed | Pass | |
| TC-v2-12 | Startup log sequence captured and understood | Functional | Medium | Review SystemOut.log after redeploy | WSVR0024I -> WSVR0001I -> WSVR0028I -> SRVE0242I sequence observed | Confirmed | Pass | |
| TC-v2-13 | v1 regression: Home page live DB read still works after v2 redeploy | Regression | Critical | Browse to /Home | "Live DB Read: DigiStack Bank is live - Version 1" still shown | Confirmed | Pass | |
| TC-v2-14 | backupConfig baseline captured (v2) | Functional | High | Run backupConfig.sh | Non-zero-size zip file present | Confirmed | Pass | |

## Sign-off

| Item | Status |
|---|---|
| All Critical cases passed | 8/8 Critical — Pass |
| All High cases passed | 5/5 High — Pass |
| No open Critical/High defects | None found |
| Regression subset re-run (v1 pack) | Pass — TC-v2-13 confirms |
| Reviewed by | Venkatesh (self-review, per EPS01 §3.2a Solo Review Discipline) |
| Approved date | 2026-08-08 |