const express = require('express');
const fs = require('fs');
const path = require('path');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { v4: uuidv4 } = require('uuid');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;
const JWT_SECRET = process.env.JWT_SECRET || 'arasof-super-secret-jwt-key-2024';
const DATA_DIR = process.env.DATA_DIR || path.join(__dirname, 'data');

// Ensure data directory exists
if (!fs.existsSync(DATA_DIR)) {
    fs.mkdirSync(DATA_DIR, { recursive: true });
}

// File paths
const INCIDENTS_FILE = path.join(DATA_DIR, 'incidents.json');
const ASSET_DETAILS_FILE = path.join(DATA_DIR, 'assetDetails.json');
const USERS_FILE = path.join(DATA_DIR, 'users.json');

// Initialize files if they don't exist
function initFile(filePath, defaultContent) {
    if (!fs.existsSync(filePath)) {
        fs.writeFileSync(filePath, JSON.stringify(defaultContent, null, 2), 'utf8');
    }
}
initFile(INCIDENTS_FILE, []);
initFile(ASSET_DETAILS_FILE, {});
initFile(USERS_FILE, []);

// Atomic file writer queue to prevent corruption
let writeQueue = Promise.resolve();
function atomicWrite(filePath, data) {
    writeQueue = writeQueue.then(() => {
        return new Promise((resolve, reject) => {
            const tempPath = `${filePath}.${uuidv4()}.tmp`;
            fs.writeFile(tempPath, JSON.stringify(data, null, 2), 'utf8', (err) => {
                if (err) return reject(err);
                fs.rename(tempPath, filePath, (err) => {
                    if (err) return reject(err);
                    resolve();
                });
            });
        });
    });
    return writeQueue;
}

function readJson(filePath) {
    try {
        if (!fs.existsSync(filePath)) return null;
        return JSON.parse(fs.readFileSync(filePath, 'utf8'));
    } catch (e) {
        console.error(`Error reading ${filePath}:`, e);
        return null;
    }
}

// Basic Rate Limiting Middleware
const requestCounts = new Map();
app.use((req, res, next) => {
    const ip = req.ip || req.connection.remoteAddress;
    const now = Date.now();
    let record = requestCounts.get(ip);
    if (!record || now - record.startTime > 60000) {
        record = { count: 0, startTime: now };
        requestCounts.set(ip, record);
    }
    record.count++;
    if (record.count > 120) { // 120 requests per minute
        return res.status(429).json({ error: 'Too many requests, please try again later.' });
    }
    next();
});

// Nightly backup (every 24 hours)
setInterval(() => {
    try {
        const backupDir = path.join(DATA_DIR, 'backup');
        if (!fs.existsSync(backupDir)) fs.mkdirSync(backupDir, { recursive: true });
        const timestamp = new Date().toISOString().replace[:/]/g, '-');
        ['incidents.json', 'assetDetails.json', 'users.json'].forEach(file => {
            const src = path.join(DATA_DIR, file);
            if (fs.existsSync(src)) {
                fs.copyFileSync(src, path.join(backupDir, `${timestamp}-${file}`));
            }
        });
        console.log('Nightly backup completed successfully.');
    } catch (e) {
        console.error('Backup failed:', e);
    }
}, 24 * 60 * 60 * 1000);

// Auth Middleware
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    if (!token) return res.status(401).json({ error: 'Missing authorization token' });

    jwt.verify(token, JWT_SECRET, (err, user) => {
        if (err) return res.status(401).json({ error: 'Invalid or expired token' });
        req.user = user;
        next();
    });
}

function requireMaintenance(req, res, next) {
    if (req.user.role !== 'MAINTENANCE') {
        return res.status(403).json({ error: 'Access denied. Maintenance role required.' });
    }
    next();
}

// --- ENDPOINTS ---

app.get('/health', (req, res) => {
    res.json({ status: 'OK', timestamp: Date.now() });
});

// Student login / registration
app.post('/auth/student', (req, res) => {
    const { name } = req.body;
    if (!name || typeof name !== 'string' || name.trim().length === 0 || name.length > 50) {
        return res.status(400).json({ error: 'Invalid name (1-50 characters required)' });
    }
    const cleanName = name.trim();
    const token = jwt.sign({ name: cleanName, role: 'STUDENT' }, JWT_SECRET, { expiresIn: '7d' });
    res.json({ token, role: 'STUDENT', name: cleanName });
});

// Maintenance login
app.post('/auth/login', async (req, res) => {
    const { username, password } = req.body;
    if (!username || !password) {
        return res.status(400).json({ error: 'Username and password required' });
    }
    const users = readJson(USERS_FILE) || [];
    const user = users.find(u => u.username === username);
    if (!user) {
        return res.status(401).json({ error: 'Invalid username or password' });
    }
    const match = await bcrypt.compare(password, user.passwordHash);
    if (!match) {
        return res.status(401).json({ error: 'Invalid username or password' });
    }
    const token = jwt.sign({ username: user.username, name: user.name, role: 'MAINTENANCE' }, JWT_SECRET, { expiresIn: '7d' });
    res.json({ token, role: 'MAINTENANCE', name: user.name });
});

