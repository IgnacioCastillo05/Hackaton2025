import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Navigate, Outlet } from "react-router-dom";
import { HomeAside } from "./Aside";
import { TbMenu4 } from "react-icons/tb";
import useAuth from "@/store/userStore";
import { useState } from "react";
export function Layout() {
    const [isHidden, setisHidden] = useState(true);
    const { isAuth } = useAuth();
    if (!isAuth) {
        return _jsx(Navigate, { to: "/", replace: true });
    }
    return (_jsx("main", { className: "flex flex-col h-screen bg-gray-100 w-full", children: _jsxs("div", { className: "flex flex-row w-full h-full", children: [_jsx(HomeAside, { hidden: isHidden }), _jsxs("div", { className: "bg-white rounded-lg shadow-md w-full h-full", children: [_jsx("nav", { className: "flex items-center p-2 md:hidden bg-white w-full border-b border-gray-200", children: _jsx(TbMenu4, { size: 32, className: "cursor-pointer", onClick: () => setisHidden(!isHidden) }) }), _jsx(Outlet, {})] })] }) }));
}
