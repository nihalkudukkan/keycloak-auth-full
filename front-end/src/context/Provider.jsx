import React, { useState } from 'react'
import Context from './Context'

export default function Provider({children}) {
    const [pageloaded, setPageloaded] = useState(false)
    const [authenticated, setAuthenticated] = useState(false)
    const [user, setUser] = useState()
    return (
        <Context.Provider value={{authenticated, setAuthenticated, pageloaded, setPageloaded, user, setUser}}>
            {children}
        </Context.Provider>
    )
}
