export interface User {
    id: string;
    name: string;
    email: string;
    password: string;
    createdAt: string;
    updatedAt: string;
}

export interface JobApplication {
    id: string;
    userId: string;
    jobId: string;
    createdAt: Date;
    updatedAt: string;
    status : "Saved" | "Applied" | "Interview" | "Offer" | "Rejected";
    jobLink?: string;
    notes?: string;
    User?: User;
}

export interface AuthContextType {
    user: User | null;
    login: (email: string, password: string ) => Promise<void>;
    isLoading: boolean;
    logout: () => void;
}

export interface ApplicationFilters {
    status: JobApplication["status"];
    jobLink?: string;
    notes?: string;
    company?: string;
    dateTo?: Date;
    dateFrom?: Date;
    search?: string;
}