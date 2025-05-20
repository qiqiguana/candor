#!/bin/sh
#
email="alois.belaska@gmail.com"
password=""
application="eshopsengine"
sourceid="$application-WatchDog"

auth=`curl -s https://www.google.com/accounts/ClientLogin -d "Email=$email" -d "Passwd=$password" -d accountType=GOOGLE -d "source=$sourceid" -d service="ah" | grep 'Auth' | cut -d '=' -f 2`

cookie=`mktemp /tmp/cookie.eshops.XXXX`

curl -s -L -c "$cookie" "https://appengine.google.com/_ah/login?&auth=$auth"

ACSID=`grep ACSID $cookie | sed -e "s/.*ACSID\t\(.*\)/\1/"`

rm -f "$cookie"

curl -s -H "Cookie: ACSID=$ACSID" "https://appengine.google.com/dashboard/quotadetails?&app_id=$application"
