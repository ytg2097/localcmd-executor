FROM java

EXPOSE 9520

VOLUME /home

ADD /stream-transcoding.jar app.jar

ENTRYPOINT ["java" , "-Djava.security.egd=file:/dev/./urandom" ,"-jar" , "app.jar"]

