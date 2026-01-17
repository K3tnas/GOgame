#!/usr/bin/env bash

# TODO: zmienić konfig mavena tak, aby można było wystartować klienta za pomocą mavena

java --add-modules javafx.controls\
     --module-path "$JAVAFX_HOME"/lib\
     -cp client/target/client-1.0-SNAPSHOT.jar:model/target/model-1.0-SNAPSHOT.jar\
     pl.pwr.student.gogame.client.Client ${@}
