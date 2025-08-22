/**
 * Context is a way to share data between components without having to pass props manually at every level.
 * It's a way to share state and functions between components without having to pass them down manually at every level.
 *
 * Context is implemented using the React.createContext() function.
 * This function creates a Context object that can be used to provide and consume some data.
 *
 * The value passed to the Provider component will be available to all components that are descendants of the Provider.
 *
 * The useContext() hook is used to consume the value from the context.
 *
 * Note: The useContext() hook only lets you read the context value.
 * You can’t use it to update the context value.
 *
 * Note: If you want to update the context value, you need to use a different approach like state management libraries like Redux or MobX.
 */

import React , { createContext  , useContext , useEffect , useState} from "react";

/**
 * Importing React: It's a library for building user interfaces in JavaScript.
 * It provides components, which are reusable pieces of code that represent parts of the user interface.
 *
 * Importing createContext: It's a function that creates a context object.
 * Context is a way to share data between components without having to pass props manually at every level.
 * It's a way to share state and functions between components without having to pass them down manually at every level.
 *
 * Importing useContext: It's a hook that lets you consume the value from the context.
 * It's a way to read the context value.
 *
 * Importing useEffect: It's a hook that lets you use effects in functional components.
 * It's a way to perform side effects in functional components, like data fetching or subscriptions.
 *
 * Importing useState: It's a hook that lets you add state to functional components.
 * It's a way to add state to functional components, like a variable that can change over time.
 */


import { AuthContextType , User } from "../types";

/**
 * Importing AuthContextType and User types from ../types.
 * These types define the shape of the AuthContext and User objects.
 *
 * The AuthContextType defines the shape of the AuthContext object.
 * It includes the user object and functions to login, register and logout.
 *
 * The User type defines the shape of the user object.
 * It includes the id, username, email, roles and createdAt properties.
 */

const AuthContext = createContext<AuthContextType | undefined>(undefined);
