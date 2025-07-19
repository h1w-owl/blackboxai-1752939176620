/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  env: {
    CUSTOM_KEY: process.env.CUSTOM_KEY,
  },
  images: {
    domains: [
      'hayuwidyas.com',
      'firebasestorage.googleapis.com',
      'images.unsplash.com',
      'via.placeholder.com'
    ],
    unoptimized: true,
  },
  experimental: {
    serverComponentsExternalPackages: ['firebase-admin'],
  },
  typescript: {
    ignoreBuildErrors: false,
  },
  eslint: {
    ignoreDuringBuilds: false,
  },
}

module.exports = nextConfig