import axios from 'axios';

// Concept: Creating a pre-configured instance for the Java Backend
export const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    }
});

// This is the line your error is complaining about!
export default api;