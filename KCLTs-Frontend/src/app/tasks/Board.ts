import { Column } from './Column';

export class Board {
    constructor(public name: string, public columns: Column[]) {}
}