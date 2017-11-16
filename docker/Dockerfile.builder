FROM docker:17.09-dind

RUN apk add --update \
    python \
    py2-pip \
    xmlstarlet \
    git \
    openssl \
  && pip install awscli \
  && rm -rf /var/cache/apk/* 

