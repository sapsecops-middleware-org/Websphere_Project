# Test Cases — Version 3: Basic Transaction (Deposit & Withdraw)

**Part:** P01 — Foundation
**Prerequisite versions' regression pack:** TestCases-v1.md, TestCases-v2.md

## Test Case Table

| ID | Description | Type | Priority | Steps | Expected Result | Actual Result | Status | Notes |
|---|---|---|---|---|---|---|---|---|
| TC-v3-01 | accounts table created, seed account linked to testuser1 via FK | Functional | Critical | SELECT * FROM accounts | user_id=1, account_number='DSB-ACC-0001', balance=1000.00 | Confirmed | Pass | |
| TC-v3-02 | Foreign key constraint genuinely rejects invalid user_id | Negative | Critical | INSERT with user_id=9999 | FK violation error raised | Confirmed | Pass | |
| TC-v3-03 | AccountDao.getBalance returns correct value | Functional | Critical | DaoTest servlet Step 1 | Returns 1000.00 | Confirmed | Pass | (test servlet since removed) |
| TC-v3-04 | AccountDao.updateBalance persists correctly | Functional | Critical | DaoTest servlet Steps 2-3 | Update reflected on next read | Confirmed | Pass | (test servlet since removed) |
| TC-v3-05 | AccountService.deposit increases balance correctly | Functional | Critical | ServiceTest servlet Test 1 | 1000.00 + 100.00 = 1100.00 | Confirmed | Pass | (test servlet since removed) |
| TC-v3-06 | AccountService.withdraw decreases balance correctly | Functional | Critical | ServiceTest servlet Test 2 | 1100.00 - 50.00 = 1050.00 | Confirmed | Pass | (test servlet since removed) |
| TC-v3-07 | Over-withdrawal correctly rejected via InsufficientFundsException | Negative | Critical | ServiceTest servlet Test 3 | Exception thrown, balance unchanged | Confirmed | Pass | (test servlet since removed) |
| TC-v3-08 | Real UI: Balance displays correctly on Account.jsp | Functional | Critical | Login, view Account page | Account Number and Current Balance shown correctly | Confirmed | Pass | |
| TC-v3-09 | Real UI: Deposit via form updates balance and shows success message | Functional | Critical | Submit Deposit form, 250.00 | Green success message, balance updates to 1250.00 | Confirmed | Pass | |
| TC-v3-10 | Real UI: Withdraw via form updates balance and shows success message | Functional | Critical | Submit Withdraw form, 100.00 | Green success message, balance updates correctly | Confirmed | Pass | |
| TC-v3-11 | Real UI: Over-withdrawal via form shows red error, balance unchanged | Negative | Critical | Submit Withdraw form, 99999.00 | Red error message, balance NOT changed | Confirmed | Pass | |
| TC-v3-12 | Non-numeric amount input handled gracefully (no crash) | Negative | Medium | Submit form with letters in amount field | Friendly error message, no stack trace shown | Confirmed | Pass | |
| TC-v3-13 | Direct access to /Account without login is blocked | Negative | High | Incognito, browse directly to /Account | Redirected to Login | Confirmed | Pass | |
| TC-v3-14 | v3 EAR redeployed cleanly over running v2 | Functional | Critical | Update Application with digistack-bank-v3.ear | App restarts, all features functional post-redeploy | Confirmed | Pass | |
| TC-v3-15 | ClassLoader policy documented with concrete example | Functional | Medium | Review Class loading and update detection settings | Parent First confirmed; PostgreSQL driver visibility via Shared Library (Layer 3) explained as the concrete example | Confirmed | Pass | |
| TC-v3-16 | v1/v2 regression: Home page and Login/Session still work after v3 redeploy | Regression | Critical | Browse /Home, Login, Dashboard | All function as before | Confirmed | Pass | |
| TC-v3-17 | backupConfig baseline captured (v3) | Functional | High | Run backupConfig.sh | Non-zero-size zip file present | Confirmed | Pass | |

## Sign-off

| Item | Status |
|---|---|
| All Critical cases passed | 11/11 Critical — Pass |
| All High cases passed | 2/2 High — Pass |
| No open Critical/High defects | None found |
| Regression subset re-run (v1+v2 pack) | Pass — TC-v3-16 confirms |
| Reviewed by | Venkatesh (self-review, per EPS01 §3.2a Solo Review Discipline) |
| Approved date | 2026-08-09 |