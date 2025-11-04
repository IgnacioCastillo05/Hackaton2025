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
import { logoutService } from "../services/logout"
import { appLogout } from "@/store/userStore"

export function ModalLogout({ children }: { children?: React.ReactNode }) {
  const handleLogout = () => {
    logoutService()
    appLogout()
  }
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          {children}
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>Cerrar Sesión</DialogTitle>
            <DialogDescription>
              Estas segura de que quieres cerrar sesión? Esto te llevará a la página de inicio de sesión.
            </DialogDescription>
          </DialogHeader>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline" className="cursor-pointer">Cancel</Button>
            </DialogClose>
            <Button onClick={handleLogout} variant={"destructive"} className="cursor-pointer" >Cerrar Sesión</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  )
}
