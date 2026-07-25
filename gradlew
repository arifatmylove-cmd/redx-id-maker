#!/bin/sh

#
# Gradle wrapper script — POSIX sh compatible
#

# Resolve APP_HOME
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=$(dirname "$PRG")"/$link"
    fi
done
APP_HOME=$(dirname "$PRG")
APP_HOME=$(cd "$APP_HOME" && pwd)

APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Default JVM options
DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'

# Determine Java command
if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
    if [ ! -x "$JAVACMD" ] ; then
        echo "ERROR: JAVA_HOME is set but java not found at: $JAVACMD" >&2
        exit 1
    fi
else
    JAVACMD="java"
fi

# Increase file descriptors if possible
MAX_FD_LIMIT=$(ulimit -H -n 2>/dev/null)
if [ "$MAX_FD_LIMIT" != "" ] && [ "$MAX_FD_LIMIT" != "unlimited" ]; then
    ulimit -n "$MAX_FD_LIMIT" 2>/dev/null || true
fi

exec "$JAVACMD" \
    $DEFAULT_JVM_OPTS \
    ${JAVA_OPTS} \
    ${GRADLE_OPTS} \
    "-Dorg.gradle.appname=$APP_BASE_NAME" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
