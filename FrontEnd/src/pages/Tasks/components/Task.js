import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { ModalUpdateTarea } from "./ModalUpdateTask";
import { useMutation } from "@tanstack/react-query";
import { updateTaskService } from "../services/updateTaskService";
import { toast } from "sonner";
const dificultColores = {
    Facil: 'bg-green-100 text-green-800 border-green-100',
    Medio: 'bg-yellow-100 text-yellow-800 border-yellow-100',
    Dificil: 'bg-red-100 text-red-800 border-red-100',
};
export function TaskComponent(data) {
    const mutation = useMutation({
        mutationFn: ({ id, data }) => updateTaskService(id, data),
        onSuccess: () => {
            toast.success("Tarea actualizada");
            //queryClient.invalidateQueries({ queryKey: ["categories"] });
        },
        onError: () => {
            toast.error("Error al actualizar la tarea");
        }
    });
    const handleUpdateTask = (e) => {
        e.stopPropagation();
        mutation.mutate({ id: data.id, data: { ...data, completada: !data.completada } });
    };
    return (_jsx(ModalUpdateTarea, { data: data, mutate: (partialTask) => {
            mutation.mutate({ id: data.id, data: { ...data, ...partialTask } });
        }, children: _jsxs("div", { className: "flex flex-row border-2 border-gray-200 rounded-md p-5 cursor-pointer hover:bg-gray-50 transition-colors", children: [_jsx("div", { children: _jsx("input", { type: "checkbox", className: "\r\n            relative size-5 appearance-none\r\n            rounded-full border border-gray-500 bg-white\r\n            before:absolute before:inset-1 before:rounded-full\r\n            before:bg-white not-checked:before:hidden\r\n            checked:border-emerald-600\r\n            checked:bg-emerald-600\r\n            focus-visible:outline-2\r\n            focus-visible:outline-offset-2\r\n            focus-visible:outline-indigo-60\r\n            disabled:border-gray-300\r\n            disabled:bg-gray-100\r\n            disabled:before:bg-gray-400\r\n            forced-colors:appearance-auto\r\n            cursor-pointer\r\n            forced-colors:before:hidden", onClick: handleUpdateTask, defaultChecked: data.completada }) }), _jsxs("div", { className: "flex-1 flex flex-col ml-6", children: [_jsx("p", { className: "text-gray-800 font-semibold text-lg capitalize", children: data.titulo }), _jsx("p", { className: "text-gray-600", children: data.descripcion.charAt(0).toUpperCase() + data.descripcion.slice(1) }), _jsxs("div", { className: "flex flex-row justify-between items-center mt-2", children: [new Date(data.fechaLimite).toLocaleDateString('es-ES', {
                                    year: 'numeric',
                                    month: 'long',
                                    day: 'numeric'
                                }), _jsx("div", { className: `border-1 rounded-sm px-10 ${dificultColores[data.dificultad]}`, children: data.dificultad })] })] })] }) }));
}
