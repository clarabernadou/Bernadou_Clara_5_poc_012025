export interface Register {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface Login {
    email: string;
    password: string;
}

export interface AuthToken {
    token: string;
}

export interface Session {
    isLogged: boolean;
    sessionInformation?: AuthToken;
}