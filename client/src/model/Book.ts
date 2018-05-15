export class Book {
    private _id!: string;
    private _title: string;
    private _publisher: string;
    private _year: number;

    constructor(title: string, publisher: string, year: number) {
        this._title = title;
        this._publisher = publisher;
        this._year = year;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        this._title = value;
    }

    get publisher(): string {
        return this._publisher;
    }

    set publisher(value: string) {
        this._publisher = value;
    }

    get year(): number {
        return this._year;
    }

    set year(value: number) {
        this._year = value;
    }

    get json(): {} {
        return {
            'title': this._title,
            'publisher': this._publisher,
            'year': this._year
        }
    }
}