# syntax=docker/dockerfile:1

# ---- Étape 1 : construction du site avec Node (Vite) ----
FROM node:24-alpine AS build
WORKDIR /app

# Dépendances d'abord : elles ne sont réinstallées que si package*.json change
COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build

# ---- Étape 2 : service des fichiers statiques avec nginx (sans droits root) ----
FROM nginxinc/nginx-unprivileged:stable-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 8080
