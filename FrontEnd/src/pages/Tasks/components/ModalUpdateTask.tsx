import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"

import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import type { Task } from "@/common/types/types"
import { useState } from "react"

type TaskComponentProps = Pick<Task, 'titulo' | 'descripcion' | 'fechaLimite' | 'dificultad'>;

function formatDateToYYMMDD(date: Date) {
  const year = date.getFullYear().toString(); // Obtener los últimos 2 dígitos del año
  const month = (date.getMonth() + 1).toString().padStart(2, '0'); // getMonth() es 0-indexed
  const day = date.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}



export function ModalUpdateTarea({ children, data, mutate }:
  { children: React.ReactNode, data: TaskComponentProps, mutate: (data: Partial<Task>) => void }) {
  const [isOpen, setIsOpen] = useState(false);

  const date = formatDateToYYMMDD(new Date(data.fechaLimite));

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);

    const updatedTask: Partial<Task> = {
      titulo: formData.get("titulo") as string,
      descripcion: formData.get("descripcion") as string,
      fechaLimite: new Date(formData.get("fechaLimite") as string).toISOString(),
      dificultad: formData.get("dificultad") as string,
    };

    mutate(updatedTask);
    setIsOpen(false);
  }

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogTrigger asChild>
          {children}
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
      <form onSubmit={handleSubmit}>
          <DialogHeader>
            <DialogTitle>Actualizar Tarea</DialogTitle>
            <DialogDescription>
              Completa los detalles para actualizar la tarea.
            </DialogDescription>
          </DialogHeader>
          <div className="grid grid-cols-2 gap-4">
            <div className="grid gap-3 col-span-2">
              <Label htmlFor="name-1">Nombre de la tarea</Label>
              <Input id="name-1" name="titulo" defaultValue={data.titulo || ''} placeholder="Escribe el nombre de la tarea" />
            </div>
            <div className="grid gap-3">
              <Label htmlFor="date-1">Fecha</Label>
              <Input id="date-1" name="fechaLimite" defaultValue={date} type="date" />
            </div>
            <div className="grid gap-3">
              <Label htmlFor="username-1">Dificultad</Label>
              <Select name="dificultad" defaultValue={data.dificultad || ''}>
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Selecciona la dificultad" />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    <SelectLabel>Dificultad</SelectLabel>
                    <SelectItem value="Facil">Facil</SelectItem>
                    <SelectItem value="Medio">Media</SelectItem>
                    <SelectItem value="Dificil">Dificil</SelectItem>
                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>
            <div className="col-span-2 gap-3 grid">
              <Label htmlFor="description-1">Descripción</Label>
              <Textarea id="description-1" defaultValue={data.descripcion || ''} className="resize-none" name="descripcion" placeholder="Escribe una breve descripción" />
            </div>
          </div>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline">Cancel</Button>
            </DialogClose>
            <Button type="submit">Guardar cambios</Button>
          </DialogFooter>
      </form>
        </DialogContent>
    </Dialog>
  )
}
