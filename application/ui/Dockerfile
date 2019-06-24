FROM node:10.16.0-alpine as build-ui
WORKDIR /workspace/app/ui
RUN npm install -g @angular/cli
COPY package*.json ./
RUN npm i

COPY tsconfig.*.json ./
COPY tsconfig.json ./
COPY angular.json .
COPY e2e/ e2e/
COPY browserslist .
COPY karma.conf.js .

EXPOSE 3000
EXPOSE 49153

ENTRYPOINT ["npm", "run", "start"]