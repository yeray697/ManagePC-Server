#! /bin/bash
volumen=$(pactl list sinks | grep '^[[:space:]]Volume:' | tail -n $(( $SINK + 1 )) | sed -e 's,.* \([0-9][0-9]*\)%.*,\1,')
max=$2
contador=$1
limite=$((max - contador))
if (( $volumen > $limite ))
then
	pactl set-sink-volume @DEFAULT_SINK@ $max%
else
	pactl set-sink-volume @DEFAULT_SINK@ +$contador%
fi
