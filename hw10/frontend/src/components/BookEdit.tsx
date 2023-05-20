import { Component } from 'react'
import {
  GenreService,
  AuthorService,
  BookService,
  Book,
  Author,
  Genre,
} from '../services'
import { withRouting, ComponentWithRoutingProps } from '../utils'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'

interface RouteParams {
  id: string
}

interface ComponentProps extends ComponentWithRoutingProps<RouteParams> {}

interface ComponentState {
  book: Book
  authors: Author[]
  genres: Genre[]
}

class BookEdit extends Component<ComponentProps, ComponentState> {
  emptyBook: Book = {
    id: '',
    name: '',
    author: { id: '', name: '', surName: '' },
    genre: { id: '', name: '' },
  }

  constructor(props: ComponentProps) {
    super(props)
    this.state = {
      book: this.emptyBook,
      authors: [],
      genres: [],
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleAuthorChange = this.handleAuthorChange.bind(this)
    this.handleGenreChange = this.handleGenreChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    let { id } = this.props.params
    if (id !== 'new') {
      BookService.get(id).then((data) => this.setState({ book: data }))
    } else {
      this.setState({ book: this.emptyBook })
    }

    GenreService.getAll().then((data) => this.setState({ genres: data }))
    AuthorService.getAll().then((data) => this.setState({ authors: data }))
  }

  handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const target = event.target
    const value = target.value
    const name = target.name
    let book = { ...this.state.book }
    // TODO: fix
    book[name] = value
    this.setState({ book })
  }

  handleGenreChange(event: React.ChangeEvent<HTMLSelectElement>) {
    const target = event.target
    const value = target.value
    const name = target.name
    let selectedGenre = [...this.state.genres].filter((i) => i.id == value)[0]
    let book = { ...this.state.book }
    // TODO: fix
    book[name] = selectedGenre
    this.setState({ book })
  }

  handleAuthorChange(event: React.ChangeEvent<HTMLSelectElement>) {
    const target = event.target
    const value = target.value
    const name = target.name
    let selectedAuthor = [...this.state.authors].filter((i) => i.id == value)[0]
    let book = { ...this.state.book }
    // TODO: fix
    book[name] = selectedAuthor
    this.setState({ book })
  }

  handleSubmit() {
    const book = this.state.book

    BookService.save(book).then((data) => this.setState({ book: data }))
    // this.props.history.push('/book')
    this.props.navigate('/book')
  }

  authorFormat(author: Author) {
    return author.name + ' ' + author.surName
  }

  render() {
    const { book, genres, authors } = this.state
    const title = book.id ? 'Изменить книгу' : 'Новая книга'
    return (
      <div className="container" id="main">
        <form id="book-edit-form">
          <Card className="card mb-auto">
            <Card.Body className="card-body">
              <h3>{title}</h3>
              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>id</Form.Label>
                <Form.Control
                  type="text"
                  name="id"
                  id="id"
                  value={book.id}
                  readOnly
                />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Наименование</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={book.name}
                  onChange={this.handleChange}
                />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Автор</Form.Label>
                <Form.Select
                  value={book.author.id}
                  name="author"
                  onChange={this.handleAuthorChange}
                >
                  <option></option>
                  {authors.map((a) => (
                    <option key={a.id} value={a.id}>
                      {this.authorFormat(a)}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Жанр</Form.Label>
                <Form.Select
                  value={book.genre.id}
                  name="genre"
                  onChange={this.handleGenreChange}
                >
                  <option></option>
                  {genres.map((g) => (
                    <option key={g.id} value={g.id}>
                      {g.name}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>

              <div className="form-edit-group-row">
                <Button
                  variant="primary"
                  type="button"
                  onClick={this.handleSubmit}
                >
                  Сохранить
                </Button>
                <a href="/book">
                  <Button variant="secondary" type="button">
                    Выход
                  </Button>
                </a>
              </div>
            </Card.Body>
          </Card>
        </form>
      </div>
    )
  }
}
export default withRouting(BookEdit)
