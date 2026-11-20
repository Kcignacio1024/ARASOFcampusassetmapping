const fs = require('fs');
const path = require('path');
const bcrypt = require('bcrypt');

const args = process.argv.slice(2);
if (args.length < 3) {
    console.error('Usage: node scripts/create-admin.js <username> <password> <name>');
    process.exit(1);
}

const [username, password, name] = args;
const DATA_DIR = process.env.DATA_DIR || path.join(__dirname, '../data');
const USERS_FILE = path.join(DATA_DIR, 'users.json');

if (!fs.existsSync(DATA_DIR)) {
    fs.mkdirSync(DATA_DIR, { recursive: true });
}

let users = [];
if (fs.existsSync(USERS_FILE)) {
    try {
        users = JSON.parse(fs.readFileSync(USERS_FILE, 'utf8'));
    } catch (e) {
        users = [];
    }
}

if (users.some(u => u.username === username)) {
    console.error(`Error: User '${username}' already exists.`);
    process.exit(1);
}

bcrypt.hash(password, 10, (err, hash) => {
    if (err) {
        console.error('Error hashing password:', err);
        process.exit(1);
    }

    users.push({
        username,
        passwordHash: hash,
        name,
        role: 'MAINTENANCE',
        createdAt: Date.now()
    });

    fs.writeFileSync(USERS_FILE, JSON.stringify(users, null, 2), 'utf8');
    console.log(`Maintenance admin user '${username}' created successfully.`);
});
