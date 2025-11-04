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
import { useMutation } from "@tanstack/react-query";
import { toast } from "sonner";
import { deleteCategorieServices } from "../services/deleteCategorie";
import { useNavigate } from "react-router-dom";

export function ModalDeleteCat({ children, id }: { children?: React.ReactNode, id: string }) {
  const navigate = useNavigate();
  const mutation = useMutation({ mutationFn: (id: string) => deleteCategorieServices(id),
    onSuccess: (response) => {
      toast.success("Categoría eliminada exitosamente");
      console.log(response);
      navigate('/tasks');
    },
    onError: () => {
      toast.error("Error al eliminar la categoría");
    }
  })
  const handleLogout = () => {
    console.log("id", id);
    
    mutation.mutate(id);
    //navigate(0);

  }
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          {children}
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>Elimar Cateoria</DialogTitle>
            <DialogDescription>
              Estas seguro de eliminar esta categoría? Esta acción no se puede deshacer.
            </DialogDescription>
          </DialogHeader>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline" className="cursor-pointer">Cancelar</Button>
            </DialogClose>
            <Button onClick={handleLogout} type="button" variant={"destructive"} className="cursor-pointer" >Eliminar Categoría</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  )
}
