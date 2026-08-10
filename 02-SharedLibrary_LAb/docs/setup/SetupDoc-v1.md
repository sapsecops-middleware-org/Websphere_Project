# Setup Documentation — Version 1: Project Setup & Enterprise Architecture

**Part:** P01 — Foundation
**Prerequisite versions completed:** None (first version)
**Estimated setup time:** 2–3 hours (VM provision + WAS install)

---

## 1. Overview
Version 1 establishes the base infrastructure: a Rocky Linux 8.x VM
(dsb-dmgr) provisioned against the SOE01 golden image, WebSphere ND
9.0.3 installed via IBM Installation Manager, a standalone AppServer
profile (AppSrv01) created, and the Admin Console confirmed reachable.
No application code is deployed in Sprint 1.

## 2. VM Setup
- VM: dsb-dmgr (192.168.10.10), 2 vCPU, 3 GB RAM, 40 GB thin disk
- OS: Rocky Linux 8.x minimal install
- Packages: per SOE01 §2 (tar, unzip, wget, curl, net-tools, bind-utils,
  lsof, sysstat, tcpdump, glibc, libstdc++, vim, less, strace, chrony)
- Filesystem: /opt/IBM, /var/log/was, /backup created per SOE01 §3
- sysctl: per SOE01 §4 applied and confirmed via sysctl -p
- limits.conf: wasadmin entries per SOE01 §5 applied
- Ports opened: 9060, 9043, 9080, 9443

## 4. Step-by-Step Configuration

### 4.1 WebSphere Admin Console Steps
1. IBM Installation Manager installed to /opt/IBM/InstallationManager
   Expected result: imcl version returns version string ✓
2. WAS ND 9.0.3 installed to /opt/IBM/WebSphere/AppServer via imcl
   Expected result: AppServer/bin/ contains wsadmin.sh, startServer.sh ✓
3. Standalone profile AppSrv01 created (manageprofiles.sh -create)
   Cell: DigiStackCell / Node: Node01 / Server: server1
   Admin security enabled: wasadmin user
   Expected result: INSTCONFSUCCESS ✓
4. server1 started (startServer.sh server1)
   Expected result: ADMU3000I — open for e-business ✓
5. Admin Console confirmed reachable at https://192.168.10.10:9043/ibm/console
   Expected result: Login page loads, wasadmin authenticated ✓

### 4.2 wsadmin / Command-Line Steps
- backupConfig.sh run → /backup/backupConfig-pre-v1.zip ✓

## 5. Verification Steps
- serverStatus.sh confirms server1 = STARTED
- Admin Console login succeeds

## 6. Rollback Procedure
Sprint 1 introduces no application or schema changes.
Rollback = restore VM from pre-v1 snapshot (taken before WAS install).

## 7. Known Issues / Troubleshooting
(populate as encountered)

## 8. Sign-off
- [ ] Setup completed successfully
- [ ] All verification steps passed
- [ ] Documentation reviewed for accuracy

---

### Environment Notes (mirror to Progress_Log.md)
- WAS ND version installed: (record actual from versionInfo.sh output)
- Java version: (record actual from java -version output)
- Profile created: AppSrv01 (standalone, default template)
- Cell: DigiStackCell / Node: Node01 / Server: server1
- Admin security: enabled, wasadmin user