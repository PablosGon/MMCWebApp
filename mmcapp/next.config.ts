import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  images: {
    remotePatterns: [{
      hostname: "cdn.brawlify.com"
    }],
  },
};

export default nextConfig;
