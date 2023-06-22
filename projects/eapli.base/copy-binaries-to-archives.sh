#!/bin/bash

cp AGVDigitalTwin/target/AGVDigitalTwin-1.4.0-SNAPSHOT.jar ../../archives/AllApps/AgvDigitalTwin/
cp -r AGVDigitalTwin/target/dependency/* ../../archives/AllApps/dependencies


cp AGVManager/target/AGVManager-1.4.0-SNAPSHOT.jar ../../archives/AllApps/AgvManager/
cp -r AGVManager/target/dependency/* ../../archives/AllApps/dependencies/

cp OrderServer/target/OrderServer-1.4.0-SNAPSHOT.jar ../../archives/AllApps/OrderServer/
cp -r OrderServer/target/dependency/* ../../archives/AllApps/dependencies/


cp HTTPServer/target/HTTPServer-1.4.0-SNAPSHOT.jar ../../archives/AllApps/HttpServer/
cp -r HTTPServer/target/dependency/* ../../archives/AllApps/dependencies/


cp base.app.backoffice.console/target/base.app.backoffice.console-1.4.0-SNAPSHOT.jar ../../archives/AllApps/BackOffice/
cp -r base.app.backoffice.console/target/dependency/* ../../archives/AllApps/dependencies/

cp  base.app.user.console/target/base.app.user.console-1.4.0-SNAPSHOT.jar ../../archives/AllApps/UserConsole/
cp -r  base.app.user.console/target/dependency/* ../../archives/AllApps/dependencies/
