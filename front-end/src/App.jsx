import { use, useContext, useEffect, useState } from 'react';
import './App.css'
import Navbar from './components/navbar/Navbar'
import axios from 'axios';
import Context from './context/Context';

function App() {
  const [isLogged, setIsLogged] = useState(false);

  const {user, authenticated} = useContext(Context)
  useEffect(() => {
    console.log(user);
    
  },[])

  const callServer2 = () => {
    axios.get('http://localhost:8090/public/get')
      .then(response => {
        console.log('Resource Server 2 Response:', response.data);
      })
      .catch(error => {
        console.error('Error calling Resource Server 2:', error);
      });
  }

  const callServer2viaResServ1 = () => {
    axios.get('http://localhost:8080/public/call/resource2')
      .then(response => {
        console.log('Resource Server 2 Response:', response.data);
      })
      .catch(error => {
        console.error('Error calling Resource Server 2:', error);
      });
  }

  const callServer1Authed = () => {
    axios.get('http://localhost:8080/private/get', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
      }
    })
  }

  const callServer2via1Authed = () => {
    axios.get('http://localhost:8080/auth/server2', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
      }
    })
  }

  return (
    <>
      <Navbar />
      <div className='page'>
        <div>
          {authenticated ? <p>Logged In</p>: <p>Logged out</p>}
          {user && (
            <div>
              <h2>User Information</h2>
              <p><strong>Username:</strong> {user.preferred_username}</p>
              <p><strong>Email:</strong> {user.email}</p>
              <p><strong>Name:</strong> {user.name}</p>
            </div>
          )}
          {authenticated && (
            <div className='p-2 flex space-x-1'>
              <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={callServer1Authed}>Call private server 1</button>
              <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={callServer2via1Authed}>Call private server 2 via private server 1</button>
            </div>
          )}
        </div>
        <hr />
        <div className='mt-4 p-2'>
          <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={callServer2}>Call server 2</button>
        </div>
        <hr />
        <div className='mt-4 p-2'>
          <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={callServer2viaResServ1}>Call server 2 via server 1</button>
        </div>
        <div className='mt-4 p-2'>
          <button className='cursor-pointer bg-slate-700 text-white p-2 rounded-sm' onClick={callServer2viaResServ1}>Call private server 2 via resource server 1</button>
        </div>
      </div>
    </>
  )
}

export default App
