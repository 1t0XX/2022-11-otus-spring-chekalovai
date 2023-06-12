import { NavigateFunction, useNavigate, useParams } from 'react-router-dom'

export function withRouting(Component) {
  return (props) => (
    <Component {...props} params={useParams()} navigate={useNavigate()} />
  )
}

export interface ComponentWithRoutingProps<T> {
  params: T
  navigate: NavigateFunction
}
