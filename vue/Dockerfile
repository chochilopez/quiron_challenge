FROM node:17-alpine3.14

USER node

# Directorio de trabajo
RUN mkdir /home/node/app
WORKDIR /home/node/app

# Dependencias
COPY package.json ./
COPY yarn.lock ./
RUN npm install -g @quasar/cli && \
    npm install -g @vue/cli && \
    npm install -g @vue/cli-init

# Código
COPY . .

USER node
EXPOSE 8080

CMD /bin/sh
