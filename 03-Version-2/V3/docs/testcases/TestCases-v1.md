# Test Cases — Version 1: Project Setup & Enterprise Architecture

**Part:** P01 — Foundation
**Prerequisite versions' regression pack:** None

## Test Case Table

| ID | Description | Type | Priority | Steps | Expected Result | Actual Result | Status | Notes |
|---|---|---|---|---|---|---|---|---|
| TC-v1-01 | Admin Console loads | Functional | Critical | Hit https://192.168.10.10:9043/ibm/console | Login page renders | | | |
| TC-v1-02 | wasadmin login succeeds | Functional | Critical | Enter wasadmin credentials | Console home loads, no auth error | | | |
| TC-v1-03 | server1 status = Started | Functional | Critical | Servers → server1 in console OR serverStatus.sh | Status = Started / green arrow | | | |
| TC-v1-04 | backupConfig baseline exists | Functional | High | ls /backup/backupConfig-pre-v1.zip | File present, non-zero size | | | |
| TC-v1-05 | SOE01 kernel params applied | Functional | High | sysctl net.core.somaxconn vm.swappiness fs.file-max | Values match SOE01 §4 | | | |