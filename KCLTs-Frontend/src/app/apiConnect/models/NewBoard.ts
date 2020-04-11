export class NewBoard {
    public boardId: number;
    public title: string;
    public backgroundImagePath: string;
    public admin: number;
    public users: number[] = [];
    constructor() {
    }
}