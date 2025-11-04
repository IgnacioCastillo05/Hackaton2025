import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from "@/components/ui/dialog";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue, } from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { useRef, useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createTaskServices } from "../services/createTaskServices";
import { toast } from "sonner";
import useAuth from "@/store/userStore";
import { useNavigate } from "react-router-dom";
export function DialogNewTask({ id }) {
    const formRef = useRef(null);
    const [isOpen, setIsOpen] = useState(false);
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
                id: "",
                titulo: formData.get("titulo"),
                descripcion: formData.get("descripcion"),
                fechaLimite: new Date(formData.get("fechaLimite")).toISOString(),
                dificultad: formData.get("dificultad"),
                prioridad: "Normal",
                categoriaId: id
            };
            mutation.mutate(taskData);
        }
    };
    const mutation = useMutation({ mutationFn: (data) => createTaskServices(data),
        onSuccess: () => {
            toast.success("Tarea creada exitosamente");
            //queryClient.invalidateQueries({ queryKey: ["tasks", id] });
            setIsOpen(false);
            window.location.reload();
        },
        onError: (response) => {
            toast.error("Error al crear la tarea");
            console.log(response);
        } });
    return (_jsxs(Dialog, { open: isOpen, onOpenChange: setIsOpen, children: [_jsx(DialogTrigger, { asChild: true, children: _jsx("div", { className: "text-emerald-700 font-semibold px-4 py-2 border-2 border-emerald-700 rounded hover:bg-emerald-50 transition-colors cursor-pointer", children: "Nueva Tarea" }) }), _jsxs(DialogContent, { className: "sm:max-w-[425px]", children: [_jsxs(DialogHeader, { children: [_jsx(DialogTitle, { children: "Crear nueva tarea" }), _jsx(DialogDescription, { children: "Completa los detalles para tu nueva tarea." })] }), _jsxs("form", { onSubmit: handleSubmit, ref: formRef, children: [_jsxs("div", { className: "grid grid-cols-2 gap-4", children: [_jsxs("div", { className: "grid gap-3 col-span-2", children: [_jsx(Label, { htmlFor: "name-1", children: "Nombre de la tarea" }), _jsx(Input, { id: "name-1", name: "titulo", placeholder: "Escribe el nombre de la tarea" })] }), _jsxs("div", { className: "grid gap-3", children: [_jsx(Label, { htmlFor: "date-1", children: "Fecha Limite" }), _jsx(Input, { id: "date-1", name: "fechaLimite", type: "date" })] }), _jsxs("div", { className: "grid gap-3", children: [_jsx(Label, { htmlFor: "dificultad-1", children: "Dificultad" }), _jsxs(Select, { name: "dificultad", children: [_jsx(SelectTrigger, { className: "w-[180px]", children: _jsx(SelectValue, { placeholder: "Selecciona la dificultad" }) }), _jsx(SelectContent, { children: _jsxs(SelectGroup, { children: [_jsx(SelectLabel, { children: "Dificultad" }), _jsx(SelectItem, { value: "Facil", children: "Facil" }), _jsx(SelectItem, { value: "Medio", children: "Media" }), _jsx(SelectItem, { value: "Dificil", children: "Dificil" })] }) })] })] }), _jsxs("div", { className: "col-span-2 gap-3 grid", children: [_jsx(Label, { htmlFor: "description-1", children: "Descripci\u00F3n" }), _jsx(Textarea, { id: "description-1", className: "resize-none", name: "descripcion", placeholder: "Escribe una breve descripci\u00F3n" })] })] }), _jsxs(DialogFooter, { children: [_jsx(DialogClose, { asChild: true, children: _jsx(Button, { variant: "outline", children: "Cancel" }) }), _jsx(Button, { type: "submit", disabled: mutation.isPending, children: "Crear tarea" })] })] })] })] }));
}
