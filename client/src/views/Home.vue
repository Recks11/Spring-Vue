<template>
    <div class="home">
        <img src="../assets/logo.png">
        <div class="notification" :class="{'is-warning': hasError, 'is-primary': !hasError}" v-if="showReport === true">
            <button class="delete" @click="showReport = false"></button>
            {{ reportText }}
        </div>
        <table class="table is-bordered is-fullwidth">
            <thead>
            <tr>
                <td>Title</td>
                <td>Publisher</td>
                <td>Year</td>
                <td>Action</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="book in books">
                <td>{{book.title}}</td>
                <td>{{book.publisher}}</td>
                <td>{{book.year}}</td>
                <td><button class="delete" @click="deleteBook(book.id)"></button></td>
            </tr>
            </tbody>
        </table>
        <hr/>
        <div class="comumns has-text-left">
            <div class="column ">
                <label>Title</label>
                <input class="input" type="text" v-model="book.title" placeholder="Title"/>
            </div>
            <div class="column">
                <label>Publisher</label>
                <input class="input" type="text" v-model="book.publisher" placeholder="Publisher"/>
            </div>
            <div class="column">
                <label>Year</label>
                <input class="input" type="number" min="1990" v-model="book.year" placeholder="Year"/>
            </div>
        </div>
        <button class="button is-primary" @click="createBook()">Create Book</button>
        <hr/>
        <br/>
        <button class="button is-success" @click="getBooks()">Axios Get Books</button>
        <button class="button is-success" @click="getBooksFromObservable()">Ajax Observable Get Books</button>
        <button class="button is-success" @click="handleWithEventSource()">EventSource Get Books</button>
        <button class="button is-warning" @click="books = []; showReport = false">Clear Books</button>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {Book} from '@/model/Book';
    import {AxiosResponse} from 'axios';
    import {ajax} from 'rxjs/ajax';

    @Component
    export default class Home extends Vue {
        public books: Array<Book> = [];
        public book: Book = new Book('', '', 1990);
        public showReport: boolean = false;
        public reportText: string = '';
        public hasError: boolean = false;

        public createBook(): void {
            ajax({
                url: 'http://localhost:8080/api/books',
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: this.book.json
            })
                .subscribe((value) => {
                        let data: Book = value.response;
                        console.log(data);
                        this.books.push(data);
                        this.changeShowReport(true, 'Book Added', false)
                    },
                    (error) => {
                        this.changeShowReport(true, 'There Was an error', true);
                    });
        }

        public getBooks(): Array<Book> {
            let allBooks: Array<Book> = [];

            this.axios.get('http://localhost:8080/api/books')
                .then((response: AxiosResponse<Book[]>) => {
                    this.books = response.data;
                    this.changeShowReport(true, 'Success!', false)
                }, (error: string) => {
                    console.log(error);
                    this.changeShowReport(true, 'There Was an Error', true)
                });
            return allBooks;
        }

        public getBooksFromObservable() {
            ajax.getJSON('http://localhost:8080/api/books')
                .subscribe((value: Array<Book>) => {
                    this.books = value
                });
        }

        public handleWithEventSource(): void {
            let eventSource = new EventSource('http://localhost:8080/api/books/delayed/1');

            eventSource.onmessage = (response) => {

                let result = JSON.parse(response.data) as Book;
                this.books.push(result);
                this.changeShowReport(true, 'Stream Incoming!', false)
            };

            setTimeout(() => {
                eventSource.close();
                this.changeShowReport(true, 'Stream Closed!', true)
            }, 10000);
        }

        public deleteBook(id: string) {
            this.axios.delete('http://localhost:8080/api/books/' + id)
                .then(value => {
                    this.changeShowReport(true, 'Book Deleted', false)
                },(error: string) => {
                    this.changeShowReport(true, 'Book could not be deleted', true)
                });
            let uid = this.books.findIndex((value) => value.id === id);
            this.books.splice(uid, 1);
        }

        private changeShowReport(show: boolean, message: string, error: boolean): void {
            this.showReport = show;
            this.reportText = message;
            this.hasError = error;
        }

        // created?(): void {
        //     console.log('Created')
        // }
        // beforeDestroy?(): void{
        //     console.log('before Destroy')
        // };
        // destroyed?(): void{
        //     console.log('Destroyed')
        // };
        // beforeMount?(): void{
        // console.log('before mount')
        // }
        // mounted?(): void{
        //     console.log('Mounted')
        // };
        // beforeUpdate?(): void{
        //     console.log('before Update')
        // };
        // updated?(): void{
        //     console.log('Updated')
        // };
        // activated?(): void{
        //     console.log('Activated')
        // };
        // deactivated?(): void{
        //     console.log('Deactivated')
        // };
    }
</script>
