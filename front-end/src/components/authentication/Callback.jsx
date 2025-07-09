import axios from 'axios';
import React, { useEffect } from 'react'

export default function Callback() {
    // This component will handle the callback from Keycloak after authentication
    // You can extract the authorization code from the URL and exchange it for tokens
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    useEffect(() => {
      console.log('====================================');
      console.log("code: " + code);
      console.log('====================================');
      if (code) {
        const codeVerifier = localStorage.getItem('codeVerifier');
        console.log('Code Verifier:', codeVerifier);

        // Exchange the authorization code for tokens
        axios.post('http://localhost:9000/realms/learn/protocol/openid-connect/token',
          {
            grant_type: 'authorization_code',
            code: code,
            redirect_uri: 'http://localhost:5173/auth/callback',
            client_id: 'react-client',
            code_verifier: codeVerifier
          },
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          }
        )
        .then(response => {
          console.log('Tokens:', response.data);
          // Store tokens in local storage or state management
          localStorage.setItem('access_token', response.data.access_token);
          localStorage.setItem('refresh_token', response.data.refresh_token);
          localStorage.setItem('id_token', response.data.id_token);
          localStorage.removeItem('codeVerifier'); // Clean up the code verifier
          // Redirect to the home page or another page after successful login
          window.location.href = '/';
        })
        .catch(error => {
          console.error('Error exchanging code for tokens:', error);
          localStorage.clear(); // Clear local storage on error
        });
      }
    },[])

  return (
    <div>Callback page</div>
  )
}
