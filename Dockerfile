FROM kurron/docker-azul-jdk-8:1.8.0_131-b11

MAINTAINER Ron Kurr "kurr@jvmguy.com"

#ENTRYPOINT ["java", "-server", "-XX:MaxRAM=2g", "-Xms1563m", "-Xmx1563m", "-XX:+UseSerialGC", "-XX:+ScavengeBeforeFullGC", "-XX:+CMSScavengeBeforeRemark", "-XX:MinHeapFreeRatio=20", "-XX:MaxHeapFreeRatio=40", "-Dsun.net.inetaddr.ttl=60", "-Djava.awt.headless=true", "-jar", "/opt/microservice.jar"]
ENTRYPOINT ["java", "-server", "-XX:MaxRAM=3g", "-Xms2g", "-Xmx2g", "-XX:+ScavengeBeforeFullGC", "-XX:+CMSScavengeBeforeRemark", "-XX:MinHeapFreeRatio=20", "-XX:MaxHeapFreeRatio=40", "-Dsun.net.inetaddr.ttl=60", "-Djava.awt.headless=true", "-jar", "/opt/microservice.jar"]

COPY build/libs/amqp-bare-metal-producer-0.0.0.RELEASE.jar /opt/microservice.jar
