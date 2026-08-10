# Verification -1 ==> Logout Actually Clears the Session
Open Browser
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Dashboard.jsp
```
Step:1 ==> Login to the Page 
```
og in with testuser1/Test@1234
```

Step:2 ==> Logout

Expected result ==> Browser navigates to the Login page ==> Now press your browser's Back button.

Expected result 
```
You should be bounced back to Login again (not shown a stale cached Dashboard) — this proves the guard check in Step 1 is doing real work even against back-button navigation.
```

# Verification -1 ==> Observe and Capture the Startup Log Sequence
While/after the app restarts, pull the relevant section of SystemOut.log:
```
tail -100 /apps/IBM/WebSphere/AppServer/profiles/devdsbinappserver01/logs/server1/SystemOut.log
```
You should see a recognizable sequence of messages. Here's what the key ones mean — watch for these specific message codes (WebSphere prefixes almost every log line with a message ID like WSVR0028I — these codes are consistent across WAS versions and are exactly what you'd search IBM's documentation for if troubleshooting):
<img width="729" height="421" alt="image" src="https://github.com/user-attachments/assets/1152d39a-703b-4780-9e58-dd8ca575d671" />

# Regression Test (Confirm v2 Still Works After Redeploy)

### Step:1 ==> V1 check
open Browser
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Home
```
still shows the live app_config read (v1 functionality, unaffected)

### Step:1 ==> Logout Actually Clears the Session
Open Browser
```
http://dsb-dmgr.digistack.cloud:9080/digistack-bank/Login
```
1. Login to the Page 
```
Log in with testuser1/Test@1234
```

2. Check Dashboard shows last login

3. Logout → redirected to Login, back button doesn't leak Dashboard
