// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: '2024-11-01',
    devtools: {enabled: true},
    ssr: true, // SSR enabled
    typescript: {
        shim: false // Optional: disable type shims if you prefer strict TypeScript
    },
    modules: [
        '@nuxtjs/tailwindcss', // Tailwind CSS module
        '@pinia/nuxt'          // Pinia module for state management
        // You can add more modules as needed later.
    ],
    runtimeConfig: {
        public: {
            // Base URL for the Spring Boot backend API. You can override this in different environments.
            apiBase: process.env.API_BASE || 'http://localhost:8080/api'
        }
    },
    css: [
        '~/assets/css/tailwind.css' // Include your Tailwind CSS file
    ]
});
