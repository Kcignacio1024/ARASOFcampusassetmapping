const request = require('supertest');
const fs = require('fs');
const path = require('path');
const app = require('../server');

const TEST_DATA_DIR = path.join(__dirname, '../data_test');
process.env.DATA_DIR = TEST_DATA_DIR;

beforeAll(() => {
    if (fs.existsSync(TEST_DATA_DIR)) {
        fs.rmSync(TEST_DATA_DIR, { recursive: true, force: true });
    }
    fs.mkdirSync(TEST_DATA_DIR, { recursive: true });
});

afterAll(() => {
    if (fs.existsSync(TEST_DATA_DIR)) {
        fs.rmSync(TEST_DATA_DIR, { recursive: true, force: true });
    }
});

describe('ARASOF Backend API Tests', () => {
    let studentToken;
    let adminToken;
    const testUuid = '123e4567-e89b-12d3-a456-426614174000';

    test('GET /health returns OK', async () => {
        const res = await request(app).get('/health');
        expect(res.statusCode).toEqual(200);
        expect(res.body.status).toEqual('OK');
    });

    test('POST /auth/student returns student token', async () => {
        const res = await request(app)
            .post('/auth/student')
            .send({ name: 'Test Student' });
        expect(res.statusCode).toEqual(200);
        expect(res.body.token).toBeDefined();
        expect(res.body.role).toEqual('STUDENT');
        studentToken = res.body.token;
    });

    test('POST /auth/login fails with invalid credentials', async () => {
        const res = await request(app)
            .post('/auth/login')
            .send({ username: 'admin', password: 'wrongpassword' });
        expect(res.statusCode).toEqual(401);
    });

    test('POST /incidents creates an incident', async () => {
        const res = await request(app)
            .post('/incidents')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({
                id: testUuid,
                assetId: 'B1-1-AC-01',
                buildingId: 1,
                buildingName: 'Admin Building',
                floor: 'GROUND',
                description: 'AC not cooling'
            });
        expect(res.statusCode).toEqual(201);
        expect(res.body.id).toEqual(testUuid);
        expect(res.body.status).toEqual('OPEN');
    });

    test('POST /incidents is idempotent for duplicate id', async () => {
        const res = await request(app)
            .post('/incidents')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({
                id: testUuid,
                assetId: 'B1-1-AC-01',
                buildingId: 1,
                description: 'Retry sending'
            });
        expect(res.statusCode).toEqual(200);
        expect(res.body.id).toEqual(testUuid);
    });

    test('POST /incidents returns 409 if open incident already exists for assetId', async () => {
        const res = await request(app)
            .post('/incidents')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({
                id: '987e6543-e89b-12d3-a456-426614174999',
                assetId: 'B1-1-AC-01',
                buildingId: 1,
                description: 'Another report on same AC'
            });
        expect(res.statusCode).toEqual(409);
        expect(res.body.existingIncident).toBeDefined();
    });
});
