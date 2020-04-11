import { User } from 'src/app/apiConnect/models/User';
export class Board {
    public boardId: number;
    public title: string;
    public backgroundImagePath: string;
    public users: User[];
    public admin: number;
    constructor() {
    }
}