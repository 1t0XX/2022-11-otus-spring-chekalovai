import { Component } from 'react'
import { Genre, GenreService } from '../services'
import { withRouting, ComponentWithRoutingProps } from '../utils'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'

interface RouteParams {
  id: string
}

interface ComponentProps extends ComponentWithRoutingProps<RouteParams> {}

interface ComponentState {
  genre: Genre
}

class GenreEdit extends Component<ComponentProps, ComponentState> {
  emptyGenre: Genre = {
    id: '',
    name: '',
  }

  constructor(props: ComponentProps) {
    super(props)
    this.state = {
      genre: this.emptyGenre,
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    let { id } = this.props.params
    if (id !== 'new') {
      GenreService.get(id).then((data) => this.setState({ genre: data }))
    } else {
      this.setState({ genre: this.emptyGenre })
    }
  }

  handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const target = event.target
    const value = target.value
    const name = target.name
    let genre = { ...this.state.genre }
    // TODO: fix
    genre[name] = value
    this.setState({ genre })
  }

  handleSubmit() {
    const genre = this.state.genre
    GenreService.save(genre).then((data) => this.setState({ genre: data }))
    this.props.navigate('/genre')
  }

  render() {
    const { genre } = this.state
    const title = genre.id ? 'Изменить жанр' : 'Новый жанр'
    return (
      <div className="container" id="main">
        <form id="genre-edit-form">
          <Card className="card mb-auto">
            <Card.Body className="card-body">
              <h3>{title}</h3>
              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>id</Form.Label>
                <Form.Control
                  type="text"
                  name="id"
                  id="id"
                  value={genre.id}
                  readOnly
                />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3">
                <Form.Label>Наименование</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={genre.name}
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
                <a href="/genre">
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

export default withRouting(GenreEdit)
