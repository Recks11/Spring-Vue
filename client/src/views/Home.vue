<template>
  <div class="home">
    <img src="../assets/logo.png">
    <div class="notification is-primary" v-if="showReport === true">
      <button class="delete" @click="showReport = false"></button>
      {{ reportText }}
    </div>
    <table class="table is-bordered is-fullwidth">
        <thead>
        <tr>
          <td>Title</td>
          <td>Publisher</td>
          <td>Year</td>
        </tr>
        </thead>
      <tbody>
      <tr v-for="book in books">
        <td>{{book.title}}</td>
        <td>{{book.publisher}}</td>
        <td>{{book.year}}</td>
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
      <button class="button is-success" @click="getBooks()"> Get Books</button>
      <button class="button is-success" @click="getBooksFromObservable()"> Get Observable</button>
      <button class="button is-success" @click="handleWithEventSource()"> Get EventSource</button>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Book } from '@/model/Book';
import { AxiosResponse } from 'axios';
import { ajax } from 'rxjs/ajax';

@Component
export default class Home extends Vue {
    public books:Array<Book> = [];
    public book: Book = new Book('','',1990);
    public showReport: boolean = false;
    public reportText:string = '';

    public createBook(): void {

        ajax({
            url: 'http://localhost:8080/api/books',
            method: 'post',
            headers: {'Content-Type':'application/json'},
            body: this.book.json
            })
            .subscribe((value) => {
            let data: Book = value.response;
            console.log(data);
            this.books.push(data);
            this.showReport = true;
            this.reportText = 'Book Added'
        },
        (error) => {
            this.showReport = true;
            this.reportText = 'There Was an error'
        });
    }
    public getBooks(): Array<Book> {
        let allBooks: Array<Book> = [];

         this.axios.get('http://localhost:8080/api/books')
            .then((response: AxiosResponse<Book[]>) => {
                // response.data.forEach(
                //     book => {
                //         this.books.push(book)
                //     }
                // )
                this.books = response.data
            }, (error: string) => {
                console.log(error)
            });
        return allBooks;
    }

    public getBooksFromObservable() {
        ajax.getJSON('http://localhost:8080/api/books')
            .subscribe((value: Array<Book>) => {
                this.books = value
            });
        console.log(this.books)
    }

    public handleWithEventSource(): void {
        let eventSource = new EventSource('http://localhost:8080/api/books/delayed/1');

            console.log('CLOSED: '+ eventSource.CLOSED);
            eventSource.onmessage = (response) => {
                let result = JSON.parse(response.data) as Book;
                this.books.push(result);
            };

            setTimeout(() => {
                eventSource.close()
            }, 10000)
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
