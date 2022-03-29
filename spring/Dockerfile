FROM openjdk:11

USER backend

RUN mkdir /home/backend/quiron
WORKDIR /home/backend/quiron

COPY /home/mmuni/GIT/quiron_challenge/spring/out/artifacts/quiron_jar /home/backend/quiron
ENTRYPOINT ["java","-jar","/home/backend/quiron/quiron.jar"]
