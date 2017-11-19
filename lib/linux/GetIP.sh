#! /bin/bash
ip=$(ip r show | grep ' src '|cut -d ' ' -f 12)
echo $ip
