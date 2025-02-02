FROM node:18-alpine

WORKDIR /app

# Copy package.json and package-lock.json (if available)
COPY package*.json ./

# Install Node.js dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Nuxt application.
# This command uses the build script defined in your package.json
RUN npm run build

# Expose the port Nuxt will run on (default is 3000)
EXPOSE 3000

# Start the Nuxt server in SSR mode.
# (Make sure your package.json has a start script that starts the Nuxt SSR server, e.g., "start": "nuxi preview")
#CMD ["npm", "run", "start"]
CMD ["node", ".output/server/index.mjs"]
