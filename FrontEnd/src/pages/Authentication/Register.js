import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { postRegister } from "./services/loginService";
import { toast } from "sonner";
import { appSaveUser } from "@/store/userStore";
export function Register() {
    const [formData, setFormData] = useState({
        email: "",
        password: "",
        nombre: "",
        confirmarPassword: ""
    });
    const mutation = useMutation({
        mutationFn: (data) => postRegister(data),
        onSuccess: (response) => {
            console.log(response);
            appSaveUser(response.usuario);
            navigate("/tasks");
        },
        onError: () => {
            toast.error("Error al iniciar sesión");
        }
    });
    const navigate = useNavigate();
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
    return (_jsx("main", { className: "flex items-center justify-center h-screen bg-gray-100", children: _jsxs("div", { className: "flex flex-col bg-white rounded-lg shadow-md w-96 p-6 py-14", children: [_jsx("h2", { className: "text-2xl font-bold text-center text-black", children: "Login" }), _jsxs("form", { onSubmit: (e) => { e.preventDefault(); handleSubmit(); }, className: "flex flex-col justify-center h-full gap-5", children: [_jsxs("section", { className: "gap-3 flex flex-col", children: [_jsxs("div", { className: "mb-4", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "email", children: "Nombre" }), _jsx(Input, { type: "text", id: "nombre", placeholder: "Ingresa tu email", value: formData.nombre, onChange: handleFormDataChange, required: true, name: "nombre" })] }), _jsxs("div", { className: "mb-4", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "email", children: "Email" }), _jsx(Input, { type: "email", id: "email", placeholder: "Ingresa tu email", value: formData.email, onChange: handleFormDataChange, required: true, name: "email" })] }), _jsxs("div", { className: "mb-6", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "password", children: "Password" }), _jsx(Input, { type: "password", id: "password", placeholder: "Ingresa tu contrase\u00F1a", value: formData.password, onChange: handleFormDataChange, required: true, name: "password" })] }), _jsxs("div", { className: "mb-6", children: [_jsx("label", { className: "block text-sm font-medium mb-2", htmlFor: "password", children: "Confirmar Contrase\u00F1a" }), _jsx(Input, { type: "password", id: "passwordconfirmar", placeholder: "Confirma tu contrase\u00F1a", value: formData.confirmarPassword, onChange: handleFormDataChange, required: true, name: "confirmarPassword" })] })] }), _jsx(Button, { type: "submit", className: "cursor-pointer", disabled: mutation.isPending, children: mutation.isPending ? "Cargando..." : "Crear cuenta" }), _jsx(Button, { variant: "secondary", className: "cursor-pointer", disabled: mutation.isPending, onClick: () => navigate('/register'), children: "Iniciar sesi\u00F3n" })] })] }) }));
}
