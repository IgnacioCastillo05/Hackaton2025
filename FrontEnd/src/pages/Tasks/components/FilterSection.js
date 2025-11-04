import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue, } from "@/components/ui/select";
import { useEffect, useState } from "react";
export function FilterSection({ data, onSort }) {
    const [filters, setFilters] = useState({
        difficulty: "NA",
        date: null,
        completadas: false,
        pendientes: false,
        search: "",
    });
    const filterTasks = (tasks, filters) => {
        let filteredData = [...tasks]; // Comienza siempre con una copia de los datos originales
        // Filtrar por búsqueda en el título
        if (filters.search) {
            const searchTerm = filters.search.toLowerCase();
            filteredData = filteredData.filter(task => task.titulo.toLowerCase().includes(searchTerm));
        }
        // Filtrar por estado de completadas
        if (filters.completadas) {
            filteredData = filteredData.filter(task => task.completada);
        }
        // Filtrar por estado de pendientes (solo si 'completadas' no está activo para evitar conflicto si son checkboxes excluyentes)
        else if (filters.pendientes) {
            filteredData = filteredData.filter(task => !task.completada);
        }
        // Filtrar por dificultad
        // Asegúrate de que 'difficulty' no sea 'null' (para la opción "Limpiar filtro") ni "NA" (si lo usas como valor por defecto)
        if (filters.difficulty && filters.difficulty !== "NA" && filters.difficulty !== null) {
            filteredData = filteredData.filter(task => task.dificultad === filters.difficulty);
        }
        return filteredData;
    };
    useEffect(() => {
        let sortedData = [...data];
        onSort(filterTasks(sortedData, filters));
    }, [filters]);
    return (_jsxs("form", { className: "flex flex-row items-center justify-between p-1 bg-gray-50/10 shadow-md rounded-lg mt-4", children: [_jsxs("div", { className: "flex items-center space-x-4", children: [_jsx(Button, { type: "button", onClick: () => setFilters({ ...filters, completadas: !filters.completadas, pendientes: false }), className: `${filters.completadas ? 'bg-green-500' : 'bg-gray-200'} hover:bg-green-500 px-4 py-2  text-gray-800 rounded cursor-pointer`, children: "Completadas" }), _jsx(Button, { type: "button", onClick: () => setFilters({ ...filters, pendientes: !filters.pendientes, completadas: false }), className: `${filters.pendientes ? 'bg-green-500' : 'bg-gray-200'} hover:bg-green-500 px-4 py-2  text-gray-800 rounded cursor-pointer`, children: "Pendientes" }), _jsxs(Select, { name: "dificultad", defaultValue: "reset", onValueChange: (value) => setFilters((prevFilters) => ({
                            ...prevFilters,
                            difficulty: value === 'reset' ? "NA" : value,
                        })), children: [_jsx(SelectTrigger, { className: "w-[180px]", children: _jsx(SelectValue, { placeholder: "Selecciona la dificultad" }) }), _jsx(SelectContent, { children: _jsxs(SelectGroup, { children: [_jsx(SelectLabel, { children: "Dificultad" }), _jsx(SelectItem, { value: "reset", children: "Ninguno" }), _jsx(SelectItem, { value: "Facil", children: "Facil" }), _jsx(SelectItem, { value: "Medio", children: "Media" }), _jsx(SelectItem, { value: "Dificil", children: "Dificil" })] }) })] })] }), _jsx(Input, { type: "text", onChange: (e) => setFilters({ ...filters, search: e.target.value }), value: filters.search, placeholder: "Buscar tarea...", className: "px-4 py-2 border rounded-lg focus:outline-none focus:ring focus:ring-blue-200" })] }));
}
