# Overview
This project is a simple Docker image that provides a component that can be used to benchmark RabbitMQ deployments.
This application will publish messages to a a queue.

# Prerequisites
* a working [Docker](http://docker.io) engine
* a working [Docker Compose](http://docker.io) installation

# Building
Type `./build.sh` to build the image.

# Installation
Docker will automatically install the newly built image into the cache.
To tag and publish the image, run `/tag-and-push.sh`.  You must have already
logged into Docker Hub.

# Tips and Tricks

## Launching The Image

`./run-container.sh` will launch the image with default values.  The script takes various arguments that control the
application's behavior.  Examine the script to see the current list of arguments.

## Deploying as a Kubernetes batch job

1. `kubernetes/create-namespace.sh` to create a namespace, if you aren't using one already
1. `kubernetes/set-namespace-as-default.sh` to create the namespace, if you aren't using one already
1. `kubernetes/create-config-map.sh` to install the configuration values into K8s.  You can either edit the script or
pass in the required information as arguments.
1. `kubernetes/install-batch-job.sh` to create and run the job
1. run `kubectl get jobs --show-all` to check on its status
1. run `kubectl get pods --show-all` to see the completed pod
1. run `kubectl logs amqp-bare-metal-producer` to see its output
1. run `kubectl delete jobs/amqp-bare-metal-producer` to clean up

# Troubleshooting

# License and Credits
This project is licensed under the [Apache License Version 2.0, January 2004](http://www.apache.org/licenses/).

# List of Changes
