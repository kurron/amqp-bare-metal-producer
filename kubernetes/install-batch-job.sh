#!/bin/bash

INSTALL="kubectl create --filename kubernetes/k8s-batch-job.yml"
echo ${INSTALL}
${INSTALL}

JOB_STATUS="kubectl get jobs --show-all"
echo ${JOB_STATUS}
${JOB_STATUS}

POD_STATUS="kubectl get pods --show-all"
echo ${POD_STATUS}
${POD_STATUS}

# use kubectl logs <job name> to see how it did
