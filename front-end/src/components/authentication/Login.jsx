import Navbar from '../navbar/Navbar'
import { generateCodeChallenge, generateCodeVerifier } from './pkceUtils';

export default function Login() {
  const handleKeycloakLogin = () => {
    const clientId = 'react-client'; // Replace with your Keycloak client ID
    const redirectUri = 'http://localhost:5173/auth/callback'; // Replace with your redirect URI
    const keycloakUrl = 'http://localhost:9000'; // Replace with your Keycloak URL

    const codeVerifier = generateCodeVerifier();
    console.log('====================================');
    console.log('Code Verifier:', codeVerifier);
    console.log('====================================');
    localStorage.setItem('codeVerifier', codeVerifier);

    generateCodeChallenge(codeVerifier).then(codeChallenge => {
      const authUrl = `${keycloakUrl}/realms/learn/protocol/openid-connect/auth?` + // Replace 'your-realm' with your Keycloak realm
        `response_type=code&` +  
        `client_id=${clientId}&` +
        `redirect_uri=${encodeURIComponent(redirectUri)}&` +
        `scope=openid profile email&` +
        `code_challenge=${codeChallenge}&` +
        `code_challenge_method=S256`;

      window.location.href = authUrl;
    });
  }
  return (
    <>
        <Navbar />
        <div className='flex justify-center items-center h-screen'>
            <div className='bg-white p-8 rounded shadow-md w-96 flex flex-col items-center'>
                <h2 className='text-2xl font-bold mb-6'>Login</h2>
                <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={handleKeycloakLogin}>Login with Keyclock</button>
            </div>
        </div>
    </>
  )
}
