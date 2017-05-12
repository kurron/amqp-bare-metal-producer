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

# Tips and Tricks

## Launching The Image

`./run-container.sh` will launch the image with default values.  The script takes various arguments that control the
application's behavior.  Examine the script to see the current list of arguments.

# Troubleshooting

# License and Credits
This project is licensed under the [Apache License Version 2.0, January 2004](http://www.apache.org/licenses/).

# List of Changes

