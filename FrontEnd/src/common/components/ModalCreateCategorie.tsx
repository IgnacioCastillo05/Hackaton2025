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
import useAuth from "@/store/userStore";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useRef, useState } from "react";
import { toast } from "sonner";
import type { Category } from "../types/types";
import { postCategorieServices } from "../services/postCategorie";
import { useNavigate } from "react-router-dom";

export function ModalCreateCategory({ children }: { children: React.ReactNode }) {
  const queryClient = useQueryClient();
  const formRef = useRef<HTMLFormElement>(null);
  const [open, setOpen] = useState(false);
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

      const taskData: Category = {
        nombre: formData.get("category") as string,
        id: usuarioId
      };
      mutation.mutate(taskData);
    }
  }

  const mutation = useMutation({
    mutationFn: (data: Category) => postCategorieServices(data),
    onSuccess: (response: Category) => {
      toast.success("Categoría creada");
      queryClient.invalidateQueries({ queryKey: ["categories"] });
      navigate(`/task/${response.id}/${response.nombre}`);
      setOpen(false);
    },
    onError: () => {
      toast.error("Error al crear la categoría");
    }
  })

  return (
    <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
          {children}
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
      <form onSubmit={handleSubmit} ref={formRef} id="create_cateogory">
          <DialogHeader>
            <DialogTitle>Crear nueva categoría</DialogTitle>
            <DialogDescription>
              Completa los detalles crear una categoría.
            </DialogDescription>
          </DialogHeader>
          <div className="grid grid-cols-2 gap-4">
            <div className="grid gap-3 col-span-2">
              <Label htmlFor="category">Nombre de la categoría</Label>
              <Input id="category" name="category" placeholder="Escribe el nombre de la tarea" />
            </div>
          </div>
          <DialogFooter className="mt-4">
            <DialogClose asChild>
              <Button variant="outline">Cancelar</Button>
            </DialogClose>
            <Button type="submit" form="create_cateogory" disabled={mutation.isPending}>Crear categoría</Button>
          </DialogFooter>
      </form>
        </DialogContent>
    </Dialog>
  )
}
