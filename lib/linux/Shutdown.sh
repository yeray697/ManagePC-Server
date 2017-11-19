#! /bin/bash
time=$1
shutdown -c
if [ "$time" = "0" ]
then
	shutdown now
else
	shutdown -t $time
fi
