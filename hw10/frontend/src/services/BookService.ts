import { Book } from "./typings"

const getAll = () => {
  return fetch('/api/book').then((response) => response.json())
}

const remove = (id: string) => {
  return fetch('/api/book/' + id, { method: 'DELETE' })
}

const get = (id: string) => {
  return fetch('/api/book/' + id).then((response) => response.json())
}

const save = (book: Book) => {
  return fetch('/api/book', {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(book),
  }).then((response) => response.json())
}

const BookService = {
  getAll,
  remove,
  get,
  save,
}

export default BookService
