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


import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { useRef, useState } from "react"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { createTaskServices } from "../services/createTaskServices"
import { toast } from "sonner"
import type { Task } from "@/common/types/types"
import useAuth from "@/store/userStore"
import { useNavigate } from "react-router-dom"

export function DialogNewTask({id}: { id: string }) {
  const formRef = useRef<HTMLFormElement>(null);
  const [isOpen, setIsOpen] = useState(false);
  const { user } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const usuarioId = user?.id;

    if (!usuarioId) {
      toast.error("Usuario no encontrado");
      return;
    }

    if (formRef.current) {
      const formData = new FormData(formRef.current);
      
      const taskData: Task = {
        id: "",
        titulo: formData.get("titulo") as string,
        descripcion: formData.get("descripcion") as string,
        fechaLimite: new Date(formData.get("fechaLimite") as string).toISOString(),
        dificultad: formData.get("dificultad") as string,
        prioridad: "Normal",
        categoriaId: id
      };
      mutation.mutate(taskData);
    }
  }
   const mutation = useMutation({ mutationFn: (data: Task) => createTaskServices(data),
    onSuccess: () => {
      toast.success("Tarea creada exitosamente");
      //queryClient.invalidateQueries({ queryKey: ["tasks", id] });
      setIsOpen(false)
      window.location.reload();
    },
    onError: (response) => {
      toast.error("Error al crear la tarea");
      console.log(response);
      
    }
  })

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogTrigger asChild>
          <div className="text-emerald-700 font-semibold px-4 py-2 border-2 border-emerald-700 rounded hover:bg-emerald-50 transition-colors cursor-pointer">
            Nueva Tarea
          </div>
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>Crear nueva tarea</DialogTitle>
            <DialogDescription>
              Completa los detalles para tu nueva tarea.
            </DialogDescription>
          </DialogHeader>


      <form onSubmit={handleSubmit} ref={formRef}>
          <div className="grid grid-cols-2 gap-4">
            <div className="grid gap-3 col-span-2">
              <Label htmlFor="name-1">Nombre de la tarea</Label>
              <Input id="name-1" name="titulo" placeholder="Escribe el nombre de la tarea" />
            </div>
            <div className="grid gap-3">
              <Label htmlFor="date-1">Fecha Limite</Label>
              <Input id="date-1" name="fechaLimite" type="date"  />
            </div>
            <div className="grid gap-3">
              <Label htmlFor="dificultad-1">Dificultad</Label>
              <Select name="dificultad">
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
              <Textarea id="description-1" className="resize-none" name="descripcion" placeholder="Escribe una breve descripción" />
            </div>
          </div>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline">Cancel</Button>
            </DialogClose>
            <Button type="submit" disabled={mutation.isPending}>Crear tarea</Button>
          </DialogFooter>
      </form>
      
        </DialogContent>
    </Dialog>
  )
}
