#!/bin/bash

# Created by Emanuel Palm (https://github.com/emanuelpalm)

cd "$(dirname "$0")" || exit
source "lib_certs.sh"
cd ..

create_furnace_system_keystore() {
  SYSTEM_NAME=$1
  DIR_NAME=$2

  create_system_keystore \
    "./master.crt" "arrowhead.eu" \
    "./testcloud2.p12" "testcloud2.aitia.arrowhead.eu" \
    "./../../../../../../../../../../../../../../../../../../${DIR_NAME}/src/main/resources/certificates/${SYSTEM_NAME}.p12" "${SYSTEM_NAME}.testcloud2.aitia.arrowhead.eu" \
    "dns:localhost,ip:127.0.0.1"
}
#
create_furnace_system_keystore "temperaturesensorprovider" "temperature-sensor-provider"
create_furnace_system_keystore "heatingsystem" "heating-system"
create_furnace_system_keystore "heaterconsumer" "heater-consumer"
create_furnace_system_keystore "doorsensorprovider" "door-sensor-provider"


#
#
#create_truststore \
#  "cloud-data-producer/crypto/truststore.p12" \
#  "cloud-data-producer/crypto/conet-demo-producer.crt" "conet-demo-producer.ltu.arrowhead.eu" \
#  "cloud-relay/crypto/conet-demo-relay.crt" "conet-demo-relay.ltu.arrowhead.eu"
