# ARASOF Campus Asset Mapping - Backend Server

Node.js + Express backend service for incidents management, authentication, and asset detail overrides.

## Getting Started

1. Install dependencies:
   ```bash
   npm install
   ```

2. Create a maintenance admin user:
   ```bash
   npm run create-admin -- admin secret123 "Maintenance Admin"
   ```

3. Run tests:
   ```bash
   npm test
   ```

4. Start the server:
   ```bash
   npm start
   ```
   (Server runs on port 3000 by default).

## Environment Variables
- `PORT`: Server port (default: 3000)
- `JWT_SECRET`: Secret key for JWT signing
- `DATA_DIR`: Path to data directory (default: `./data`)

> **Production Note:** In production, this server must run behind an HTTPS proxy (e.g. Nginx with TLS certificates).
