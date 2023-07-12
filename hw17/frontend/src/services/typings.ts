export interface Author {
  id: string
  name: string
  surName: string
}

export interface Book {
  id: string
  name: string
  author: Author
  genre: Genre
}

export interface Genre {
  id: string
  name: string
}
