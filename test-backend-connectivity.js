// Test script to check backend connectivity
// Run with: node test-backend-connectivity.js

const axios = require('axios');

const BASE_URL = 'http://localhost:8080';

async function testBackendConnectivity() {
  console.log('🔍 Testing Backend Connectivity...\n');

  // Test 1: Basic connectivity
  console.log('1️⃣ Testing basic connectivity...');
  try {
    const response = await axios.get(`${BASE_URL}/actuator/health`, { timeout: 5000 });
    console.log('✅ Backend is accessible!');
    console.log('📊 Status:', response.status);
    console.log('📝 Response:', response.data);
  } catch (error) {
    if (error.code === 'ECONNREFUSED') {
      console.log('❌ Connection refused - Backend is not running on port 8080');
      console.log('💡 Try: docker-compose up -d');
    } else if (error.code === 'ENOTFOUND') {
      console.log('❌ Host not found - Check if localhost is accessible');
    } else if (error.code === 'ETIMEDOUT') {
      console.log('❌ Connection timeout - Backend might be starting up');
    } else {
      console.log('❌ Connection error:', error.message);
    }
    return false;
  }

  // Test 2: Registration endpoint
  console.log('\n2️⃣ Testing registration endpoint...');
  try {
    const response = await axios.post(`${BASE_URL}/api/auth/register`, {
      username: 'testuser',
      password: 'testpass123',
      email: 'test@example.com'
    }, { timeout: 10000 });
    
    console.log('✅ Registration endpoint is working!');
    console.log('📊 Status:', response.status);
    console.log('📝 Response:', response.data);
  } catch (error) {
    if (error.response) {
      console.log('⚠️ Registration endpoint responded with error:');
      console.log('📊 Status:', error.response.status);
      console.log('📝 Response:', error.response.data);
    } else {
      console.log('❌ Registration endpoint error:', error.message);
    }
  }

  // Test 3: Login endpoint
  console.log('\n3️⃣ Testing login endpoint...');
  try {
    const response = await axios.post(`${BASE_URL}/api/auth/login`, {
      username: 'admin',
      password: 'admin123'
    }, { timeout: 10000 });
    
    console.log('✅ Login endpoint is working!');
    console.log('📊 Status:', response.status);
    if (response.data.token) {
      console.log('🔑 Token received:', response.data.token.substring(0, 50) + '...');
    }
  } catch (error) {
    if (error.response) {
      console.log('⚠️ Login endpoint responded with error:');
      console.log('📊 Status:', error.response.status);
      console.log('📝 Response:', error.response.data);
    } else {
      console.log('❌ Login endpoint error:', error.message);
    }
  }

  return true;
}

async function checkDockerStatus() {
  console.log('\n🐳 Checking Docker Status...\n');
  
  const { exec } = require('child_process');
  
  return new Promise((resolve) => {
    exec('docker-compose ps', (error, stdout, stderr) => {
      if (error) {
        console.log('❌ Docker Compose not available or error:', error.message);
        resolve(false);
        return;
      }
      
      console.log('📋 Docker Compose Status:');
      console.log(stdout);
      
      if (stdout.includes('bookstore_springboot_app')) {
        if (stdout.includes('Up')) {
          console.log('✅ Spring Boot app is running');
          resolve(true);
        } else {
          console.log('❌ Spring Boot app is not running');
          resolve(false);
        }
      } else {
        console.log('❌ Spring Boot app container not found');
        resolve(false);
      }
    });
  });
}

async function runDiagnostics() {
  console.log('🚀 Starting Backend Connectivity Diagnostics...\n');
  
  const dockerOk = await checkDockerStatus();
  
  if (dockerOk) {
    await testBackendConnectivity();
  } else {
    console.log('\n💡 Recommendations:');
    console.log('1. Make sure Docker Desktop is running');
    console.log('2. Run: docker-compose up -d');
    console.log('3. Wait for all services to start');
    console.log('4. Check logs: docker-compose logs -f bookstore_springboot_app');
  }
  
  console.log('\n✨ Diagnostics completed!');
}

// Run the diagnostics
runDiagnostics().catch(console.error);
