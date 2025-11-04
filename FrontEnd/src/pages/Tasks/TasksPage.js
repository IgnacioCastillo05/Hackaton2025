import { jsx as _jsx, Fragment as _Fragment, jsxs as _jsxs } from "react/jsx-runtime";
import { useParams } from "react-router-dom";
import { DialogNewTask } from "./components/ModalCreateTask";
import { getTasksById } from "./services/getTasksById";
import { DotLottieReact } from "@lottiefiles/dotlottie-react";
import { TaskComponent } from "./components/Task";
import { FilterSection } from "./components/FilterSection";
import { useCallback, useEffect, useState } from "react";
export function TasksPage() {
    const { id, nombre } = useParams();
    const [data, setData] = useState();
    const [filterData, setFilterData] = useState();
    useEffect(() => {
        const fetchData = async () => {
            if (id) {
                const response = await getTasksById(id);
                setData(response);
                setFilterData(response);
            }
        };
        fetchData();
    }, [id]);
    const handleSortTasks = useCallback((sortedData) => {
        setData(sortedData);
    }, []);
    return (_jsxs("section", { className: "h-full w-full p-6 overflow-auto relative", children: [_jsx("h1", { className: "text-3xl font-semibold capitalize", children: nombre }), _jsxs("section", { className: "", children: [_jsx("p", { className: "text-gray-600 font-semibold p-5", children: "Lista de tareas" }), _jsxs("div", { children: [_jsx(DialogNewTask, { id: id }), _jsx(FilterSection, { data: filterData || [], onSort: (data) => handleSortTasks(data) }), _jsx("div", { className: "py-2 space-y-2 mt-3", children: data && data.length > 0 ? (_jsx(_Fragment, { children: data.map((task) => (_jsx(TaskComponent, { id: task.id, completada: task.completada, titulo: task.titulo, descripcion: task.descripcion, fechaLimite: task.fechaLimite, dificultad: task.dificultad }, task.id))) })) : (_jsxs("div", { className: "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col items-center justify-center", children: [_jsx(DotLottieReact, { src: "/empty.lottie", className: "w-80  md:w-96 h-auto", loop: true, autoplay: true }), _jsxs("div", { className: "text-gray-800 font-bold mt-5", children: ["No hay tareas para la categor\u00EDa ", nombre] })] })) })] })] })] }));
}
