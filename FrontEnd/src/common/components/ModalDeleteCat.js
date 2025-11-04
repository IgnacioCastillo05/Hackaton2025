import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from "@/components/ui/dialog";
import { useMutation } from "@tanstack/react-query";
import { toast } from "sonner";
import { deleteCategorieServices } from "../services/deleteCategorie";
import { useNavigate } from "react-router-dom";
export function ModalDeleteCat({ children, id }) {
    const navigate = useNavigate();
    const mutation = useMutation({ mutationFn: (id) => deleteCategorieServices(id),
        onSuccess: (response) => {
            toast.success("Categoría eliminada exitosamente");
            console.log(response);
            navigate('/tasks');
        },
        onError: () => {
            toast.error("Error al eliminar la categoría");
        } });
    const handleLogout = () => {
        console.log("id", id);
        mutation.mutate(id);
        //navigate(0);
    };
    return (_jsx(Dialog, { children: _jsxs("form", { children: [_jsx(DialogTrigger, { asChild: true, children: children }), _jsxs(DialogContent, { className: "sm:max-w-[425px]", children: [_jsxs(DialogHeader, { children: [_jsx(DialogTitle, { children: "Elimar Cateoria" }), _jsx(DialogDescription, { children: "Estas seguro de eliminar esta categor\u00EDa? Esta acci\u00F3n no se puede deshacer." })] }), _jsxs(DialogFooter, { children: [_jsx(DialogClose, { asChild: true, children: _jsx(Button, { variant: "outline", className: "cursor-pointer", children: "Cancelar" }) }), _jsx(Button, { onClick: handleLogout, type: "button", variant: "destructive", className: "cursor-pointer", children: "Eliminar Categor\u00EDa" })] })] })] }) }));
}
