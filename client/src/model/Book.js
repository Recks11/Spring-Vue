export class Book {
    constructor(title, publisher, year) {
        this._title = title;
        this._publisher = publisher;
        this._year = year;
    }
    get id() {
        return this._id;
    }
    set id(value) {
        this._id = value;
    }
    get title() {
        return this._title;
    }
    set title(value) {
        this._title = value;
    }
    get publisher() {
        return this._publisher;
    }
    set publisher(value) {
        this._publisher = value;
    }
    get year() {
        return this._year;
    }
    set year(value) {
        this._year = value;
    }
    get json() {
        return {
            'title': this._title,
            'publisher': this._publisher,
            'year': this._year
        };
    }
}
//# sourceMappingURL=Book.js.map