// GET /incidents
app.get('/incidents', authenticateToken, (req, res) => {
    const { status, buildingId, since } = req.query;
    let incidents = readJson(INCIDENTS_FILE) || [];

    if (status) {
        incidents = incidents.filter(i => i.status === status.toUpperCase());
    }
    if (buildingId) {
        const bId = parseInt(buildingId, 10);
        incidents = incidents.filter(i => i.buildingId === bId);
    }
    if (since) {
        const sinceMs = parseInt(since, 10);
        if (!isNaN(sinceMs)) {
            incidents = incidents.filter(i => (i.updatedAt || i.reportedAt) > sinceMs);
        }
    }
    res.json(incidents);
});

// POST /incidents
app.post('/incidents', authenticateToken, async (req, res) => {
    const incident = req.body;
    if (!incident || !incident.id || !incident.assetId || !incident.buildingId) {
        return res.status(400).json({ error: 'Missing required incident fields (id, assetId, buildingId)' });
    }
    const bId = parseInt(incident.buildingId, 10);
    if (isNaN(bId) || bId < 1 || bId > 46) {
        return res.status(400).json({ error: 'Invalid buildingId (must be 1-46)' });
    }

    let incidents = readJson(INCIDENTS_FILE) || [];

    // Idempotency check: if id already exists, return existing
    const existingById = incidents.find(i => i.id === incident.id);
    if (existingById) {
        return res.json(existingById);
    }

    // Only one OPEN incident per assetId rule
    const existingOpenForAsset = incidents.find(i => i.assetId === incident.assetId && i.status === 'OPEN');
    if (existingOpenForAsset) {
        return.status(409).json({
            error: 'An open incident already exists for this asset',
            existingIncident: existingOpenForAsset
        });
    }

    const now = Date.now();
    const newIncident = {
        id: incident.id,
        assetId: incident.assetId,
        assetTag: incident.assetTag || '',
        assetLabel: incident.assetLabel || '',
        buildingId: bId,
        buildingName: incident.buildingName || '',
        floor: incident.floor || 'GROUND',
        description: (incident.description || '').substring(0, 500),
        status: 'OPEN',
        reportedBy: req.user.name || req.user.username || 'Anonymous',
        reportedAt: incident.reportedAt || now,
        updatedAt: now,
        resolvedBy: null,
        resolvedAt: null,
        resolutionNote: null
    };

    incidents.push(newIncident);
    await atomicWrite(INCIDENTS_FILE, incidents);
    res.status(201).json(newIncident);
});

// PATCH /incidents/:id/resolve
app.patch('/incidents/:id/resolve', authenticateToken, requireMaintenance, async (req, res) => {
    const { id } = req.params;
    const { note } = req.body;

    let incidents = readJson(INCIDENTS_FILE) || [];
    const index = incidents.findIndex(i => i.id === id);
    if (index === -1) {
        return res.status(404).json({ error: 'Incident not found' });
    }

    const now = Date.now();
    const updated = {
        ...incidents[index],
        status: 'RESOLVED',
        resolvedBy: req.user.name || req.user.username || 'Maintenance',
        resolvedAt: now,
        updatedAt: now,
        resolutionNote: note ? note.substring(0, 500) : null
    };

    incidents[index] = updated;
    await atomicWrite(INCIDENTS_FILE, incidents);
    res.json(updated);
});

// GET /assets/details
app.get('/assets/details', authenticateToken, (req, res) => {
    const details = readJson(ASSET_DETAILS_FILE) || {};
    res.json(details);
});

// PUT /assets/details/:assetId
app.put('/assets/details/:assetId', authenticateToken, requireMaintenance, async (req, res) => {
    const { assetId } = req.params;
    const body = req.body;

    let details = readJson(ASSET_DETAILS_FILE) || {};
    details[assetId] = {
        tag: body.tag || '',
        locationNote: body.locationNote || '',
        brand: body.brand || '',
        model: body.model || '',
        serialNo: body.serialNo || '',
        installedOn: body.installedOn || '',
        condition: body.condition || 'GOOD',
        lastServiceOn: body.lastServiceOn || '',
        updatedAt: Date.now()
    };

    await atomicWrite(ASSET_DETAILS_FILE, details);
    res.json({ success: true, assetId, details: details[assetId] });
});

if (require.main === module) {
    app.listen(PORT, () => {
        console.log(`ARASOF Campus Backend running on port ${PORT}`);
    });
}

module.exports = app;
