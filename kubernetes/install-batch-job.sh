#!/bin/bash

INSTALL="kubectl create --filename kubernetes/k8s-batch-job.yml"
echo ${INSTALL}
${INSTALL}

STATUS="kubectl get jobs --show-all"
echo ${STATUS}
${STATUS}

# use kubectl logs <job name> to see how it did
