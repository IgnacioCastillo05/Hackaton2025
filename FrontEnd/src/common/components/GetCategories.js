import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { IoAdd } from "react-icons/io5";
import { NavLink } from "react-router-dom";
import { ModalCreateCategory } from "./ModalCreateCategorie";
import { useQuery } from "@tanstack/react-query";
import { getCategories } from "../services/getCategories";
import { FaRegTrashAlt } from "react-icons/fa";
import { ModalDeleteCat } from "./ModalDeleteCat";
export function GetCategories() {
    const { data: categories, isLoading } = useQuery({
        queryKey: ["categories"],
        queryFn: async () => getCategories(),
        staleTime: 1000 * 60 * 5
    });
    return (_jsxs("div", { children: [_jsx("h2", { className: "font-bold text-gray-500 mb-2", children: "Categor\u00EDas" }), _jsxs("ul", { className: "ml-4 space-y-1 mb-2", children: [!isLoading && categories.map((cat) => (_jsxs(NavLink, { to: `task/${cat.id}/${cat.nombre}`, className: ({ isActive }) => `flex items-center justify-between capitalize px-5 py-2 gap-5 rounded hover:bg-gray-200 transition-colors ${isActive ? "bg-gray-200" : ""}`, children: [cat.nombre, _jsx(ModalDeleteCat, { id: cat.id, children: _jsx(FaRegTrashAlt, { className: "text-red-500 hover:text-red-700 cursor-pointer" }) })] }, cat.id))), categories && categories.length === 0 && !isLoading && (_jsx("div", { className: "text-gray-800 font-bold mt-5", children: "No hay categorias" }))] }), _jsx(ModalCreateCategory, { children: _jsxs("div", { className: "flex flex-row items-center \r\n          p-2 gap-3 \r\n          rounded \r\n          hover:bg-gray-200 \r\n          transition-colors text-emerald-700\r\n          cursor-pointer\r\n          ", children: [_jsx(IoAdd, {}), "Crear nueva categor\u00EDa"] }) })] }));
}
