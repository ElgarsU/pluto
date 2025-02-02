// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: '2024-11-01',
    devtools: {enabled: true},
    ssr: true,
    typescript: {
        shim: false
    },
    modules: [
        '@nuxtjs/tailwindcss',
        '@pinia/nuxt'
    ],
    runtimeConfig: {
        public: {
            apiBase: process.env.API_BASE || 'http://localhost:8080/api'
        }
    },
    css: [
        '~/assets/css/tailwind.css'
    ]
});
