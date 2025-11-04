import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useMutation } from "@tanstack/react-query";
import { Suspense, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { postLogin } from "./services/loginService";
import { toast } from "sonner";
import useAuth, { appSaveUser } from "@/store/userStore";
export function Login() {
    const { user, isAuth } = useAuth();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });
    useEffect(() => {
        if (user?.id && isAuth) {
            navigate("/tasks");
            return;
        }
    }, [user, isAuth]);
    const mutation = useMutation({ mutationFn: (data) => postLogin(data),
        onSuccess: (response) => {
            console.log(response);
            appSaveUser(response.usuario);
            navigate("/tasks");
        },
        onError: () => {
            toast.error("Error al iniciar sesión");
        } });
    const handleSubmit = async () => {
        const { email, password } = formData;
        if (!email || !password) {
            toast.error("Por favor, completa todos los campos.");
            return;
        }
        mutation.mutate(formData);
    };
    const handleFormDataChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };
    return (_jsx(Suspense, { fallback: _jsx("div", { children: "Cargando..." }), children: _jsx("main", { className: "flex items-center justify-center h-screen bg-gray-100", children: _jsxs("div", { className: "flex flex-col bg-white rounded-lg shadow-md w-96 p-6 py-14", children: [_jsx("h2", { className: "text-2xl font-bold text-center text-black", children: "Login" }), _jsxs("form", { onSubmit: (e) => { e.preventDefault(); handleSubmit(); }, className: "flex flex-col justify-center h-full gap-5", children: [_jsxs("section", { className: "gap-3 flex flex-col", children: [_jsxs("div", { className: "mb-4", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "email", children: "Email" }), _jsx(Input, { type: "email", id: "email", placeholder: "Ingresa tu email", value: formData.email, onChange: handleFormDataChange, required: true, name: "email" })] }), _jsxs("div", { className: "mb-6", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "password", children: "Password" }), _jsx(Input, { type: "password", id: "password", placeholder: "Ingresa tu contrase\u00F1a", value: formData.password, onChange: handleFormDataChange, required: true, name: "password" })] })] }), _jsx(Button, { type: "submit", className: "cursor-pointer", disabled: mutation.isPending, children: mutation.isPending ? "Cargando..." : "Iniciar sesión" }), _jsx(Button, { variant: "secondary", className: "cursor-pointer", disabled: mutation.isPending, onClick: () => navigate('/register'), children: "Crear cuenta" })] })] }) }) }));
}
