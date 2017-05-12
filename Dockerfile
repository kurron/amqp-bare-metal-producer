FROM kurron/docker-azul-jdk-8:1.8.0_131-b11

MAINTAINER Ron Kurr "kurr@jvmguy.com"

ENTRYPOINT ["java", "-server", "-XX:MaxRAM=1g", "-Xms768m", "-Xmx768m", "-XX:+UseSerialGC", "-XX:+ScavengeBeforeFullGC", "-XX:+CMSScavengeBeforeRemark", "-XX:MinHeapFreeRatio=20", "-XX:MaxHeapFreeRatio=40", "-Dsun.net.inetaddr.ttl=60", "-Djava.awt.headless=true", "-jar", "/opt/microservice.jar"]

COPY build/libs/amqp-bare-metal-producer-0.0.0.RELEASE.jar /opt/microservice.jar
