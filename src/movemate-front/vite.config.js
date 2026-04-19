import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        // Reescribir la URL que el frontend (Vite) le pasa al backend, eliminando el prefijo /api antes de reenviar la solicitud.
        rewrite: path => path.replace(/^\/api/, '')
      }
    }
  }
})
