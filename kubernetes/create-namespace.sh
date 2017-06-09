#!/bin/bash

NAMESPACE=${1:-kurron}

CREATE="kubectl create namespace ${NAMESPACE}"

echo ${CREATE}
${CREATE}

