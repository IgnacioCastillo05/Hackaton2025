import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import useAuth from "@/store/userStore";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useRef, useState } from "react";
import { toast } from "sonner";
import { postCategorieServices } from "../services/postCategorie";
import { useNavigate } from "react-router-dom";
export function ModalCreateCategory({ children }) {
    const queryClient = useQueryClient();
    const formRef = useRef(null);
    const [open, setOpen] = useState(false);
    const { user } = useAuth();
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
        const usuarioId = user?.id;
        if (!usuarioId) {
            toast.error("Usuario no encontrado");
            return;
        }
        if (formRef.current) {
            const formData = new FormData(formRef.current);
            const taskData = {
                nombre: formData.get("category"),
                id: usuarioId
            };
            mutation.mutate(taskData);
        }
    };
    const mutation = useMutation({
        mutationFn: (data) => postCategorieServices(data),
        onSuccess: (response) => {
            toast.success("Categoría creada");
            queryClient.invalidateQueries({ queryKey: ["categories"] });
            navigate(`/task/${response.id}/${response.nombre}`);
            setOpen(false);
        },
        onError: () => {
            toast.error("Error al crear la categoría");
        }
    });
    return (_jsxs(Dialog, { open: open, onOpenChange: setOpen, children: [_jsx(DialogTrigger, { asChild: true, children: children }), _jsx(DialogContent, { className: "sm:max-w-[425px]", children: _jsxs("form", { onSubmit: handleSubmit, ref: formRef, id: "create_cateogory", children: [_jsxs(DialogHeader, { children: [_jsx(DialogTitle, { children: "Crear nueva categor\u00EDa" }), _jsx(DialogDescription, { children: "Completa los detalles crear una categor\u00EDa." })] }), _jsx("div", { className: "grid grid-cols-2 gap-4", children: _jsxs("div", { className: "grid gap-3 col-span-2", children: [_jsx(Label, { htmlFor: "category", children: "Nombre de la categor\u00EDa" }), _jsx(Input, { id: "category", name: "category", placeholder: "Escribe el nombre de la tarea" })] }) }), _jsxs(DialogFooter, { className: "mt-4", children: [_jsx(DialogClose, { asChild: true, children: _jsx(Button, { variant: "outline", children: "Cancelar" }) }), _jsx(Button, { type: "submit", form: "create_cateogory", disabled: mutation.isPending, children: "Crear categor\u00EDa" })] })] }) })] }));
}
