
/**
 * Context is a way to share data between components without having to pass props manually at every level.
 * It's a way to share state and functions between components without having to pass them down manually at every level.
 */ 

import React , { createContext, useContext, useEffect, useState } from 'react';
import { User, AuthContextType } from '../types';


// Importing User and AuthContextType types from ../types.
// These types define the shape of the User object and the AuthContext object.


// Importing React, createContext, useContext, useEffect and useState from 'react'.
// These are the base components and hooks that React provides for building user interfaces.


// createContext: This is a function from the React library that creates a Context object.
// A Context object is used to share data between components without having to pass props manually at every level.

// useContext: This is a hook that lets you consume the value from the context.
// It's a way to read the context value.

// useContext: How to use useContext
// 1. Import the context object you created using createContext.
// 2. Wrap your component tree with the Provider component that you created using createContext.
// 3. Pass the value you want to share as a prop to the Provider component.
// 4. Inside one of your components, use the useContext hook to access the value of the context object.
// 5. The value you passed to the Provider component will be available to all components that are descendants of the Provider.

// Example:
// const MyContext = React.createContext();
// const MyComponent = () => {
//   const value = useContext(MyContext);
//   return <div>{value}</div>;
// };

// Importing User from '../types'.
// This type defines the shape of the User object, which includes the id, username, email, roles and createdAt properties.

// Importing AuthContextType from '../types'.
// This type defines the shape of the AuthContext object, which includes the user object and functions to login, register and logout.



// Context Creation
// 1. Import the createContext function from 'react'.
// 2. Call the createContext function to create a new context object.
// 3. Pass an argument to the createContext function.
//    This argument is the default value of the context object.
//    It should be an object or undefined.
//    In this case, the default value is undefined.
// 4. The createContext function returns a new context object.
//    This context object is used to share data between components.
//    It has a Provider component and a Consumer component.
//    The Provider component is used to provide the value of the context object to its descendants.
//    The Consumer component is used to consume the value of the context object.
//    The Consumer component takes a function as a child and passes the value of the context object to that function.
//    The function can then return JSX that will be rendered.
// 5. The context object has a displayName property that is set to the name of the context.
//    This is used for debugging purposes.
// 6. The context object can be imported and used in any component in the application.
//    To use the context object, wrap the component tree with the Provider component and pass the value of the context object as a prop to the Provider component.
//    Inside a component that needs to access the value of the context object, use the useContext hook to access the value.

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Context Provider
// 1. Import the AuthContext object you created using createContext.
// 2. Wrap your component tree with the Provider component that you created using createContext.
// 3. Pass the value you want to share as a prop to the Provider component.
// 4. Inside one of your components, use the useContext hook to access the value of the context object.
// 5. The value you passed to the Provider component will be available to all components that are descendants of the Provider.

export const AuthProvider : React.FC<{children : React.ReactNode}> = ({children}) => {
    // useState: What is useState
    // 1. Import useState from 'react'.
    // 2. Call useState to create a state variable and a function to update it.
    // 3. The initial value of the state variable is the argument you passed to useState.
    // 4. The function to update the state variable is used to update the state variable.
    // 5. The state variable is only updated when the function to update it is called.
    // 6. The state variable is not mutable.
    // 7. The function to update the state variable returns the new state variable.
    // 8. The component that used useState will re-render when the state variable is updated.
    // 9. The state variable can be any value, including objects and arrays.
    // 10. The state variable is only updated when the function to update it is called.
    // 11. The function to update the state variable is passed as a dependency to useEffect.
    // 12. The component will re-render when the state variable is updated.

    // These two state variables are used to store the user object and the loading state.
    // The user object is used to store the user information after a successful login or registration.
    // The loading state is used to show a loading spinner while the user is being fetched or while the user is being registered or logged in.

    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

   useEffect(() => {
   // Check for stored auth token
   // The JWT is stored in the local storage of the browser.
   // The JWT is sent from the server to the client after a successful login or registration.
   // The client stores the JWT in the local storage of the browser.
   // The client sends the JWT back to the server on subsequent requests in the Authorization header.
   // The server can verify the JWT using the secret key or public key.
   // If the JWT is valid, the server treats the user as authenticated.

   const token = localStorage.getItem('auth_token');
   const userData = localStorage.getItem('user_data');
   if (token && userData) {
    try {
      const user = JSON.parse(userData);
      setUser(user);
    } catch (error) {
      console.error('Error parsing user data:', error);
      localStorage.removeItem('auth_token');
      localStorage.removeItem('user_data');
    }
   } 
   setLoading(false);
   }, []);

   const login = async (email: string, password: string) => {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // Mock user data
    const mockUser: User = {
      id: '1',
      username: 'johndoe',
      email: email,
      roles: ['USER'],
      createdAt: new Date()
    };
    
    const token = 'mock_jwt_token_' + Date.now();
    localStorage.setItem('auth_token', token);
    localStorage.setItem('user_data', JSON.stringify(mockUser));
    setUser(mockUser);
  };

  const register = async (username: string, email: string, password: string) => {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const newUser: User = {
      id: Date.now().toString(),
      username,
      email,
      roles: ['USER'],
      createdAt: new Date()
    };
    
    const token = 'mock_jwt_token_' + Date.now();
    localStorage.setItem('auth_token', token);
    localStorage.setItem('user_data', JSON.stringify(newUser));
    setUser(newUser);
  };

  const logout = () => {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_data');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};