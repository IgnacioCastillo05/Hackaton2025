export interface User {
  id: string;
  correo: string;
  nombre: string;
}

export interface Task {
  id: string;
  titulo: string;
  descripcion: string;
  fechaLimite: Date | string;
  completada: boolean;
  prioridad: string;
  dificultad: string;
  categoriaId: string;
  usuarioId?: string;
}

export interface Category {
  id: string;
  nombre: string;
}