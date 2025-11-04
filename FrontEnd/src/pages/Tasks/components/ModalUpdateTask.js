import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue, } from "@/components/ui/select";
import { useState } from "react";
function formatDateToYYMMDD(date) {
    const year = date.getFullYear().toString(); // Obtener los últimos 2 dígitos del año
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // getMonth() es 0-indexed
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}
export function ModalUpdateTarea({ children, data, mutate }) {
    const [isOpen, setIsOpen] = useState(false);
    const date = formatDateToYYMMDD(new Date(data.fechaLimite));
    const handleSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const updatedTask = {
            titulo: formData.get("titulo"),
            descripcion: formData.get("descripcion"),
            fechaLimite: new Date(formData.get("fechaLimite")).toISOString(),
            dificultad: formData.get("dificultad"),
        };
        mutate(updatedTask);
        setIsOpen(false);
    };
    return (_jsxs(Dialog, { open: isOpen, onOpenChange: setIsOpen, children: [_jsx(DialogTrigger, { asChild: true, children: children }), _jsx(DialogContent, { className: "sm:max-w-[425px]", children: _jsxs("form", { onSubmit: handleSubmit, children: [_jsxs(DialogHeader, { children: [_jsx(DialogTitle, { children: "Actualizar Tarea" }), _jsx(DialogDescription, { children: "Completa los detalles para actualizar la tarea." })] }), _jsxs("div", { className: "grid grid-cols-2 gap-4", children: [_jsxs("div", { className: "grid gap-3 col-span-2", children: [_jsx(Label, { htmlFor: "name-1", children: "Nombre de la tarea" }), _jsx(Input, { id: "name-1", name: "titulo", defaultValue: data.titulo || '', placeholder: "Escribe el nombre de la tarea" })] }), _jsxs("div", { className: "grid gap-3", children: [_jsx(Label, { htmlFor: "date-1", children: "Fecha" }), _jsx(Input, { id: "date-1", name: "fechaLimite", defaultValue: date, type: "date" })] }), _jsxs("div", { className: "grid gap-3", children: [_jsx(Label, { htmlFor: "username-1", children: "Dificultad" }), _jsxs(Select, { name: "dificultad", defaultValue: data.dificultad || '', children: [_jsx(SelectTrigger, { className: "w-[180px]", children: _jsx(SelectValue, { placeholder: "Selecciona la dificultad" }) }), _jsx(SelectContent, { children: _jsxs(SelectGroup, { children: [_jsx(SelectLabel, { children: "Dificultad" }), _jsx(SelectItem, { value: "Facil", children: "Facil" }), _jsx(SelectItem, { value: "Medio", children: "Media" }), _jsx(SelectItem, { value: "Dificil", children: "Dificil" })] }) })] })] }), _jsxs("div", { className: "col-span-2 gap-3 grid", children: [_jsx(Label, { htmlFor: "description-1", children: "Descripci\u00F3n" }), _jsx(Textarea, { id: "description-1", defaultValue: data.descripcion || '', className: "resize-none", name: "descripcion", placeholder: "Escribe una breve descripci\u00F3n" })] })] }), _jsxs(DialogFooter, { children: [_jsx(DialogClose, { asChild: true, children: _jsx(Button, { variant: "outline", children: "Cancel" }) }), _jsx(Button, { type: "submit", children: "Guardar cambios" })] })] }) })] }));
}
