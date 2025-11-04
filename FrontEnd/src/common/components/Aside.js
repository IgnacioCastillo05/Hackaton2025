import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { FaUser } from "react-icons/fa";
import { IoSettingsSharp } from "react-icons/io5";
import { ImExit } from "react-icons/im";
import { NavLink } from "react-router-dom";
import { GetCategories } from "./GetCategories";
import useAuth from "@/store/userStore";
import { ModalLogout } from "./ModalLogout";
const routes = [
    {
        path: "/settings",
        label: "Configuración",
        icon: _jsx(IoSettingsSharp, {})
    }
];
export function HomeAside({ hidden }) {
    const { user } = useAuth();
    return (_jsxs("aside", { className: `${hidden ? 'hidden' : 'fixed block z-50'} h-full w-full md:block md:w-80 bg-gray-50 text-gray-700 p-3`, children: [_jsx("h2", { className: "text-xl font-bold mb-4", children: user?.nombre }), _jsx(GetCategories, {}), _jsx("br", {}), _jsx("hr", {}), _jsx("br", {}), _jsxs("ul", { className: "space-y-2 w-full", children: [routes.map((route) => (_jsxs(NavLink, { to: route.path, className: ({ isActive }) => `flex items-center p-2 gap-5 rounded hover:bg-gray-200 transition-colors ${isActive ? "bg-gray-200" : ""}`, children: [route.icon, route.label] }, route.path))), _jsxs("div", { className: "flex items-center p-2 gap-5 rounded hover:bg-gray-200 transition-colors text-red-600 cursor-pointer", children: [_jsx(ImExit, {}), _jsx(ModalLogout, { children: _jsx("p", { children: "Cerrar Sesi\u00F3n" }) })] })] })] }));
}
