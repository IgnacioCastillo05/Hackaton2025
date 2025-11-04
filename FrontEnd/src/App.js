import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Routes, Route } from "react-router";
import { Login } from "./pages/Authentication/Login";
import { TasksPage } from "./pages/Tasks/TasksPage";
import { Layout } from "./common/components/HomeLayout";
import { Register } from "./pages/Authentication/Register";
import { Home } from "./pages/Home/Home";
import { Settings } from "./pages/Settings/Settings";
export function App() {
    return (_jsxs(Routes, { children: [_jsx(Route, { path: "/", index: true, element: _jsx(Login, {}) }), _jsx(Route, { path: "/register", index: true, element: _jsx(Register, {}) }), _jsxs(Route, { element: _jsx(Layout, {}), children: [_jsx(Route, { path: "tasks", element: _jsx(Home, {}) }), _jsx(Route, { index: true, path: "task/:id/:nombre", element: _jsx(TasksPage, {}) }), _jsx(Route, { path: "settings", element: _jsx(Settings, {}) })] })] }));
}
