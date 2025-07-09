import React, { useContext, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Context from '../../context/Context'
import axios from 'axios'
import { jwtDecode } from 'jwt-decode'

export default function Navbar() {
  const navigate = useNavigate()
  const {authenticated, setAuthenticated, pageloaded, setPageloaded, setUser} = useContext(Context)

  useEffect(() => {
    if (pageloaded) {
      return
    }
    if(!authenticated) {
      const token = localStorage.getItem("access_token")
      if (token) {
        axios.get("http://localhost:8080/token",{
          headers: {
            Authorization: `Bearer ${token}`
          }
        })
          .then(res=>{
            console.log("token verified");
            setAuthenticated(true);
            const id_token = localStorage.getItem("id_token");
            if (!id_token) {
              setAuthenticated(false);
              setUser(null);
              localStorage.clear();
              return
            }
            // Set user data in context or state
            const userInfo = jwtDecode(localStorage.getItem("id_token")) // Decode the ID token to get user info
            console.log(userInfo);
            setUser(userInfo)
            
          })
          .catch(err=>{
            console.log("token verification error");
            localStorage.clear();
            setAuthenticated(false)
            setUser(null)
            })
          .finally(()=>setPageloaded(true))
      } else {
        setAuthenticated(false)
        setUser(null)
        localStorage.clear();
        setPageloaded(true)
      }
    }
  }, [pageloaded]);

  const handleLogOut = () => {
    localStorage.clear();
    setAuthenticated(false);
    setUser(null);
  }

  return (
    <div className='flex justify-between p-2 bg-slate-800 text-white sticky top-0'>
        <div className='font-bold font-sans'>Key-Cloak client</div>
        <nav>
            <ul>
              {authenticated ? <li><button className='cursor-pointer' onClick={handleLogOut}>Log Out</button></li>:<li><Link to={"/login"}>Login</Link></li>}
            </ul>
        </nav>
    </div>
  )
}
