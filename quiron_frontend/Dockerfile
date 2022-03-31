FROM node:17-alpine3.14

USER root

# Dependencias
COPY package.json ./
COPY yarn.lock ./
RUN npm install -g @quasar/cli && \
    npm install -g @vue/cli && \
    npm install -g @vue/cli-init

# Directorio de trabajo
RUN mkdir /home/node/app
WORKDIR /home/node/app

# Código
COPY . .

EXPOSE 8080

CMD /bin/sh
