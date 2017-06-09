#!/bin/bash

NAMESPACE=${1:-kurron}

COMMAND="kubectl config set-context $(kubectl config current-context) --namespace=${NAMESPACE}"

echo ${COMMAND}
${COMMAND}

