import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    plugins: [vue()],
    test: {
        globals: true,
        environment: 'jsdom',
        setupFiles: './vitest.setup.js',
        coverage: {
            reporter: ['text', 'lcov'],
            reportsDirectory: './coverage',
            exclude: [
                'src/components/**',
            ]
        }
    }
})
