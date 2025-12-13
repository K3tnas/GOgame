#!/usr/bin/env bash

# TODO: zmienić konfig mavena tak, aby można było wystartować serwer za pomocą mavena

mvn package &&\
java -cp server/target/server-1.0-SNAPSHOT.jar:model/target/model-1.0-SNAPSHOT.jar pl.pwr.student.gogame.server.Server
