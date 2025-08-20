# JobTrackr Frontend Implementation Guide

## 🚀 Complete Step-by-Step Frontend Implementation

This guide walks you through implementing the JobTrackr frontend from scratch, covering every aspect from project setup to deployment.

---

## 📋 Table of Contents

1. [Project Setup & Dependencies](#1-project-setup--dependencies)
2. [Project Structure](#2-project-structure)
3. [TypeScript Types](#3-typescript-types)
4. [UI Component Library](#4-ui-component-library)
5. [Authentication System](#5-authentication-system)
6. [API Service Layer](#6-api-service-layer)
7. [Core Features Implementation](#7-core-features-implementation)
8. [Routing & Navigation](#8-routing--navigation)
9. [State Management](#9-state-management)
10. [Styling & Theming](#10-styling--theming)
11. [Testing Strategy](#11-testing-strategy)
12. [Performance Optimization](#12-performance-optimization)
13. [Deployment](#13-deployment)

---

## 1. Project Setup & Dependencies

### Step 1.1: Initialize Vite React Project

```bash
# Create new Vite project with React + TypeScript
npm create vite@latest jobtrackr -- --template react-ts
cd jobtrackr
npm install
```

### Step 1.2: Install Core Dependencies

```bash
# UI Framework & Styling
npm install @mui/material@^5.15.0 @emotion/react@^11.14.0 @emotion/styled@^11.14.1
npm install tailwindcss@latest postcss@latest autoprefixer@latest
npm install lucide-react@latest

# State Management & Data Fetching
npm install @tanstack/react-query@latest

# Routing
npm install react-router-dom@latest

# Forms & Validation
npm install react-hook-form@latest @hookform/resolvers@latest yup@latest

# Charts & Visualization
npm install recharts@latest

# Date Utilities
npm install date-fns@latest

# HTTP Client
npm install axios@latest
```

### Step 1.3: Install Development Dependencies

```bash
npm install -D @types/react@latest @types/react-dom@latest
npm install -D eslint@latest @typescript-eslint/eslint-plugin@latest
npm install -D @typescript-eslint/parser@latest
```

### Step 1.4: Configure Tailwind CSS

```bash
# Initialize Tailwind
npx tailwindcss init -p
```

**tailwind.config.js:**
```javascript
/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#eff6ff',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
        }
      }
    },
  },
  plugins: [],
};
```

**src/index.css:**
```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

---

## 2. Project Structure

### Step 2.1: Create Directory Structure

```
src/
├── components/
│   ├── ui/                 # Reusable UI components
│   │   ├── Button.tsx
│   │   ├── Input.tsx
│   │   ├── Select.tsx
│   │   ├── Modal.tsx
│   │   └── StatusBadge.tsx
│   ├── auth/              # Authentication components
│   │   ├── LoginForm.tsx
│   │   └── RegisterForm.tsx
│   ├── applications/      # Application management
│   │   ├── ApplicationCard.tsx
│   │   ├── ApplicationForm.tsx
│   │   └── ApplicationFilters.tsx
│   ├── dashboard/         # Dashboard components
│   │   ├── Dashboard.tsx
│   │   ├── StatsCard.tsx
│   │   └── StatusChart.tsx
│   ├── layout/           # Layout components
│   │   └── Navbar.tsx
│   └── uml/              # UML diagram components
│       └── UMLDiagram.tsx
├── contexts/             # React contexts
│   └── AuthContext.tsx
├── pages/               # Page components
│   ├── LandingPage.tsx
│   ├── AuthPage.tsx
│   ├── ApplicationsPage.tsx
│   ├── AnalyticsPage.tsx
│   ├── ProfilePage.tsx
│   └── UMLPage.tsx
├── services/            # API services
│   └── api.ts
├── types/               # TypeScript type definitions
│   └── index.ts
├── utils/               # Utility functions
│   └── helpers.ts
├── App.tsx
└── main.tsx
```

---

## 3. TypeScript Types

### Step 3.1: Define Core Types

**src/types/index.ts:**
```typescript
export interface User {
  id: string;
  username: string;
  email: string;
  roles: ('USER' | 'ADMIN')[];
  createdAt: Date;
}

export interface JobApplication {
  id: string;
  title: string;
  company: string;
  location: string;
  status: 'Saved' | 'Applied' | 'Interview' | 'Offer' | 'Rejected';
  dateApplied: Date;
  jobLink?: string;
  resumeFile?: string;
  coverLetterFile?: string;
  notes?: string;
  userId: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<void>;
  register: (username: string, email: string, password: string) => Promise<void>;
  logout: () => void;
  isLoading: boolean;
}

export interface ApplicationFilters {
  status?: JobApplication['status'];
  company?: string;
  dateFrom?: Date;
  dateTo?: Date;
  search?: string;
}
```

---

## 4. UI Component Library

### Step 4.1: Create Button Component

**src/components/ui/Button.tsx:**
```typescript
import React from 'react';
import { Button as MuiButton, CircularProgress } from '@mui/material';
import { LucideIcon } from 'lucide-react';

interface ButtonProps {
  children: React.ReactNode;
  variant?: 'contained' | 'outlined' | 'text';
  color?: 'primary' | 'secondary' | 'error' | 'warning' | 'info' | 'success';
  size?: 'small' | 'medium' | 'large';
  icon?: LucideIcon;
  loading?: boolean;
  onClick?: () => void;
  type?: 'button' | 'submit' | 'reset';
  disabled?: boolean;
  fullWidth?: boolean;
  className?: string;
}

export const Button: React.FC<ButtonProps> = ({
  children,
  variant = 'contained',
  color = 'primary',
  size = 'medium',
  icon: Icon,
  loading = false,
  onClick,
  type = 'button',
  disabled,
  fullWidth = false,
  className = '',
  ...props
}) => {
  return (
    <MuiButton
      variant={variant}
      color={color}
      size={size}
      onClick={onClick}
      type={type}
      disabled={disabled || loading}
      fullWidth={fullWidth}
      className={className}
      startIcon={loading ? <CircularProgress size={16} /> : Icon ? <Icon size={16} /> : undefined}
      {...props}
    >
      {children}
    </MuiButton>
  );
};
```

### Step 4.2: Create Input Component

**src/components/ui/Input.tsx:**
```typescript
import React from 'react';
import { TextField } from '@mui/material';

interface InputProps {
  label?: string;
  error?: string;
  helperText?: string;
  type?: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  disabled?: boolean;
  fullWidth?: boolean;
  multiline?: boolean;
  rows?: number;
  name?: string;
  required?: boolean;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(({
  label,
  error,
  helperText,
  type = 'text',
  placeholder,
  value,
  onChange,
  disabled = false,
  fullWidth = true,
  multiline = false,
  rows,
  name,
  required = false,
  ...props
}, ref) => {
  return (
    <TextField
      ref={ref}
      label={label}
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      disabled={disabled}
      fullWidth={fullWidth}
      multiline={multiline}
      rows={rows}
      name={name}
      required={required}
      error={!!error}
      helperText={error || helperText}
      variant="outlined"
      size="medium"
      {...props}
    />
  );
});

Input.displayName = 'Input';
```

### Step 4.3: Create Select Component

**src/components/ui/Select.tsx:**
```typescript
import React from 'react';
import { FormControl, InputLabel, Select as MuiSelect, MenuItem, FormHelperText } from '@mui/material';

interface SelectProps {
  label?: string;
  error?: string;
  options: { value: string; label: string }[];
  value?: string;
  onChange?: (e: any) => void;
  disabled?: boolean;
  fullWidth?: boolean;
  name?: string;
  required?: boolean;
}

export const Select = React.forwardRef<HTMLSelectElement, SelectProps>(({
  label,
  error,
  options,
  value,
  onChange,
  disabled = false,
  fullWidth = true,
  name,
  required = false,
  ...props
}, ref) => {
  return (
    <FormControl fullWidth={fullWidth} error={!!error} disabled={disabled}>
      {label && <InputLabel>{label}</InputLabel>}
      <MuiSelect
        ref={ref}
        value={value || ''}
        onChange={onChange}
        label={label}
        name={name}
        required={required}
        {...props}
      >
        {options.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        ))}
      </MuiSelect>
      {error && <FormHelperText>{error}</FormHelperText>}
    </FormControl>
  );
});

Select.displayName = 'Select';
```

### Step 4.4: Create Modal Component

**src/components/ui/Modal.tsx:**
```typescript
import React from 'react';
import { Dialog, DialogTitle, DialogContent, IconButton } from '@mui/material';
import { X } from 'lucide-react';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
}

export const Modal: React.FC<ModalProps> = ({
  isOpen,
  onClose,
  title,
  children,
  size = 'md'
}) => {
  return (
    <Dialog
      open={isOpen}
      onClose={onClose}
      maxWidth={size}
      fullWidth
      PaperProps={{
        sx: {
          borderRadius: 2,
        }
      }}
    >
      <DialogTitle sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        {title}
        <IconButton onClick={onClose} size="small">
          <X size={16} />
        </IconButton>
      </DialogTitle>
      <DialogContent>
        {children}
      </DialogContent>
    </Dialog>
  );
};
```

### Step 4.5: Create StatusBadge Component

**src/components/ui/StatusBadge.tsx:**
```typescript
import React from 'react';
import { Chip } from '@mui/material';
import { JobApplication } from '../../types';

interface StatusBadgeProps {
  status: JobApplication['status'];
}

export const StatusBadge: React.FC<StatusBadgeProps> = ({ status }) => {
  const statusConfig = {
    Saved: { color: 'default' as const, variant: 'outlined' as const },
    Applied: { color: 'primary' as const, variant: 'filled' as const },
    Interview: { color: 'warning' as const, variant: 'filled' as const },
    Offer: { color: 'success' as const, variant: 'filled' as const },
    Rejected: { color: 'error' as const, variant: 'filled' as const }
  };

  const config = statusConfig[status];

  return (
    <Chip
      label={status}
      color={config.color}
      variant={config.variant}
      size="small"
    />
  );
};
```

---

## 5. Authentication System

### Step 5.1: Create Auth Context

**src/contexts/AuthContext.tsx:**
```typescript
import React, { createContext, useContext, useEffect, useState } from 'react';
import { User, AuthContextType } from '../types';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Check for stored auth token
    const token = localStorage.getItem('auth_token');
    const userData = localStorage.getItem('user_data');
    
    if (token && userData) {
      try {
        const parsedUser = JSON.parse(userData);
        setUser(parsedUser);
      } catch (error) {
        console.error('Error parsing stored user data:', error);
        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_data');
      }
    }
    
    setIsLoading(false);
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
    <AuthContext.Provider value={{ user, login, register, logout, isLoading }}>
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
```

### Step 5.2: Create Login Form

**src/components/auth/LoginForm.tsx:**
```typescript
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { Paper, Typography, Box, Alert } from '@mui/material';
import { LogIn } from 'lucide-react';
import { useAuth } from '../../contexts/AuthContext';
import { Input } from '../ui/Input';
import { Button } from '../ui/Button';

const schema = yup.object().shape({
  email: yup.string().email('Invalid email').required('Email is required'),
  password: yup.string().required('Password is required')
});

type FormData = {
  email: string;
  password: string;
};

interface LoginFormProps {
  onToggleMode: () => void;
}

export const LoginForm: React.FC<LoginFormProps> = ({ onToggleMode }) => {
  const { login } = useAuth();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: yupResolver(schema),
    defaultValues: {
      email: 'john@example.com',
      password: 'password'
    }
  });

  const onSubmit = async (data: FormData) => {
    try {
      setIsLoading(true);
      setError('');
      await login(data.email, data.password);
    } catch (err) {
      setError('Login failed. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box sx={{ width: '100%', maxWidth: 400, mx: 'auto' }}>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 2 }}>
        <Box sx={{ textAlign: 'center', mb: 4 }}>
          <Typography variant="h4" component="h2" gutterBottom>
            Welcome Back
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Sign in to your account
          </Typography>
        </Box>

        <Box component="form" onSubmit={handleSubmit(onSubmit)} sx={{ '& > *': { mb: 3 } }}>
          <Input
            label="Email"
            type="email"
            placeholder="john@example.com"
            error={errors.email?.message}
            {...register('email')}
          />

          <Input
            label="Password"
            type="password"
            placeholder="Enter your password"
            error={errors.password?.message}
            {...register('password')}
          />

          {error && (
            <Alert severity="error">
              {error}
            </Alert>
          )}

          <Button
            type="submit"
            fullWidth
            loading={isLoading}
            icon={LogIn}
            size="large"
          >
            Sign In
          </Button>
        </Box>

        <Box sx={{ mt: 3, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary">
            Don't have an account?{' '}
            <Box component="button"
              onClick={onToggleMode}
              sx={{ color: 'primary.main', textDecoration: 'none', cursor: 'pointer', border: 'none', background: 'none', fontWeight: 500 }}
            >
              Sign up
            </Box>
          </Typography>
        </Box>
      </Paper>
    </Box>
  );
};
```

---

## 6. API Service Layer

### Step 6.1: Create API Service

**src/services/api.ts:**
```typescript
import axios from 'axios';
import { JobApplication, ApplicationFilters } from '../types';

// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle auth errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('auth_token');
      localStorage.removeItem('user_data');
      window.location.href = '/auth';
    }
    return Promise.reject(error);
  }
);

export const applicationApi = {
  getAll: async (filters?: ApplicationFilters): Promise<JobApplication[]> => {
    // Mock data for demo
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const mockApplications: JobApplication[] = [
      {
        id: '1',
        title: 'Senior Frontend Developer',
        company: 'Google',
        location: 'Mountain View, CA',
        status: 'Interview',
        dateApplied: new Date('2024-01-15'),
        jobLink: 'https://careers.google.com/jobs/123',
        notes: 'Great opportunity, team seems amazing.',
        userId: 'demo-user-123',
        createdAt: new Date('2024-01-15'),
        updatedAt: new Date('2024-01-20')
      },
      // Add more mock data...
    ];
    
    // Apply filters
    let filteredApplications = mockApplications;
    
    if (filters?.status) {
      filteredApplications = filteredApplications.filter(app => app.status === filters.status);
    }
    
    if (filters?.search) {
      const searchTerm = filters.search.toLowerCase();
      filteredApplications = filteredApplications.filter(app => 
        app.title.toLowerCase().includes(searchTerm) ||
        app.company.toLowerCase().includes(searchTerm)
      );
    }
    
    return filteredApplications;
  },

  getById: async (id: string): Promise<JobApplication | null> => {
    const applications = await applicationApi.getAll();
    return applications.find(app => app.id === id) || null;
  },

  create: async (data: Omit<JobApplication, 'id' | 'userId' | 'createdAt' | 'updatedAt'>): Promise<JobApplication> => {
    await new Promise(resolve => setTimeout(resolve, 800));
    
    const newApplication: JobApplication = {
      id: Date.now().toString(),
      ...data,
      userId: 'demo-user-123',
      createdAt: new Date(),
      updatedAt: new Date()
    };
    
    return newApplication;
  },

  update: async (id: string, data: Partial<JobApplication>): Promise<JobApplication> => {
    await new Promise(resolve => setTimeout(resolve, 600));
    
    const existingApp = await applicationApi.getById(id);
    if (!existingApp) {
      throw new Error('Application not found');
    }
    
    const updatedApplication: JobApplication = {
      ...existingApp,
      ...data,
      updatedAt: new Date()
    };
    
    return updatedApplication;
  },

  delete: async (id: string): Promise<void> => {
    await new Promise(resolve => setTimeout(resolve, 400));
  },

  getStats: async () => {
    const applications = await applicationApi.getAll();
    
    return {
      total: applications.length,
      saved: applications.filter(app => app.status === 'Saved').length,
      applied: applications.filter(app => app.status === 'Applied').length,
      interview: applications.filter(app => app.status === 'Interview').length,
      offer: applications.filter(app => app.status === 'Offer').length,
      rejected: applications.filter(app => app.status === 'Rejected').length
    };
  }
};

export const authApi = {
  login: async (email: string, password: string) => {
    const response = await api.post('/auth/signin', { email, password });
    return response.data;
  },

  register: async (username: string, email: string, password: string) => {
    const response = await api.post('/auth/signup', { username, email, password });
    return response.data;
  }
};
```

---

## 7. Core Features Implementation

### Step 7.1: Create Dashboard

**src/components/dashboard/Dashboard.tsx:**
```typescript
import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { Briefcase, Clock, CheckCircle, XCircle, Target } from 'lucide-react';
import { applicationApi } from '../../services/api';
import { StatsCard } from './StatsCard';
import { StatusChart } from './StatusChart';

export const Dashboard: React.FC = () => {
  const { data: applications = [] } = useQuery({
    queryKey: ['applications'],
    queryFn: () => applicationApi.getAll()
  });

  const stats = {
    total: applications.length,
    applied: applications.filter(app => app.status === 'Applied').length,
    interviews: applications.filter(app => app.status === 'Interview').length,
    offers: applications.filter(app => app.status === 'Offer').length,
    rejected: applications.filter(app => app.status === 'Rejected').length
  };

  const chartData = [
    { name: 'Saved', value: applications.filter(app => app.status === 'Saved').length, color: '#6B7280' },
    { name: 'Applied', value: stats.applied, color: '#3B82F6' },
    { name: 'Interview', value: stats.interviews, color: '#F59E0B' },
    { name: 'Offer', value: stats.offers, color: '#10B981' },
    { name: 'Rejected', value: stats.rejected, color: '#EF4444' }
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="text-gray-600 mt-1">Track your job application progress</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
        <StatsCard
          title="Total Applications"
          value={stats.total}
          icon={Briefcase}
          color="bg-blue-500"
        />
        <StatsCard
          title="Applied"
          value={stats.applied}
          icon={Clock}
          color="bg-yellow-500"
        />
        <StatsCard
          title="Interviews"
          value={stats.interviews}
          icon={Target}
          color="bg-orange-500"
        />
        <StatsCard
          title="Offers"
          value={stats.offers}
          icon={CheckCircle}
          color="bg-green-500"
        />
        <StatsCard
          title="Rejected"
          value={stats.rejected}
          icon={XCircle}
          color="bg-red-500"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Application Status Distribution</h3>
          <StatusChart data={chartData} />
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Activity</h3>
          <div className="space-y-3">
            {applications.slice(0, 5).map(app => (
              <div key={app.id} className="flex items-center justify-between py-2 border-b border-gray-100 last:border-0">
                <div>
                  <p className="text-sm font-medium text-gray-900">{app.title}</p>
                  <p className="text-xs text-gray-500">{app.company}</p>
                </div>
                <div className="text-xs text-gray-400">
                  {app.dateApplied.toLocaleDateString()}
                </div>
              </div>
            ))}
            {applications.length === 0 && (
              <p className="text-gray-500 text-center py-4">No applications yet</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
```

### Step 7.2: Create Application Management

**src/components/applications/ApplicationCard.tsx:**
```typescript
import React from 'react';
import { format } from 'date-fns';
import { MapPin, Calendar, ExternalLink, Edit, Trash2, FileText } from 'lucide-react';
import { JobApplication } from '../../types';
import { StatusBadge } from '../ui/StatusBadge';
import { Button } from '../ui/Button';

interface ApplicationCardProps {
  application: JobApplication;
  onEdit: (application: JobApplication) => void;
  onDelete: (id: string) => void;
  onView: (application: JobApplication) => void;
}

export const ApplicationCard: React.FC<ApplicationCardProps> = ({
  application,
  onEdit,
  onDelete,
  onView
}) => {
  return (
    <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow duration-200">
      <div className="flex justify-between items-start mb-4">
        <div className="flex-1">
          <h3 className="text-lg font-semibold text-gray-900 mb-1">
            {application.title}
          </h3>
          <p className="text-gray-600 font-medium">{application.company}</p>
        </div>
        <StatusBadge status={application.status} />
      </div>

      <div className="space-y-2 mb-4">
        <div className="flex items-center text-sm text-gray-600">
          <MapPin className="w-4 h-4 mr-2" />
          {application.location}
        </div>
        <div className="flex items-center text-sm text-gray-600">
          <Calendar className="w-4 h-4 mr-2" />
          Applied on {format(application.dateApplied, 'MMM dd, yyyy')}
        </div>
      </div>

      {application.notes && (
        <div className="mb-4">
          <p className="text-sm text-gray-700 line-clamp-2">{application.notes}</p>
        </div>
      )}

      <div className="flex justify-between items-center">
        <div className="flex space-x-2">
          {application.jobLink && (
            <a
              href={application.jobLink}
              target="_blank"
              rel="noopener noreferrer"
              className="text-blue-600 hover:text-blue-700 transition-colors"
            >
              <ExternalLink className="w-4 h-4" />
            </a>
          )}
          {application.resumeFile && (
            <div className="text-green-600">
              <FileText className="w-4 h-4" />
            </div>
          )}
        </div>

        <div className="flex space-x-2">
          <Button
            variant="text"
            size="small"
            onClick={() => onView(application)}
          >
            View
          </Button>
          <Button
            variant="text"
            size="small"
            icon={Edit}
            onClick={() => onEdit(application)}
          >
            Edit
          </Button>
          <Button
            variant="text"
            size="small"
            color="error"
            icon={Trash2}
            onClick={() => onDelete(application.id)}
          >
            Delete
          </Button>
        </div>
      </div>
    </div>
  );
};
```

---

## 8. Routing & Navigation

### Step 8.1: Setup React Router

**src/App.tsx:**
```typescript
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { LandingPage } from './pages/LandingPage';
import { Navbar } from './components/layout/Navbar';
import { Dashboard } from './components/dashboard/Dashboard';
import { ApplicationsPage } from './pages/ApplicationsPage';
import { UMLPage } from './pages/UMLPage';
import { AuthPage } from './pages/AuthPage';
import { AnalyticsPage } from './pages/AnalyticsPage';
import { ProfilePage } from './pages/ProfilePage';

const queryClient = new QueryClient();

const theme = createTheme({
  palette: {
    primary: {
      main: '#3B82F6',
    },
    secondary: {
      main: '#6B7280',
    },
  },
  typography: {
    fontFamily: 'Inter, system-ui, Avenir, Helvetica, Arial, sans-serif',
  },
  shape: {
    borderRadius: 8,
  },
});

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user } = useAuth();
  
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {children}
      </main>
    </div>
  );
};

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <AuthProvider>
          <Router>
            <Routes>
              <Route path="/" element={<LandingPage />} />
              <Route path="/auth" element={<AuthPage />} />
              <Route path="/dashboard" element={
                <ProtectedRoute>
                  <Dashboard />
                </ProtectedRoute>
              } />
              <Route path="/applications" element={
                <ProtectedRoute>
                  <ApplicationsPage />
                </ProtectedRoute>
              } />
              <Route path="/analytics" element={
                <ProtectedRoute>
                  <AnalyticsPage />
                </ProtectedRoute>
              } />
              <Route path="/profile" element={
                <ProtectedRoute>
                  <ProfilePage />
                </ProtectedRoute>
              } />
              <Route path="/uml" element={
                <ProtectedRoute>
                  <UMLPage />
                </ProtectedRoute>
              } />
              <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
          </Router>
        </AuthProvider>
      </ThemeProvider>
    </QueryClientProvider>
  );
}

export default App;
```

### Step 8.2: Create Navigation Component

**src/components/layout/Navbar.tsx:**
```typescript
import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Briefcase, LogOut, User, Plus, Database } from 'lucide-react';
import { useAuth } from '../../contexts/AuthContext';
import { Button } from '../ui/Button';

export const Navbar: React.FC = () => {
  const { logout, user } = useAuth();
  const location = useLocation();

  return (
    <nav className="bg-white shadow-sm border-b">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex items-center">
            <Link to="/" className="flex items-center space-x-2">
              <Briefcase className="w-8 h-8 text-blue-600" />
              <span className="text-xl font-bold text-gray-900">JobTrackr</span>
            </Link>
            
            {user && (
              <div className="ml-8 flex items-center space-x-6">
                <Link
                  to="/dashboard"
                  className={`${
                    location.pathname === '/dashboard'
                      ? 'text-blue-600 border-b-2 border-blue-600'
                      : 'text-gray-700 hover:text-blue-600'
                  } pb-4 transition-colors font-medium`}
                >
                  Dashboard
                </Link>
                <Link
                  to="/applications"
                  className={`${
                    location.pathname === '/applications'
                      ? 'text-blue-600 border-b-2 border-blue-600'
                      : 'text-gray-700 hover:text-blue-600'
                  } pb-4 transition-colors font-medium`}
                >
                  Applications
                </Link>
                <Link
                  to="/analytics"
                  className={`${
                    location.pathname === '/analytics'
                      ? 'text-blue-600 border-b-2 border-blue-600'
                      : 'text-gray-700 hover:text-blue-600'
                  } pb-4 transition-colors font-medium`}
                >
                  Analytics
                </Link>
                <Link
                  to="/profile"
                  className={`${
                    location.pathname === '/profile'
                      ? 'text-blue-600 border-b-2 border-blue-600'
                      : 'text-gray-700 hover:text-blue-600'
                  } pb-4 transition-colors font-medium`}
                >
                  Profile
                </Link>
                <Link
                  to="/uml"
                  className={`${
                    location.pathname === '/uml'
                      ? 'text-blue-600 border-b-2 border-blue-600'
                      : 'text-gray-700 hover:text-blue-600'
                  } pb-4 transition-colors font-medium flex items-center`}
                >
                  <Database className="w-4 h-4 mr-1" />
                  Data Model
                </Link>
              </div>
            )}
          </div>

          <div className="flex items-center space-x-4">
            {user && (
              <>
                <div className="flex items-center space-x-2 text-sm text-gray-700">
                  <User className="w-4 h-4" />
                  <span>{user.username}</span>
                </div>
                
                <Button
                  variant="text"
                  icon={LogOut}
                  onClick={logout}
                  size="small"
                >
                  Logout
                </Button>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
```

---

## 9. State Management

### Step 9.1: Setup React Query

**src/main.tsx:**
```typescript
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
```

### Step 9.2: Query Configuration

Add to your App.tsx:
```typescript
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 5 * 60 * 1000, // 5 minutes
      cacheTime: 10 * 60 * 1000, // 10 minutes
      retry: 1,
      refetchOnWindowFocus: false,
    },
  },
});
```

---

## 10. Styling & Theming

### Step 10.1: Material-UI Theme

```typescript
const theme = createTheme({
  palette: {
    primary: {
      main: '#3B82F6',
      light: '#60A5FA',
      dark: '#2563EB',
    },
    secondary: {
      main: '#6B7280',
      light: '#9CA3AF',
      dark: '#4B5563',
    },
    success: {
      main: '#10B981',
    },
    warning: {
      main: '#F59E0B',
    },
    error: {
      main: '#EF4444',
    },
  },
  typography: {
    fontFamily: 'Inter, system-ui, Avenir, Helvetica, Arial, sans-serif',
    h1: {
      fontSize: '2rem',
      fontWeight: 700,
      lineHeight: 1.2,
    },
    h2: {
      fontSize: '1.5rem',
      fontWeight: 600,
      lineHeight: 1.3,
    },
    body1: {
      fontSize: '1rem',
      lineHeight: 1.5,
    },
  },
  shape: {
    borderRadius: 8,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          fontWeight: 500,
        },
      },
    },
    MuiTextField: {
      defaultProps: {
        variant: 'outlined',
      },
    },
  },
});
```

### Step 10.2: Responsive Design

Use Tailwind CSS classes for responsive design:
```css
/* Mobile First Approach */
.container {
  @apply px-4 sm:px-6 lg:px-8;
}

.grid-responsive {
  @apply grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6;
}

.text-responsive {
  @apply text-sm sm:text-base lg:text-lg;
}
```

---

## 11. Testing Strategy

### Step 11.1: Install Testing Dependencies

```bash
npm install -D @testing-library/react @testing-library/jest-dom @testing-library/user-event
npm install -D vitest jsdom
```

### Step 11.2: Configure Vitest

**vite.config.ts:**
```typescript
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/test/setup.ts',
  },
});
```

### Step 11.3: Test Examples

**src/components/ui/Button.test.tsx:**
```typescript
import { render, screen, fireEvent } from '@testing-library/react';
import { Button } from './Button';

describe('Button Component', () => {
  it('renders button with text', () => {
    render(<Button>Click me</Button>);
    expect(screen.getByText('Click me')).toBeInTheDocument();
  });

  it('calls onClick when clicked', () => {
    const handleClick = vi.fn();
    render(<Button onClick={handleClick}>Click me</Button>);
    
    fireEvent.click(screen.getByText('Click me'));
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  it('shows loading state', () => {
    render(<Button loading>Loading</Button>);
    expect(screen.getByRole('progressbar')).toBeInTheDocument();
  });
});
```

---

## 12. Performance Optimization

### Step 12.1: Code Splitting

```typescript
import { lazy, Suspense } from 'react';

const Dashboard = lazy(() => import('./components/dashboard/Dashboard'));
const ApplicationsPage = lazy(() => import('./pages/ApplicationsPage'));

// In your routes:
<Route path="/dashboard" element={
  <Suspense fallback={<div>Loading...</div>}>
    <Dashboard />
  </Suspense>
} />
```

### Step 12.2: Memoization

```typescript
import { memo, useMemo, useCallback } from 'react';

const ApplicationCard = memo(({ application, onEdit, onDelete }) => {
  const formattedDate = useMemo(() => 
    format(application.dateApplied, 'MMM dd, yyyy'), 
    [application.dateApplied]
  );

  const handleEdit = useCallback(() => {
    onEdit(application);
  }, [application, onEdit]);

  return (
    // Component JSX
  );
});
```

### Step 12.3: Bundle Analysis

```bash
npm install -D rollup-plugin-visualizer

# Add to vite.config.ts
import { visualizer } from 'rollup-plugin-visualizer';

export default defineConfig({
  plugins: [
    react(),
    visualizer({
      filename: 'dist/stats.html',
      open: true,
    }),
  ],
});
```

---

## 13. Deployment

### Step 13.1: Build for Production

```bash
npm run build
```

### Step 13.2: Deploy to Netlify

```bash
# Install Netlify CLI
npm install -g netlify-cli

# Build and deploy
npm run build
netlify deploy --prod --dir=dist
```

### Step 13.3: Deploy to Vercel

```bash
# Install Vercel CLI
npm install -g vercel

# Deploy
vercel --prod
```

### Step 13.4: Environment Variables

Create `.env.production`:
```
VITE_API_URL=https://your-api-domain.com/api
VITE_APP_NAME=JobTrackr
```

---

## 🎯 Implementation Checklist

### Phase 1: Foundation ✅
- [ ] Project setup with Vite + React + TypeScript
- [ ] Install and configure dependencies
- [ ] Setup Tailwind CSS and Material-UI
- [ ] Create project structure
- [ ] Define TypeScript types

### Phase 2: Core Components ✅
- [ ] Build UI component library (Button, Input, Select, Modal, StatusBadge)
- [ ] Create authentication system (Context, Login/Register forms)
- [ ] Setup API service layer with mock data
- [ ] Implement routing with React Router

### Phase 3: Features ✅
- [ ] Build Dashboard with stats and charts
- [ ] Create Application management (CRUD operations)
- [ ] Implement filtering and search
- [ ] Add Analytics page with detailed charts
- [ ] Build Profile management page

### Phase 4: Polish ✅
- [ ] Add responsive design
- [ ] Implement loading states and error handling
- [ ] Add form validation
- [ ] Create UML documentation page
- [ ] Add micro-interactions and animations

### Phase 5: Testing & Deployment ✅
- [ ] Write unit tests for components
- [ ] Add integration tests
- [ ] Performance optimization
- [ ] Build and deploy to production

---

## 🚀 Next Steps

1. **Start with Phase 1** - Get the foundation solid
2. **Build incrementally** - Complete each phase before moving to the next
3. **Test frequently** - Test components as you build them
4. **Iterate and improve** - Refine based on user feedback
5. **Add real backend** - Replace mock data with actual API calls

This guide provides a complete roadmap for building a professional, production-ready React application with modern best practices and tools! 🎉