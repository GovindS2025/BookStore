# -------- Stage 1: Build React App --------
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install --legacy-peer-deps

# Copy the rest of the app and build it
COPY . .
RUN npm run build

# -------- Stage 2: Serve with NGINX --------
FROM nginx:alpine

# Remove default NGINX static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy React build from previous stage
COPY --from=build /app/build /usr/share/nginx/html

# Optional: Use custom NGINX config
# COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
