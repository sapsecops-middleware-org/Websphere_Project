# Verification -1 ==> Logout Actually Clears the Session
Open Browser
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Dashboard.jsp
```
Step:1 ==> Login to the Page 

Step:2 ==> Logout

Expected result ==> Browser navigates to the Login page ==> Now press your browser's Back button.

Expected result 
```
You should be bounced back to Login again (not shown a stale cached Dashboard) — this proves the guard check in Step 1 is doing real work even against back-button navigation.
```

# Verification -2 ==> Confirm Log Entries
```
grep "LogoutServlet" /apps/IBM/WebSphere/AppServer/profiles/devdsbinappserver01/logs/server1/SystemOut.log
```
Expected result ==> A line showing "Session invalidated" with the session ID and username that matches your Test 1 session ID from Sprint 3's log check.
