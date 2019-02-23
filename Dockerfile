FROM openjdk:8-jdk-alpine

LABEL version="1.0"

MAINTAINER https://github.com/surenderthakran

# Add required packages.
RUN apk add --no-cache \
    bash \
    make

COPY . /java

WORKDIR /java

RUN make --no-print-directory install

CMD make --no-print-directory run
