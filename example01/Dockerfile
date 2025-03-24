ARG ubuntu_version=22.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
RUN apt-get update && apt-get install openjdk-21-jre-headless -y

CMD ["/usr/bin/java", "--version"]
