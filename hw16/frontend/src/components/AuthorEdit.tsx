import { Author, AuthorService } from '../services'
import { Component } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'
import { ComponentWithRoutingProps, withRouting } from '../utils'

interface RouteParams {
  id: string
}

interface ComponentProps extends ComponentWithRoutingProps<RouteParams> {}

interface ComponentState {
  author: Author
}

class AuthorEdit extends Component<ComponentProps, ComponentState> {
  emptyAuthor = {
    id: '',
    name: '',
    surName: '',
  }

  constructor(props: ComponentProps) {
    super(props)
    this.state = {
      author: this.emptyAuthor,
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    let { id } = this.props.params
    if (id !== 'new') {
      AuthorService.get(id).then((data) => this.setState({ author: data }))
    } else {
      this.setState({ author: this.emptyAuthor })
    }
  }

  handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const target = event.target
    const value = target.value
    const name = target.name
    let author = { ...this.state.author }
    author[name] = value
    this.setState({ author })
  }

  handleSubmit() {
    const author = this.state.author
    AuthorService.save(author).then((data) => this.setState({ author: data }))
    this.props.navigate('/author')
  }

  render() {
    const { author } = this.state
    const title = author.id ? 'Изменить автора' : 'Новый автор'
    return (
      <div className="container" id="main">
        <form id="author-edit-form">
          <Card className="card mb-auto">
            <Card.Body className="card-body">
              <h3>{title}</h3>
              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>id</Form.Label>
                <Form.Control
                  type="text"
                  name="id"
                  id="id"
                  value={author.id}
                  readOnly
                />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Имя</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={author.name}
                  onChange={this.handleChange}
                />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Фамилия</Form.Label>
                <Form.Control
                  type="text"
                  name="surName"
                  value={author.surName}
                  onChange={this.handleChange}
                />
              </Form.Group>

              <div className="form-edit-group-row">
                <Button
                  variant="primary"
                  type="button"
                  onClick={this.handleSubmit}
                >
                  Сохранить
                </Button>
                <a href="/author">
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
export default withRouting(AuthorEdit)
