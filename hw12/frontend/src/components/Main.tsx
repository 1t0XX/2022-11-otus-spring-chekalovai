import { Card } from 'react-bootstrap'

function Main() {
  return (
    <div className="container" id="main">
      <form id="main-form" className="card mb-auto ">
        <Card>
          <h3 className="title fw-bold text-success shadow-none text-center">
            Добро пожаловать в Хогвартс
          </h3>
          <Card.Body className="card card-body p-3 bg-light">
            <img
              src="img/lib.jpg"
              className="rounded-5 mx-auto d-block"
              width="555px"
            />
          </Card.Body>
        </Card>
      </form>
    </div>
  )
}

export default Main
