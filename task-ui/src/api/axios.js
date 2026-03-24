import axios from 'axios';

// Create API instance for backend connection
export const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 5000 // 5 second timeout
});

// Request interceptor - add auth if needed
api.interceptors.request.use(
    config => {
        // Add any auth tokens here if implementing JWT
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// Response interceptor - handle errors
api.interceptors.response.use(
    response => response,
    error => {
        if (error.code === 'ECONNABORTED') {
            console.error('Request timeout - Backend may be offline');
        } else if (!error.response) {
            console.error('Network error - Cannot reach backend at http://localhost:8080');
        } else if (error.response.status === 401) {
            console.error('Unauthorized - Check login credentials');
        } else if (error.response.status === 404) {
            console.error('Resource not found');
        } else if (error.response.status === 500) {
            console.error('Server error');
        }
        return Promise.reject(error);
    }
);

export default api;

