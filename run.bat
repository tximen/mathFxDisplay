rem @echo off
set APP_JAR=MathfxDisplay-0.0.1-SNAPSHOT.jar

set ONE_DRIVE="C:\Users\thomas\OneDrive"

set JAVA_HOME=D:/programme/java/jdk-27
set PATH_TO_FX=D:/programme/java/javafx-sdk-27/lib

set EXEC_DIR=G:\intellij\MathFxDisplay\target

set LOG_DIR=E:\temp\mathlogs

set JAVA_CMD=%JAVA_HOME%\bin\java

rem ** JAVA FX PATH **
set JAVA_CMD=%JAVA_CMD% --module-path %PATH_TO_FX%

rem ** MODULES **
set JAVA_CMD=%JAVA_CMD% --add-modules java.se,javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web

rem ** JAVA EXPORTS **
set JAVA_CMD=%JAVA_CMD% --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED
set JAVA_CMD=%JAVA_CMD% --add-exports javafx.base/com.sun.javafx.reflect=ALL-UNNAMED
set JAVA_CMD=%JAVA_CMD% --add-exports javafx.base/com.sun.javafx.beans=ALL-UNNAMED
set JAVA_CMD=%JAVA_CMD% --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED
set JAVA_CMD=%JAVA_CMD% --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED

set JAVA_CMD=%JAVA_CMD% --enable-native-access=javafx.graphics
set JAVA_CMD=%JAVA_CMD% --enable-native-access=javafx.web

set JAVA_CMD=%JAVA_CMD% --add-exports java.base/jdk.internal.misc=ALL-UNNAMED
set JAVA_CMD=%JAVA_CMD% --add-opens java.base/jdk.internal.misc=ALL-UNNAMED

%JAVA_CMD% -jar %EXEC_DIR%/%APP_JAR%