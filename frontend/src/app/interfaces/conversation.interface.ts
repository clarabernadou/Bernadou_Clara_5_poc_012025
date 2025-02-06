import { User } from "./user.interface";

export interface Message {
    id: number;
    userId: number;
    conversationId: number;
    content: string;
    createdAt: Date;
    updatedAt: Date;
}

export interface Conversation {
    id: number;
    user1Id: User;
    user2Id: User;
    createdAt: Date;
    updatedAt: Date;
}