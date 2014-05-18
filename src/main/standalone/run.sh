#!/bin/sh
SERVICE_HOME=/home/ticketbot/application
JRE_HOME=/usr/java/jdk1.6.0_32

CLASSPATH=""
for i in `ls $SERVICE_HOME/lib/*.jar`; do
  CLASSPATH="$CLASSPATH":"$i"
done

echo "Starting SMARTMM Data Service with CLASSPATH=$CLASSPATH"
$JRE_HOME/bin/java -cp $SERVICE_HOME/resources:$CLASSPATH com.netthreads.ticketbot.Application
